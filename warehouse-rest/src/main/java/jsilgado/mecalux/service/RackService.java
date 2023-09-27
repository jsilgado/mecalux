package jsilgado.mecalux.service;

import java.util.List;
import java.util.UUID;

import jsilgado.mecalux.service.dto.RackDTO;
import jsilgado.mecalux.service.dto.RackInDTO;


public interface RackService {
	
	RackDTO insert(UUID warehouseid, RackInDTO i);
	
	List<RackDTO> findByWarehouse(UUID warehouseid);
	
	void delete (UUID rackid);

}
