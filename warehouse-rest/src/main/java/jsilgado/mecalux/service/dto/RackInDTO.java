package jsilgado.mecalux.service.dto;

import javax.validation.constraints.NotNull;

import jsilgado.mecalux.persistence.entity.enums.RackTypes;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper = true)
public class RackInDTO {

	@NotNull(message = "rackType is mandatory")
	private RackTypes rackType;

}
