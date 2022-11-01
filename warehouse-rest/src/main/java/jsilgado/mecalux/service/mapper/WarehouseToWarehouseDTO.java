package jsilgado.mecalux.service.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jsilgado.mecalux.persistence.entity.Warehouse;
import jsilgado.mecalux.service.dto.WarehouseDTO;

@Component
public class WarehouseToWarehouseDTO implements IMapper<Warehouse, WarehouseDTO> {

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public WarehouseDTO map(Warehouse in) {

		return modelMapper.map(in, WarehouseDTO.class);
	}

	@Override
	public List<WarehouseDTO> map(List<Warehouse> in) {

		return in.stream().map(warehouseDTO -> map(warehouseDTO)).collect(Collectors.toList());
	}

}
