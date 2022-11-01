package jsilgado.mecalux.service.dto;

import java.util.UUID;

import jsilgado.mecalux.persistence.entity.RackTypes;
import lombok.Data;

@Data
public class RackDTO {

	private UUID id;

	private RackTypes rackType;

}
