package jsilgado.mecalux.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import jsilgado.mecalux.exception.ResourceNotFoundException;
import jsilgado.mecalux.exception.ServiceException;
import jsilgado.mecalux.model.warehouse.WarehouseFactory;
import jsilgado.mecalux.model.warehouse.WarehouseModel;
import jsilgado.mecalux.persistence.entity.Rack;
import jsilgado.mecalux.persistence.entity.Warehouse;
import jsilgado.mecalux.persistence.repository.RackRepository;
import jsilgado.mecalux.persistence.repository.WarehouseRepository;
import jsilgado.mecalux.service.dto.RackInDTO;
import jsilgado.mecalux.service.mapper.RackInDTOToRack;

@Service
public class RackService{

	private final RackRepository rackrepository;

	private final WarehouseRepository warehouseRepository;

	private final RackInDTOToRack rackInDTOToRack;



	public RackService(RackRepository rackrepository, WarehouseRepository warehouseRepository, RackInDTOToRack rackInDTOToRack) {
		this.rackrepository = rackrepository;
		this.warehouseRepository = warehouseRepository;
		this.rackInDTOToRack = rackInDTOToRack;

	}


	public Rack insert(UUID warehouseid, RackInDTO i) {

		Warehouse warehouse = Optional.ofNullable(warehouseRepository.findById(warehouseid)).
		map(Optional::get).orElseThrow(() -> new ResourceNotFoundException("Warehouse", "id", warehouseid));

		this.validateSizeWarehouse(warehouseid, warehouse.getSize());

		WarehouseModel warehouseModel = WarehouseFactory.getWarehouse(warehouse.getWarehouseFamily());
		warehouseModel.validateRackInWarehouse(i.getRackType());

		Rack rask = rackInDTOToRack.map(i);
		rask.setWarehouse(warehouse);

		rask = rackrepository.save(rask);

		return rask;
	}


	public List<Rack> findByWarehouse(UUID warehouseid) {

		Warehouse warehouse = Optional.ofNullable(warehouseRepository.findById(warehouseid)).
				map(Optional::get).orElseThrow(() -> new ResourceNotFoundException("Warehouse", "id", warehouseid));

		return rackrepository.findByWarehouse(warehouse.getId());
	}

	@Transactional
	public void delete(UUID id) {

		rackrepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Rack", "id", id));

		rackrepository.deleteById(id);

	}


	private void validateSizeWarehouse(UUID warehouseid, Integer warehouseSize) {


		Long countByWarehouse = rackrepository.countByWarehouse(warehouseid);

		if (countByWarehouse >= warehouseSize) {
			throw new ServiceException("The warehouse is full.");
		}

	}

}
