package jsilgado.mecalux.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueMappingStrategy;

import jsilgado.mecalux.feign.CountryClient;
import jsilgado.mecalux.persistence.entity.Warehouse;
import jsilgado.mecalux.service.dto.WarehouseDTO;
import jsilgado.mecalux.service.dto.WarehouseInDTO;

@Mapper(componentModel = "spring", nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT, uses=CountryClient.class)
public interface WarehouseMapper {
	
    @Mapping(target = "cdCountry", source = "cca3")
	WarehouseDTO warehouseToWarehouseDTO(Warehouse warehouse);
    
	List<WarehouseDTO> warehouseToWarehouseDTO(List<Warehouse> lstWarehouse);

	Warehouse warehouseInDTOToWarehouse(WarehouseInDTO warehouseInDTO);
	
	Warehouse warehouseDTOToWarehouse(WarehouseDTO warehouseDTO);
	
}
