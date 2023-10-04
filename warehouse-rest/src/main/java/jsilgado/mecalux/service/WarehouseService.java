package jsilgado.mecalux.service;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import io.github.acoboh.query.filter.jpa.processor.QueryFilter;
import jsilgado.mecalux.persistence.entity.Warehouse;
import jsilgado.mecalux.service.dto.WarehouseDTO;
import jsilgado.mecalux.service.dto.WarehouseInDTO;


public interface WarehouseService extends ServiceTemplate<WarehouseDTO, WarehouseInDTO> {

	List<String> getRacksPermutations(UUID warehouseid);
	
	Page<WarehouseDTO> search(PageRequest pageRequest, QueryFilter<Warehouse> filter);
}
