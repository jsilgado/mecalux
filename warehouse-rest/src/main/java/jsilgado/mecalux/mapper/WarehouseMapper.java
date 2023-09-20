package jsilgado.mecalux.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.NullValueMappingStrategy;

import jsilgado.mecalux.persistence.entity.Warehouse;
import jsilgado.mecalux.service.dto.WarehouseDTO;
import jsilgado.mecalux.service.dto.WarehouseInDTO;

@Mapper(componentModel = "spring", nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT)
public interface WarehouseMapper {
	
	WarehouseDTO warehouseToWarehouseDTO(Warehouse warehouse);
	List<WarehouseDTO> warehouseToWarehouseDTO(List<Warehouse> lstWarehouse);

	Warehouse warehouseInDTOToWarehouse(WarehouseInDTO warehouseInDTO);
}
