package jsilgado.mecalux.controller;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.github.acoboh.query.filter.jpa.annotations.QFParam;
import io.github.acoboh.query.filter.jpa.processor.QueryFilter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import jsilgado.mecalux.exception.ResourceNotFoundException;
import jsilgado.mecalux.persistence.entity.Warehouse;
import jsilgado.mecalux.persistence.entity.filter.WarehouseFilterDef;
import jsilgado.mecalux.service.WarehouseService;
import jsilgado.mecalux.service.dto.WarehouseDTO;
import jsilgado.mecalux.service.dto.WarehouseInDTO;

@RestController
@RequestMapping("/warehouses")
public class WarehouseController {

	private final WarehouseService warehouseService;

	public WarehouseController(WarehouseService warehouseService) {
		this.warehouseService = warehouseService;
	}

	@Operation(summary = "Warehouse create", description = "Creation of a warehouse", tags = { "WarehouseController" })
	@ApiResponses(value = {
			@ApiResponse(responseCode = "500", description = "Unexpected exception (Internal Server Error)"),
			@ApiResponse(responseCode = "401", description = "Unauthorized request."),
			@ApiResponse(responseCode = "404", description = "Resource not found"),
			@ApiResponse(responseCode = "400", description = "Bad request, review the request param"),
			@ApiResponse(responseCode = "200", description = "Request Successful, review the resulting object. If infoError is not null, then a functional error has occurred in the back-end "),
			@ApiResponse(responseCode = "403", description = "Forbidden") })
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PostMapping
	public ResponseEntity<WarehouseDTO> create(@Valid @RequestBody WarehouseInDTO warehouseInDTO) {

		WarehouseDTO warehouseDTO = warehouseService.insert(warehouseInDTO);

		return new ResponseEntity<>(warehouseDTO, HttpStatus.CREATED);

	}

	@Operation(summary = "Warehouse find all", description = "Gets all warehouses", tags = { "WarehouseController" })
	@ApiResponses(value = {
			@ApiResponse(responseCode = "500", description = "Unexpected exception (Internal Server Error)"),
			@ApiResponse(responseCode = "401", description = "Unauthorized request."),
			@ApiResponse(responseCode = "404", description = "Resource not found"),
			@ApiResponse(responseCode = "400", description = "Bad request, review the request param"),
			@ApiResponse(responseCode = "200", description = "Request Successful, review the resulting object. If infoError is not null, then a functional error has occurred in the back-end "),
			@ApiResponse(responseCode = "403", description = "Forbidden") })
	@GetMapping
	public ResponseEntity<List<WarehouseDTO>> findAll() {

		return Optional.ofNullable(warehouseService.getAll()).map(lst -> ResponseEntity.ok().body(lst)) // 200 OK
				.orElseGet(() -> ResponseEntity.notFound().build()); // 404 Not found
	}

	@Operation(summary = "Warehouse find one", description = "Gets one warehouse by their id", tags = {
			"WarehouseController" })
	@ApiResponses(value = {
			@ApiResponse(responseCode = "500", description = "Unexpected exception (Internal Server Error)"),
			@ApiResponse(responseCode = "401", description = "Unauthorized request."),
			@ApiResponse(responseCode = "404", description = "Resource not found"),
			@ApiResponse(responseCode = "400", description = "Bad request, review the request param"),
			@ApiResponse(responseCode = "200", description = "Request Successful, review the resulting object. If infoError is not null, then a functional error has occurred in the back-end "),
			@ApiResponse(responseCode = "403", description = "Forbidden") })
	@GetMapping("/{id}")
	public ResponseEntity<WarehouseDTO> findById(
			@Parameter(description = "Warehouse id", required = true, example = "3fa85f64-5717-4562-b3fc-2c963f66afa6", in = ParameterIn.PATH) @PathVariable(value = "id") UUID id) {

		WarehouseDTO warehouseDTO = warehouseService.getById(id);

		return Optional.ofNullable(warehouseDTO).map(dto -> ResponseEntity.ok().body(dto)) // 200 OK
				.orElseThrow(() -> new ResourceNotFoundException("UUID Not Found")); // 404 Not found
	}

	@Operation(summary = "Warehouse update one", description = "Update one warehouse by their id", tags = {
			"WarehouseController" })
	@ApiResponses(value = {
			@ApiResponse(responseCode = "500", description = "Unexpected exception (Internal Server Error)"),
			@ApiResponse(responseCode = "401", description = "Unauthorized request."),
			@ApiResponse(responseCode = "404", description = "Resource not found"),
			@ApiResponse(responseCode = "400", description = "Bad request, review the request param"),
			@ApiResponse(responseCode = "200", description = "Request Successful, review the resulting object. If infoError is not null, then a functional error has occurred in the back-end "),
			@ApiResponse(responseCode = "403", description = "Forbidden") })
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PutMapping("/{id}")
	public ResponseEntity<WarehouseDTO> update(@PathVariable(value = "id") UUID id,
			@Valid @RequestBody WarehouseInDTO warehouseInDTO) {

		WarehouseDTO warehouseDTO = Optional.ofNullable(warehouseService.getById(id))
				.orElseThrow(() -> new ResourceNotFoundException("UUID Not Found")); // 404 Not found

		warehouseDTO.setClient(warehouseInDTO.getClient());
		warehouseDTO.setCapacity(warehouseInDTO.getCapacity());
		warehouseDTO.setWarehouseFamily(warehouseInDTO.getWarehouseFamily());

		warehouseService.update(warehouseDTO);

		return ResponseEntity.ok().body(warehouseDTO); // 200 OK
	}

