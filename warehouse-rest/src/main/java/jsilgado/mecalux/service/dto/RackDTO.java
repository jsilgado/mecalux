package jsilgado.mecalux.service.dto;

import java.util.UUID;

import jsilgado.mecalux.persistence.entity.RackTypes;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper = true)
public class RackDTO {

	private UUID id;

	private RackTypes rackType;

}
