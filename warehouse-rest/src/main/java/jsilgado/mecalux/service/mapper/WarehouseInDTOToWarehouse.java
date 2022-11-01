package jsilgado.mecalux.service.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jsilgado.mecalux.persistence.entity.Warehouse;
import jsilgado.mecalux.service.dto.WarehouseInDTO;

@Component
public class WarehouseInDTOToWarehouse implements IMapper<WarehouseInDTO, Warehouse>{

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public Warehouse map(WarehouseInDTO in) {

		return modelMapper.map(in, Warehouse.class);

	}

	@Override
	public List<Warehouse> map(List<WarehouseInDTO> in) {

		return in.stream().map(warehouse -> map(warehouse)).collect(Collectors.toList());
	}

}
