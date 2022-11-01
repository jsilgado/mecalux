package jsilgado.mecalux.controller;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import jsilgado.mecalux.persistence.entity.Warehouse;
import jsilgado.mecalux.persistence.entity.WarehouseFamilies;
import jsilgado.mecalux.security.JwtAuthenticationEntryPoint;
import jsilgado.mecalux.security.JwtTokenProvider;
import jsilgado.mecalux.service.WarehouseService;
import jsilgado.mecalux.service.dto.WarehouseDTO;
import jsilgado.mecalux.service.dto.WarehouseInDTO;
import jsilgado.mecalux.service.mapper.WarehouseToWarehouseDTO;


/**
 * Test WarehouseController
 */
@WebMvcTest(WarehouseController.class)
@WithMockUser(username = "Admon", roles = { "ADMIN" })
class WarehouseControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private WarehouseService warehouseService;

	@MockBean
	private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

	@MockBean
	private	JwtTokenProvider jwtTokenProvider;

	@MockBean
	private UserDetailsService userDetailsService;


	@MockBean
	private WarehouseToWarehouseDTO warehouseToWarehouseDTO;


	ObjectMapper objectMapper;


    private Warehouse warehouse;

    private WarehouseInDTO warehouseInDTO;

    private WarehouseDTO warehouseDTO;


    private List<Warehouse> lstWarehouse;


    /**
     * Inicialización antes de empezar los test
     */
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        objectMapper = new ObjectMapper();

        warehouse = new Warehouse();
        warehouse.setId(UUID.randomUUID());
        warehouse.setClient("Mecalux");
        warehouse.setSize(2);
        warehouse.setWarehouseFamily(WarehouseFamilies.EST);

        warehouseInDTO = new WarehouseInDTO();
        warehouseInDTO.setClient("Mecalux");
        warehouseInDTO.setSize(2);
        warehouseInDTO.setWarehouseFamily(WarehouseFamilies.EST);

        warehouseDTO = new WarehouseDTO();
        warehouseDTO.setId(UUID.randomUUID());
        warehouseDTO.setClient("Mecalux");
        warehouseDTO.setSize(2);
        warehouseDTO.setWarehouseFamily(WarehouseFamilies.EST);

        lstWarehouse = new ArrayList<>();
        lstWarehouse.add(warehouse);
    }

	@Test
	void create_ok() throws Exception {

		when(warehouseService.insert(Mockito.any(WarehouseInDTO.class))).thenReturn(warehouse);

		when(warehouseToWarehouseDTO.map(warehouse)).thenReturn(warehouseDTO);

		String writeValueAsString = objectMapper.writeValueAsString(warehouseInDTO);

		mockMvc.perform(post("/warehouses").contentType(MediaType.APPLICATION_JSON).content(writeValueAsString))
			.andExpect(status().isCreated())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$.id").isNotEmpty())
			.andExpect(jsonPath("$.client").value("Mecalux"))
		    .andExpect(jsonPath("$.size").value("2"));

	}

	@Test
	void create_notOk_clientEmpty() throws Exception {

	    warehouseInDTO.setClient(StringUtils.EMPTY);

		String writeValueAsString = objectMapper.writeValueAsString(warehouseInDTO);

		mockMvc.perform(post("/warehouses").contentType(MediaType.APPLICATION_JSON).content(writeValueAsString))
			.andExpect(status().isBadRequest())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$.message").isNotEmpty());

	}


	@Test
	void create_notOk_size() throws Exception {

	    warehouseInDTO.setSize(-1);

		String writeValueAsString = objectMapper.writeValueAsString(warehouseInDTO);

		mockMvc.perform(post("/warehouses").contentType(MediaType.APPLICATION_JSON).content(writeValueAsString))
			.andExpect(status().isBadRequest())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$.message").isNotEmpty());

	}

	@Test
	void create_notOk_sizeNull() throws Exception {

	    warehouseInDTO.setSize(null);

		String writeValueAsString = objectMapper.writeValueAsString(warehouseInDTO);

		mockMvc.perform(post("/warehouses").contentType(MediaType.APPLICATION_JSON).content(writeValueAsString))
			.andExpect(status().isBadRequest())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$.message").isNotEmpty());

	}

	@Test
	void create_notOk_warehouseFamilyNull() throws Exception {

	    warehouseInDTO.setWarehouseFamily(null);

		String writeValueAsString = objectMapper.writeValueAsString(warehouseInDTO);

		mockMvc.perform(post("/warehouses").contentType(MediaType.APPLICATION_JSON).content(writeValueAsString))
			.andExpect(status().isBadRequest())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$.message").isNotEmpty());

	}


	@Test
	void findAll_ok() throws Exception {

		when(warehouseService.getAll()).thenReturn(lstWarehouse);

		mockMvc.perform(get("/warehouses").contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$").isArray());
	}


	@Test
	void findAll_notFound() throws Exception {

		when(warehouseToWarehouseDTO.map(Mockito.anyList())).thenReturn(null);

		mockMvc.perform(get("/warehouses").contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isNotFound());

		verify(warehouseService).getAll();
	}


	@Test
	void findById_ok() throws Exception {

		when(warehouseService.getById(UUID.fromString("f8c3de3d-1fea-4d7c-a8b0-29f63c4c3454"))).thenReturn(warehouse);

		when(warehouseToWarehouseDTO.map(warehouse)).thenReturn(warehouseDTO);

		mockMvc.perform(get("/warehouses/f8c3de3d-1fea-4d7c-a8b0-29f63c4c3454").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.id").isNotEmpty());

		verify(warehouseService).getById(UUID.fromString("f8c3de3d-1fea-4d7c-a8b0-29f63c4c3454"));

	}

	@Test
	void findById_notFound() throws Exception {

		UUID uuid = UUID.randomUUID();

		when(warehouseService.getById(uuid)).thenReturn(null);

		mockMvc.perform(get("/warehouses/" + uuid.toString()).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isNotFound());

		verify(warehouseService).getById(uuid);

	}

	@Test
	void racksPermutations_ok() throws Exception {

		UUID uuid = UUID.randomUUID();

		when(warehouseService.getRacksPermutations(uuid)).thenReturn(new ArrayList<String>());

		mockMvc.perform(get("/warehouses/rackspermutations/" + uuid.toString())
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk());

		verify(warehouseService).getRacksPermutations(uuid);

	}

}
