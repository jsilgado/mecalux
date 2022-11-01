package jsilgado.mecalux.service.dto;

import javax.validation.constraints.NotNull;

import jsilgado.mecalux.persistence.entity.RackTypes;
import lombok.Data;

@Data
public class RackInDTO {

	@NotNull(message = "rackType is mandatory")
	private RackTypes rackType;

}
