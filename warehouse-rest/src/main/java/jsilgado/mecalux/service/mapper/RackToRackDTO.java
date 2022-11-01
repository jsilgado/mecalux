package jsilgado.mecalux.service.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jsilgado.mecalux.persistence.entity.Rack;
import jsilgado.mecalux.service.dto.RackDTO;

@Component
public class RackToRackDTO implements IMapper<Rack, RackDTO> {

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public RackDTO map(Rack in) {

		return modelMapper.map(in, RackDTO.class);
	}

	//TODO - A lo mejor lo quito

	@Override
	public List<RackDTO> map(List<Rack> in) {

		return in.stream().map(rackDTO -> map(rackDTO)).collect(Collectors.toList());

	}

}
