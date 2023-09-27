package jsilgado.mecalux.service;

import java.util.List;
import java.util.UUID;

import jsilgado.mecalux.service.dto.WarehouseDTO;
import jsilgado.mecalux.service.dto.WarehouseInDTO;


public interface WarehouseService extends ServiceTemplate<WarehouseDTO, WarehouseInDTO> {

	List<String> getRacksPermutations(UUID warehouseid);
}
