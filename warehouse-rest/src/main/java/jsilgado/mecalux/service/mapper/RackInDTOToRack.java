package jsilgado.mecalux.service.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jsilgado.mecalux.persistence.entity.Rack;
import jsilgado.mecalux.service.dto.RackInDTO;

@Component
public class RackInDTOToRack implements IMapper<RackInDTO, Rack>{

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public Rack map(RackInDTO in) {

		return modelMapper.map(in, Rack.class);

	}

	@Override
	public List<Rack> map(List<RackInDTO> in) {

		return in.stream().map(rack -> map(rack)).collect(Collectors.toList());

	}

}
