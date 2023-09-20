package jsilgado.mecalux.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.NullValueMappingStrategy;

import jsilgado.mecalux.persistence.entity.Rack;
import jsilgado.mecalux.service.dto.RackDTO;
import jsilgado.mecalux.service.dto.RackInDTO;

@Mapper(componentModel = "spring", nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT)
public interface RackMapper {
	
	RackDTO rackToRackDTO(Rack Rack);
	List<RackDTO> rackToRackDTO(List<Rack> lstRack);

	Rack rackInDTOToRack(RackInDTO RackInDTO);
}
