package jsilgado.mecalux.service.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import jsilgado.mecalux.persistence.entity.WarehouseFamilies;
import lombok.Data;

@Data
public class WarehouseInDTO {

	@NotNull(message = "WarehouseFamily is mandatory")
	private WarehouseFamilies warehouseFamily;

	@NotBlank(message = "Client is mandatory")
	private String client;

	@NotNull(message = "Capacity is mandatory")
	@Min(value = 0, message = "Capacity should not be less than 0")
	private Integer capacity;

}
