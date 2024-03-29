package jsilgado.mecalux.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import io.github.acoboh.query.filter.jpa.processor.QueryFilter;
import jsilgado.mecalux.configuration.AuditorAwareImpl;
import jsilgado.mecalux.exception.ResourceNotFoundException;
import jsilgado.mecalux.exception.ServiceException;
import jsilgado.mecalux.feign.CountryClient;
import jsilgado.mecalux.feign.dto.CountryDTO;
import jsilgado.mecalux.mapper.WarehouseMapper;
import jsilgado.mecalux.model.warehouse.WarehouseFactory;
import jsilgado.mecalux.model.warehouse.WarehouseModel;
import jsilgado.mecalux.persistence.entity.Warehouse;
import jsilgado.mecalux.persistence.entity.enums.RackTypes;
import jsilgado.mecalux.persistence.repository.WarehouseRepository;
import jsilgado.mecalux.service.WarehouseService;
import jsilgado.mecalux.service.dto.WarehouseDTO;
import jsilgado.mecalux.service.dto.WarehouseInDTO;

@Service
public class WarehouseServiceImpl implements WarehouseService {

	private final WarehouseRepository warehouseRepository;

	private final WarehouseMapper warehouseMapper;

	private final CountryClient countryClient;
	
	@Autowired
	private AuditorAwareImpl auditorAwareImpl;

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

		this.setCountryCommonName(lstWarehousesDTO);
	
		return lstWarehousesDTO;
	}

	@Override
	public WarehouseDTO getById(UUID id) {

		return warehouseMapper.warehouseToWarehouseDTO(warehouseRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Warehouse", "id", id)));
	}

	@Override
	public WarehouseDTO insert(WarehouseInDTO i) {
		
		Warehouse warehouse = warehouseMapper.warehouseInDTOToWarehouse(i);
				
		WarehouseDTO dto = warehouseMapper.warehouseToWarehouseDTO(warehouseRepository.save(warehouse));
		
		setCountryCommonName(dto);

		return dto;
	}

	@Override
	public void update(WarehouseDTO e) {

		Warehouse warehouse = warehouseRepository.findById(e.getId())
				.orElseThrow(() -> new ResourceNotFoundException("UUID Not Found"));

		warehouse.getLstRack();

		// Validate the new size
		if (warehouse.getLstRack().size() > e.getCapacity()) {
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

		return permutations(new ArrayList<>(), validRacks, StringUtils.EMPTY, warehouse.getCapacity());
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
	
	@Override
	public Page<WarehouseDTO> search(PageRequest pageRequest, QueryFilter<Warehouse> filter) {
		
		Page<Warehouse> search = warehouseRepository.findAll(filter, pageRequest);
		
		Page<WarehouseDTO> searchDTO = search.map(warehouseMapper::warehouseToWarehouseDTO);
		
		this.setCountryCommonName(searchDTO.getContent());
		
		return searchDTO;
	}
	
	@Override
	public WarehouseDTO softDelete(UUID id) {
		Warehouse warehouse = warehouseRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Warehouse", "id", id));
		
		warehouse.setCdUserDelete(auditorAwareImpl.getCurrentAuditor().get());
		warehouse.setDtRowDelete(LocalDateTime.now());
		
		warehouse = warehouseRepository.save(warehouse);
		
		return warehouseMapper.warehouseToWarehouseDTO(warehouse);
		
	}
	
	@Override
	public WarehouseDTO undoSoftDelete(UUID id) {
		Warehouse warehouse = warehouseRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Warehouse", "id", id));
		warehouse.setCdUserDelete(null);
		warehouse.setDtRowDelete(null);
		
		warehouse = warehouseRepository.save(warehouse);
		
		return warehouseMapper.warehouseToWarehouseDTO(warehouse);
	}

	@Override
	public List<WarehouseDTO> getActiveRecords() {
		// TODO Auto-generated method stub
		return null;
	}

	

	
	/**
	 * Devuelve el nombre comun de un pais
	 * 
	 * @param cca3
	 * @return
	 */
	private void setCountryCommonName(WarehouseDTO dto) {
		if (!Objects.isNull(dto)) { 
			dto.setDsCountry(getCountryCommonName(dto.getCdCountry()));
		}
	}
	
	
	/**
	 * Devuelve el nombre comun de un pais
	 * 
	 * @param cca3
	 * @return
	 */
	private void setCountryCommonName(List<WarehouseDTO> dtos) {
		dtos.forEach(this::setCountryCommonName);
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
