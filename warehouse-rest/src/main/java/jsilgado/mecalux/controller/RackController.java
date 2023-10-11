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
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jsilgado.mecalux.service.RackService;
import jsilgado.mecalux.service.dto.RackDTO;
import jsilgado.mecalux.service.dto.RackInDTO;

@RestController
@RequestMapping("/racks")
public class RackController {

	private final RackService rackService;

	public RackController(RackService rackService) {
		this.rackService = rackService;
	}


	@Operation(summary = "Create rack in a warehouse", description = "Create rack in a warehouse", tags = { "RackController" })
	@ApiResponses(value = {
			@ApiResponse(responseCode = "500", description = "Unexpected exception (Internal Server Error)"),
			@ApiResponse(responseCode = "401", description = "Unauthorized request."),
			@ApiResponse(responseCode = "404", description = "Resource not found"),
			@ApiResponse(responseCode = "400", description = "Bad request, review the request param"),
			@ApiResponse(responseCode = "200", description = "Request Successful, review the resulting object. If infoError is not null, then a functional error has occurred in the back-end "),
			@ApiResponse(responseCode = "403", description = "Forbidden") })
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PostMapping("/warehouse/{warehouseId}")
	public ResponseEntity<RackDTO> create(@PathVariable(value = "warehouseId") UUID warehouseId, @Valid @RequestBody RackInDTO raskInDTO){

		RackDTO rackDTO = rackService.insert(warehouseId, raskInDTO);

		return new ResponseEntity<>(rackDTO, HttpStatus.CREATED);
	}

	@Operation(summary = "Find racks by warehouse", description = "Find all racks from a warehouse", tags = { "RackController" })
	@ApiResponses(value = {
			@ApiResponse(responseCode = "500", description = "Unexpected exception (Internal Server Error)"),
			@ApiResponse(responseCode = "401", description = "Unauthorized request."),
			@ApiResponse(responseCode = "404", description = "Resource not found"),
			@ApiResponse(responseCode = "400", description = "Bad request, review the request param"),
			@ApiResponse(responseCode = "200", description = "Request Successful, review the resulting object. If infoError is not null, then a functional error has occurred in the back-end "),
			@ApiResponse(responseCode = "403", description = "Forbidden") })
	@GetMapping("/warehouse/{warehouseId}")
	public ResponseEntity<List<RackDTO>> findByWarehouse(
			@Parameter(description="Warehouse id", required = true, example="3fa85f64-5717-4562-b3fc-2c963f66afa6", in = ParameterIn.PATH)
			@PathVariable(value = "warehouseId") UUID warehouseId){

		List<RackDTO> lstRackDTO = rackService.findByWarehouse(warehouseId);

		return new ResponseEntity<>(lstRackDTO, HttpStatus.OK);
	}

	@Operation(summary = "Delete rack", description = "Delete an exists rack", tags = { "RackController" })
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
			@Parameter(description="Rack id", required = true, example="3fa85f64-5717-4562-b3fc-2c963f66afa6", in = ParameterIn.PATH )
			@PathVariable(value = "id") UUID id) {

		rackService.delete(id);

		return ResponseEntity.noContent().build();
	}

}
