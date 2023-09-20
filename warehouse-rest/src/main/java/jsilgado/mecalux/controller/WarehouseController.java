package jsilgado.mecalux.controller;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import jsilgado.mecalux.exception.ResourceNotFoundException;
import jsilgado.mecalux.mapper.WarehouseMapper;
import jsilgado.mecalux.persistence.entity.Warehouse;
import jsilgado.mecalux.service.WarehouseService;
import jsilgado.mecalux.service.dto.WarehouseDTO;
import jsilgado.mecalux.service.dto.WarehouseInDTO;

@RestController
@RequestMapping("/warehouses")
public class WarehouseController {

	private final WarehouseService warehouseService;

	private final WarehouseMapper warehouseMapper;

	public WarehouseController(WarehouseService warehouseService, WarehouseMapper warehouseMapper) {
		this.warehouseService = warehouseService;
		this.warehouseMapper = warehouseMapper;
	}

	@Operation(summary = "Warehouse create", description = "Creation of a warehouse", tags = { "WarehouseController" })
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PostMapping
	public ResponseEntity<WarehouseDTO> create(@Valid @RequestBody WarehouseInDTO warehouseInDTO) {

		Warehouse warehouse = warehouseService.insert(warehouseInDTO);
		
		WarehouseDTO warehouseDTO = warehouseMapper.warehouseToWarehouseDTO(warehouse);

		return new ResponseEntity<>(warehouseDTO, HttpStatus.CREATED);

	}

	@Operation(summary = "Warehouse find all", description = "Gets all warehouses", tags = { "WarehouseController" })
	@GetMapping
	public ResponseEntity<List<WarehouseDTO>> findAll() {

		return Optional.ofNullable(warehouseMapper.warehouseToWarehouseDTO(warehouseService.getAll()))
				.map(lst -> ResponseEntity.ok().body(lst)) // 200 OK
				.orElseGet(() -> ResponseEntity.notFound().build()); // 404 Not found
	}

	@Operation(summary = "Warehouse find one", description = "Gets one warehouse by their id", tags = { "WarehouseController" })
	@GetMapping("/{id}")
	public ResponseEntity<WarehouseDTO> findById(
			@Parameter(description="Warehouse id", required = true, example="3fa85f64-5717-4562-b3fc-2c963f66afa6", in = ParameterIn.PATH)
			@PathVariable(value = "id") UUID id) {

		Warehouse warehouse = warehouseService.getById(id);

		return Optional.ofNullable(warehouse)
				.map(dto -> ResponseEntity.ok().body(warehouseMapper.warehouseToWarehouseDTO(dto))) // 200 OK
				.orElseThrow(() -> new ResourceNotFoundException("UUID Not Found")); // 404 Not found
	}

	@Operation(summary = "Warehouse update one", description = "Update one warehouse by their id", tags = { "WarehouseController" })
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PutMapping("/{id}")
	public ResponseEntity<WarehouseDTO> update(@PathVariable(value = "id") UUID id,
			@Valid @RequestBody WarehouseInDTO warehouseInDTO) {

		Warehouse warehouse = Optional.ofNullable(warehouseService.getById(id))
				.orElseThrow(() -> new ResourceNotFoundException("UUID Not Found")); // 404 Not found

		warehouse.setClient(warehouseInDTO.getClient());
		warehouse.setSize(warehouseInDTO.getSize());
		warehouse.setWarehouseFamily(warehouseInDTO.getWarehouseFamily());

		warehouseService.update(warehouse);

		return ResponseEntity.ok().body(warehouseMapper.warehouseToWarehouseDTO(warehouse)); // 200 OK
	}


	@Operation(summary = "Delete warehouse", description = "Delete an exists warehouse and their racks", tags = { "WarehouseController" })
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(
			@Parameter(description="Warehouse id", required = true, example="3fa85f64-5717-4562-b3fc-2c963f66afa6", in = ParameterIn.PATH )
			@PathVariable(value = "id") UUID id) {

        warehouseService.delete(id);

        return ResponseEntity.noContent().build();

	}

	@Operation(summary = "Warehouse racks permutations",
			description = "Calculates the possible permutations of racking types in a warehouse based on the warehouse family",
			tags = { "WarehouseController" })
	@GetMapping("/rackspermutations/{id}")
	public ResponseEntity<List<String>> racksPermutations(@PathVariable(value = "id") UUID id){

		return new ResponseEntity<>(warehouseService.getRacksPermutations(id), HttpStatus.OK);
	}

}
