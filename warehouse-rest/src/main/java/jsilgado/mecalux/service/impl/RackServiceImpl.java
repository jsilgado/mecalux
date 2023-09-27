package jsilgado.mecalux.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import jsilgado.mecalux.exception.ResourceNotFoundException;
import jsilgado.mecalux.exception.ServiceException;
import jsilgado.mecalux.mapper.RackMapper;
import jsilgado.mecalux.model.warehouse.WarehouseFactory;
import jsilgado.mecalux.model.warehouse.WarehouseModel;
import jsilgado.mecalux.persistence.entity.Rack;
import jsilgado.mecalux.persistence.entity.Warehouse;
import jsilgado.mecalux.persistence.repository.RackRepository;
import jsilgado.mecalux.persistence.repository.WarehouseRepository;
import jsilgado.mecalux.service.RackService;
import jsilgado.mecalux.service.dto.RackDTO;
import jsilgado.mecalux.service.dto.RackInDTO;

@Service
public class RackServiceImpl implements RackService{

	private final RackRepository rackrepository;

	private final WarehouseRepository warehouseRepository;
	
	private final RackMapper rackmapper;

	public RackServiceImpl(RackRepository rackrepository, WarehouseRepository warehouseRepository, RackMapper rackmapper) {
		this.rackrepository = rackrepository;
		this.warehouseRepository = warehouseRepository;
		this.rackmapper = rackmapper;
	}


	public RackDTO insert(UUID warehouseid, RackInDTO i) {

		Warehouse warehouse = Optional.ofNullable(warehouseRepository.findById(warehouseid)).
		map(Optional::get).orElseThrow(() -> new ResourceNotFoundException("Warehouse", "id", warehouseid));

		this.validateSizeWarehouse(warehouseid, warehouse.getSize());

		WarehouseModel warehouseModel = WarehouseFactory.getWarehouse(warehouse.getWarehouseFamily());
		warehouseModel.validateRackInWarehouse(i.getRackType());

		Rack rack = rackmapper.rackInDTOToRack(i);
		rack.setWarehouse(warehouse);

		rack = rackrepository.save(rack);

		return rackmapper.rackToRackDTO(rack);
	}


	public List<RackDTO> findByWarehouse(UUID warehouseid) {

		Warehouse warehouse = Optional.ofNullable(warehouseRepository.findById(warehouseid)).
				map(Optional::get).orElseThrow(() -> new ResourceNotFoundException("Warehouse", "id", warehouseid));

		return rackmapper.rackToRackDTO(rackrepository.findByWarehouse(warehouse.getId()));
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
