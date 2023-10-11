package jsilgado.mecalux.service.dto;

import java.util.UUID;

import jsilgado.mecalux.persistence.entity.enums.WarehouseFamilies;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper = true)
public class WarehouseDTO extends AuditDTO {

	private UUID id;

	private WarehouseFamilies warehouseFamily;

	private String client;

	private Integer capacity;
	
	private String cdCountry;
	
	private String dsCountry;

}
