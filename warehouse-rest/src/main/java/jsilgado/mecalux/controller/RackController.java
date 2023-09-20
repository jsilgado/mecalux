package jsilgado.mecalux.controller;

import java.util.List;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import jsilgado.mecalux.mapper.RackMapper;
import jsilgado.mecalux.persistence.entity.Rack;
import jsilgado.mecalux.service.RackService;
import jsilgado.mecalux.service.dto.RackDTO;
import jsilgado.mecalux.service.dto.RackInDTO;

@RestController
@RequestMapping("/racks")
public class RackController {

	private final RackService rackService;
	
	private final RackMapper rackMapper;

	public RackController(RackService rackService, RackMapper rackMapper) {
		this.rackService = rackService;
		this.rackMapper = rackMapper;
	}


	@Operation(summary = "Create rack in a warehouse", description = "reate rack in a warehouse", tags = { "RackController" })
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PostMapping("/warehouse/{warehouseId}")
	public ResponseEntity<RackDTO> create(@PathVariable(value = "warehouseId") UUID warehouseId, @Valid @RequestBody RackInDTO raskInDTO){

		Rack rask = rackService.insert(warehouseId, raskInDTO);

		RackDTO raskDTO = rackMapper.rackToRackDTO(rask);

		return new ResponseEntity<>(raskDTO, HttpStatus.CREATED);
	}

	@Operation(summary = "Find racks by warehouse", description = "Find all racks from a warehouse", tags = { "RackController" })
	@GetMapping("/warehouse/{warehouseId}")
	public ResponseEntity<List<RackDTO>> findByWarehouse(
			@Parameter(description="Warehouse id", required = true, example="3fa85f64-5717-4562-b3fc-2c963f66afa6", in = ParameterIn.PATH)
			@PathVariable(value = "warehouseId") UUID warehouseId){

		List<Rack> lstRack = rackService.findByWarehouse(warehouseId);

		List<RackDTO> lstRackDTO = rackMapper.rackToRackDTO(lstRack);

		return new ResponseEntity<>(lstRackDTO, HttpStatus.OK);
	}

	@Operation(summary = "Delete rack", description = "Delete an exists rack", tags = { "RackController" })
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(
			@Parameter(description="Rack id", required = true, example="3fa85f64-5717-4562-b3fc-2c963f66afa6", in = ParameterIn.PATH )
			@PathVariable(value = "id") UUID id) {

		rackService.delete(id);

		return ResponseEntity.noContent().build();
	}

}
