package jsilgado.mecalux.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import jsilgado.mecalux.feign.CountryClient;
import jsilgado.mecalux.feign.dto.CountryDTO;
import jsilgado.mecalux.mapper.WarehouseMapper;
import jsilgado.mecalux.persistence.entity.Warehouse;
import jsilgado.mecalux.persistence.entity.WarehouseFamilies;
import jsilgado.mecalux.persistence.repository.WarehouseRepository;
import jsilgado.mecalux.service.dto.WarehouseDTO;
import jsilgado.mecalux.service.dto.WarehouseInDTO;
import jsilgado.mecalux.service.impl.WarehouseServiceImpl;
import net.datafaker.Faker;

/**
 * Test WarehouseService
 */
@RunWith(MockitoJUnitRunner.class)
class WarehouseServiceTest {

	@Mock
	private WarehouseRepository warehouseRepository;

	@Mock
	private WarehouseMapper warehouseMapper;

	@Mock
	private CountryClient countryClient;

	/**
	 * Servicio
	 */
	private WarehouseService service;

	private WarehouseInDTO warehouseInDTO;

	private Warehouse warehouse;

	private WarehouseDTO warehouseDTO;

	private List<Warehouse> lstWarehouse;

	private List<WarehouseDTO> lstWarehouseDTO;

	private List<CountryDTO> lstCountryDTO;

	/**
	 * Inicialización antes de empezar los test
	 */
	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
		service = new WarehouseServiceImpl(warehouseRepository, warehouseMapper, countryClient);
		assertNotNull(service);

		Faker faker = new Faker();

		warehouse = Warehouse.builder().id(UUID.randomUUID()).capacity(faker.number().numberBetween(1, 9))
				.warehouseFamily(WarehouseFamilies.EST).build();

		warehouseDTO = new WarehouseDTO();
		warehouseDTO.setId(UUID.randomUUID());
		warehouseDTO.setCapacity(faker.number().numberBetween(1, 9));
		warehouseDTO.setWarehouseFamily(WarehouseFamilies.EST);

		warehouseInDTO = new WarehouseInDTO();
		warehouseInDTO.setCapacity(faker.number().numberBetween(1, 9));
		warehouseInDTO.setWarehouseFamily(WarehouseFamilies.EST);

		lstWarehouse = new ArrayList<>();
		lstWarehouse.add(warehouse);

		lstWarehouseDTO = new ArrayList<WarehouseDTO>();
		lstWarehouseDTO.add(warehouseDTO);

		lstCountryDTO = new ArrayList<>();

		CountryDTO countryDTO = new CountryDTO();
		CountryDTO.Name name = countryDTO.new Name();
		countryDTO.setName(name);
		countryDTO.getName().setOfficial(faker.country().name());

		lstCountryDTO.add(countryDTO);

	}

	@Test
    void getAll_Ok() {

    	when(warehouseRepository.findAll()).thenReturn(lstWarehouse);
    	
    	when(warehouseMapper.warehouseToWarehouseDTO(lstWarehouse)).thenReturn(lstWarehouseDTO);
    	
    	when(countryClient.getCountrybycca3(Mockito.anyString())).thenReturn(lstCountryDTO);

    	List<WarehouseDTO> all = service.getAll();

    	assertFalse(all.isEmpty());

    }

	@Test
    void create_Ok() {

    	when(warehouseRepository.save(Mockito.any(Warehouse.class))).thenReturn(warehouse);
    	
    	when(warehouseMapper.warehouseToWarehouseDTO(warehouse)).thenReturn(warehouseDTO);

    	WarehouseDTO dto = service.insert(warehouseInDTO);

    	assertNotNull(dto.getId());

    }

	@Test
	void getRackPermutations_Ok() {

		warehouse.setCapacity(4);

		when(warehouseRepository.findById(Mockito.any(UUID.class))).thenReturn(Optional.of(warehouse));

		List<String> lst = service.getRacksPermutations(UUID.randomUUID());

		// VR: Racks Permitted ^ Size
		// The warehouse size is 4 and racks are 3. 3 ^ 4 = 81
		assertEquals(lst.size(), 81);

	}

}
