package jsilgado.mecalux.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import jsilgado.mecalux.exception.ResourceNotFoundException;
import jsilgado.mecalux.exception.ServiceException;
import jsilgado.mecalux.feign.CountryClient;
import jsilgado.mecalux.feign.dto.CountryDTO;
import jsilgado.mecalux.mapper.WarehouseMapper;
import jsilgado.mecalux.model.warehouse.WarehouseFactory;
import jsilgado.mecalux.model.warehouse.WarehouseModel;
import jsilgado.mecalux.persistence.entity.RackTypes;
import jsilgado.mecalux.persistence.entity.Warehouse;
import jsilgado.mecalux.persistence.repository.WarehouseRepository;
import jsilgado.mecalux.service.WarehouseService;
import jsilgado.mecalux.service.dto.WarehouseDTO;
import jsilgado.mecalux.service.dto.WarehouseInDTO;

@Service
public class WarehouseServiceImpl implements WarehouseService {

	private final WarehouseRepository warehouseRepository;

	private final WarehouseMapper warehouseMapper;

	private final CountryClient countryClient;

	public WarehouseServiceImpl(WarehouseRepository warehouseRepository, WarehouseMapper warehouseMapper,
			CountryClient countryClient) {
		this.warehouseRepository = warehouseRepository;
		this.warehouseMapper = warehouseMapper;
		this.countryClient = countryClient;
	}

	@Override
	public WarehouseDTO initialize() {
		return new WarehouseDTO();
	}

	@Override
	public List<WarehouseDTO> getAll() {

		List<Warehouse> lstWarehouses = warehouseRepository.findAll();

		List<WarehouseDTO> lstWarehousesDTO = warehouseMapper.warehouseToWarehouseDTO(lstWarehouses);

		// Recorrer la lista con stream
		lstWarehousesDTO.stream()
				// Asignar el país a cada elemento
				.forEach(warehouseDTO -> warehouseDTO
						.setDsCountry(this.getCountryCommonName(warehouseDTO.getCdCountry())));

		lstWarehousesDTO.stream().map(x -> countryClient.getCountrybycca3("col"));

		return lstWarehousesDTO;
	}

	@Override
	public WarehouseDTO getById(UUID id) {

		return warehouseMapper.warehouseToWarehouseDTO(warehouseRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Warehouse", "id", id)));
	}

	@Override
	public WarehouseDTO insert(WarehouseInDTO i) {

		Warehouse warehouse = Warehouse.builder().client(i.getClient()).size(i.getSize())
				.warehouseFamily(i.getWarehouseFamily()).build();

		return warehouseMapper.warehouseToWarehouseDTO(warehouseRepository.save(warehouse));
	}

	@Override
	public void update(WarehouseDTO e) {

		Warehouse warehouse = warehouseRepository.findById(e.getId())
				.orElseThrow(() -> new ResourceNotFoundException("UUID Not Found"));

		warehouse.getLstRack();

		// Validate the new size
		if (warehouse.getLstRack().size() > e.getSize()) {
			throw new ServiceException("The new size is smaller than the warehouse racks.");
		}

		// Validate the new Family
		WarehouseModel warehouseModel = WarehouseFactory.getWarehouse(warehouse.getWarehouseFamily());
		warehouse.getLstRack().stream().forEach(x -> warehouseModel.validateRackInWarehouse(x.getRackType()));

		warehouseRepository.save(warehouseMapper.warehouseDTOToWarehouse(e));

	}

	@Override
	@Transactional
	public void delete(UUID id) {

		this.getById(id);

		warehouseRepository.deleteById(id);

	}

	public List<String> getRacksPermutations(UUID warehouseid) {

		Warehouse warehouse = Optional.ofNullable(warehouseRepository.findById(warehouseid)).map(Optional::get)
				.orElseThrow(() -> new ResourceNotFoundException("Warehouse", "id", warehouseid));

		WarehouseModel warehouseModel = WarehouseFactory.getWarehouse(warehouse.getWarehouseFamily());
		List<RackTypes> validRacks = warehouseModel.validRacks();

		return permutations(new ArrayList<>(), validRacks, StringUtils.EMPTY, warehouse.getSize());
	}

	private List<String> permutations(List<String> lstResultado, List<RackTypes> elem, String perm, int sizeWarehouse) {

		if (sizeWarehouse == 0) {
			lstResultado.add(perm);
		} else {
			for (int i = 0; i < elem.size(); i++) {
				permutations(lstResultado, elem, perm + elem.get(i).name(), sizeWarehouse - 1);
			}
		}

		return lstResultado;
	}

	/**
	 * Devuelve el nombre comun de un pais
	 * 
	 * @param cca3
	 * @return
	 */
	private String getCountryCommonName(String cca3) {

		String strCommon = StringUtils.EMPTY;

		if (StringUtils.isNotBlank(cca3)) {
			List<CountryDTO> lstCountry = countryClient.getCountrybycca3(cca3);
			CountryDTO country = lstCountry.get(0);
			strCommon = country.getName().getCommon();
		}

		return strCommon;

	}

}