	@Operation(summary = "Delete warehouse", description = "Delete an exists warehouse and their racks", tags = {
			"WarehouseController" })
	@ApiResponses(value = {
			@ApiResponse(responseCode = "500", description = "Unexpected exception (Internal Server Error)"),
			@ApiResponse(responseCode = "401", description = "Unauthorized request."),
			@ApiResponse(responseCode = "404", description = "Resource not found"),
			@ApiResponse(responseCode = "400", description = "Bad request, review the request param"),
			@ApiResponse(responseCode = "200", description = "Request Successful, review the resulting object. If infoError is not null, then a functional error has occurred in the back-end "),
			@ApiResponse(responseCode = "403", description = "Forbidden") })
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(
			@Parameter(description = "Warehouse id", required = true, example = "3fa85f64-5717-4562-b3fc-2c963f66afa6", in = ParameterIn.PATH) @PathVariable(value = "id") UUID id) {

		warehouseService.delete(id);

		return ResponseEntity.noContent().build();

	}
	
	
	@Operation(summary = "Delete Soft warehouse", description = "Delete Soft an exists warehouse and their racks", tags = {
	"WarehouseController" })
	@ApiResponses(value = {
			@ApiResponse(responseCode = "500", description = "Unexpected exception (Internal Server Error)"),
			@ApiResponse(responseCode = "401", description = "Unauthorized request."),
			@ApiResponse(responseCode = "404", description = "Resource not found"),
			@ApiResponse(responseCode = "400", description = "Bad request, review the request param"),
			@ApiResponse(responseCode = "200", description = "Request Successful, review the resulting object. If infoError is not null, then a functional error has occurred in the back-end "),
			@ApiResponse(responseCode = "403", description = "Forbidden") })
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@DeleteMapping("/{id}/soft-delete")
	public ResponseEntity<WarehouseDTO> softDelete(
	@Parameter(description = "Warehouse id", required = true, example = "3fa85f64-5717-4562-b3fc-2c963f66afa6", in = ParameterIn.PATH) @PathVariable(value = "id") UUID id) {

		WarehouseDTO warehouseDTO = warehouseService.softDelete(id);

		return new ResponseEntity<>(warehouseDTO, HttpStatus.OK); 
	}
	
	@Operation(summary = "Undo Delete Soft warehouse", description = "Undo Delete Soft an exists warehouse and their racks", tags = {
	"WarehouseController" })
	@ApiResponses(value = {
			@ApiResponse(responseCode = "500", description = "Unexpected exception (Internal Server Error)"),
			@ApiResponse(responseCode = "401", description = "Unauthorized request."),
			@ApiResponse(responseCode = "404", description = "Resource not found"),
			@ApiResponse(responseCode = "400", description = "Bad request, review the request param"),
			@ApiResponse(responseCode = "200", description = "Request Successful, review the resulting object. If infoError is not null, then a functional error has occurred in the back-end "),
			@ApiResponse(responseCode = "403", description = "Forbidden") })
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PatchMapping("/{id}/undo-soft-delete")
	public ResponseEntity<WarehouseDTO> undoSoftDelete(
	@Parameter(description = "Warehouse id", required = true, example = "3fa85f64-5717-4562-b3fc-2c963f66afa6", in = ParameterIn.PATH) @PathVariable(value = "id") UUID id) {

		WarehouseDTO warehouseDTO = warehouseService.undoSoftDelete(id);

		return new ResponseEntity<>(warehouseDTO, HttpStatus.OK); 
	}


	

	@Operation(summary = "Warehouse racks permutations", description = "Calculates the possible permutations of racking types in a warehouse based on the warehouse family", tags = {
			"WarehouseController" })
	@ApiResponses(value = {
			@ApiResponse(responseCode = "500", description = "Unexpected exception (Internal Server Error)"),
			@ApiResponse(responseCode = "401", description = "Unauthorized request."),
			@ApiResponse(responseCode = "404", description = "Resource not found"),
			@ApiResponse(responseCode = "400", description = "Bad request, review the request param"),
			@ApiResponse(responseCode = "200", description = "Request Successful, review the resulting object. If infoError is not null, then a functional error has occurred in the back-end "),
			@ApiResponse(responseCode = "403", description = "Forbidden") })
	@GetMapping("/rackspermutations/{id}")
	public ResponseEntity<List<String>> racksPermutations(@PathVariable(value = "id") UUID id) {

		return new ResponseEntity<>(warehouseService.getRacksPermutations(id), HttpStatus.OK);
	}

	@Operation(summary = "Search warehouses", description = "Search with paging of warehouses", tags = {
	"WarehouseController" })
	@ApiResponses(value = {
			@ApiResponse(responseCode = "500", description = "Unexpected exception (Internal Server Error)"),
			@ApiResponse(responseCode = "401", description = "Unauthorized request."),
			@ApiResponse(responseCode = "404", description = "Resource not found"),
			@ApiResponse(responseCode = "400", description = "Bad request, review the request param"),
			@ApiResponse(responseCode = "200", description = "Request Successful, review the resulting object. If infoError is not null, then a functional error has occurred in the back-end "),
			@ApiResponse(responseCode = "403", description = "Forbidden") })
	@GetMapping("/search")
	public Page<WarehouseDTO> search(@RequestParam(name = "page", defaultValue = "0") int page,
			@RequestParam(name = "size", defaultValue = "10") int size,
			@RequestParam(name = "sort", defaultValue = "client") String sort,
			@RequestParam(required = false)  @QFParam(WarehouseFilterDef.class) QueryFilter<Warehouse> filter) {
		
		PageRequest pageRequest = PageRequest.of(page, size, Sort.by(sort));
		
		return warehouseService.search(pageRequest, filter);
	}	
	
}
