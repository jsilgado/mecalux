package jsilgado.mecalux.service.dto;

import java.util.UUID;

import jsilgado.mecalux.persistence.entity.WarehouseFamilies;
import lombok.Data;

@Data
public class WarehouseDTO {

	private UUID id;

	private WarehouseFamilies warehouseFamily;

	private String client;

	private Integer size;
	
	private String cdCountry;
	
	private String dsCountry;

}
