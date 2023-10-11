package jsilgado.mecalux.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import jsilgado.mecalux.exception.ResourceNotFoundException;
import jsilgado.mecalux.exception.ServiceException;
import jsilgado.mecalux.persistence.entity.enums.RackTypes;
import jsilgado.mecalux.security.JwtAuthenticationEntryPoint;
import jsilgado.mecalux.security.JwtTokenProvider;
import jsilgado.mecalux.service.RackService;
import jsilgado.mecalux.service.dto.RackDTO;
import jsilgado.mecalux.service.dto.RackInDTO;

/**
 * Test RackController
 */
@WebMvcTest(RackController.class)
@MockBean(JpaMetamodelMappingContext.class)
@WithMockUser(username = "Admon", roles = { "ADMIN" })
class RackControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private RackService rackService;

	@MockBean
	private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

	@MockBean
	private JwtTokenProvider jwtTokenProvider;

	@MockBean
	private UserDetailsService userDetailsService;

	ObjectMapper objectMapper;

	private RackInDTO rackInDTO;


	private RackDTO rackDTO;

	/**
	 * Inicialización antes de empezar los test
	 */
	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);

		objectMapper = new ObjectMapper();

		UUID randomUUID = UUID.randomUUID();

		rackInDTO = new RackInDTO();
		rackInDTO.setRackType(RackTypes.A);

		rackDTO = new RackDTO();
		rackDTO.setRackType(RackTypes.A);
		rackDTO.setId(randomUUID);

	}

	@Test
	void create_ok() throws Exception {

		when(rackService.insert(Mockito.any(UUID.class), Mockito.any(RackInDTO.class))).thenReturn(rackDTO);

		String writeValueAsString = objectMapper.writeValueAsString(rackInDTO);

		mockMvc.perform(post("/racks/warehouse/" + UUID.randomUUID()).contentType(MediaType.APPLICATION_JSON).content(writeValueAsString))
			.andExpect(status().isCreated())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$.id").isNotEmpty());

	}

	@Test
	void create_notOk_Warehouse_NotFound() throws Exception {

		when(rackService.insert(Mockito.any(UUID.class), Mockito.any(RackInDTO.class))).thenThrow(new ResourceNotFoundException("Warehouse", "id", UUID.randomUUID()));

		String writeValueAsString = objectMapper.writeValueAsString(rackInDTO);

		mockMvc.perform(post("/racks/warehouse/" + UUID.randomUUID()).contentType(MediaType.APPLICATION_JSON).content(writeValueAsString))
			.andExpect(status().isNotFound())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$.message").isNotEmpty());
	}

	@Test
	void create_notOk_Warehouse_Full() throws Exception {

		when(rackService.insert(Mockito.any(UUID.class), Mockito.any(RackInDTO.class))).thenThrow(new ServiceException("Warehouse", "The warehouse is full."));

		String writeValueAsString = objectMapper.writeValueAsString(rackInDTO);

		mockMvc.perform(post("/racks/warehouse/" + UUID.randomUUID()).contentType(MediaType.APPLICATION_JSON).content(writeValueAsString))
			.andExpect(status().isBadRequest())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$.message").isNotEmpty());
	}

	@Test
	void create_notOk_RackInWarehouse() throws Exception {

		when(rackService.insert(Mockito.any(UUID.class), Mockito.any(RackInDTO.class))).thenThrow(new ServiceException("Warehouse", "In an EST warehouse, only type A, B, C racks can be installed."));

		String writeValueAsString = objectMapper.writeValueAsString(rackInDTO);

		mockMvc.perform(post("/racks/warehouse/" + UUID.randomUUID()).contentType(MediaType.APPLICATION_JSON).content(writeValueAsString))
			.andExpect(status().isBadRequest())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$.message").isNotEmpty());
	}

}
