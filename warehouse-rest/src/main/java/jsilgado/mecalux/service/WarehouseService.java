package jsilgado.mecalux.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import jsilgado.mecalux.exception.ResourceNotFoundException;
import jsilgado.mecalux.exception.ServiceException;
import jsilgado.mecalux.model.warehouse.WarehouseFactory;
import jsilgado.mecalux.model.warehouse.WarehouseModel;
import jsilgado.mecalux.persistence.entity.RackTypes;
import jsilgado.mecalux.persistence.entity.Warehouse;
import jsilgado.mecalux.persistence.repository.WarehouseRepository;
import jsilgado.mecalux.service.dto.WarehouseInDTO;

@Service
public class WarehouseService implements ServiceTemplate<Warehouse, WarehouseInDTO> {

	private WarehouseRepository warehouseRepository;

	public WarehouseService(WarehouseRepository warehouseRepository) {
		this.warehouseRepository = warehouseRepository;
	}

	@Override
	public Warehouse initialize() {
		return new Warehouse();
	}

	@Override
	public List<Warehouse> getAll() {
		return warehouseRepository.findAll();
	}

	@Override
	public Warehouse getById(UUID id) {
		return warehouseRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Warehouse", "id", id));
	}

	@Override
	public Warehouse insert(WarehouseInDTO i) {

		Warehouse warehouse = new Warehouse();
		warehouse.setClient(i.getClient());
		warehouse.setSize(i.getSize());
		warehouse.setWarehouseFamily(i.getWarehouseFamily());

		return warehouseRepository.save(warehouse);
	}

	@Override
	public void update(Warehouse e) {

		Warehouse warehouse = this.getById(e.getId());

		warehouse.getLstRack();

		// Validate the new size
		if (warehouse.getLstRack().size() > e.getSize()) {
			throw new ServiceException("The new size is smaller than the warehouse racks.");
		}

		// Validate the new Family
		WarehouseModel warehouseModel = WarehouseFactory.getWarehouse(warehouse.getWarehouseFamily());
		warehouse.getLstRack().stream().forEach(x -> warehouseModel.validateRackInWarehouse(x.getRackType()));

		warehouseRepository.save(e);

	}

	@Override
	@Transactional
	public void delete(UUID id) {

		this.getById(id);

		warehouseRepository.deleteById(id);

	}

	public List<String> getRacksPermutations(UUID warehouseid) {

		Warehouse warehouse = Optional.ofNullable(warehouseRepository.findById(warehouseid)).
				map(Optional::get).orElseThrow(() -> new ResourceNotFoundException("Warehouse", "id", warehouseid));

		WarehouseModel warehouseModel = WarehouseFactory.getWarehouse(warehouse.getWarehouseFamily());
		List<RackTypes> validRacks = warehouseModel.validRacks();

        return permutations(new ArrayList<>(), validRacks, StringUtils.EMPTY, warehouse.getSize());
	}

	private List<String> permutations (List<String> lstResultado, List<RackTypes> elem, String perm, int sizeWarehouse) {

        if (sizeWarehouse == 0) {
        	lstResultado.add(perm);
        } else {
            for (int i = 0; i < elem.size(); i++) {
            	permutations(lstResultado, elem, perm + elem.get(i).name(), sizeWarehouse - 1);
            }
        }

        return lstResultado;
	}

}
