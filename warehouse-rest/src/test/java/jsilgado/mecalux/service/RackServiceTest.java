package jsilgado.mecalux.service;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
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

import jsilgado.mecalux.exception.ResourceNotFoundException;
import jsilgado.mecalux.exception.ServiceException;
import jsilgado.mecalux.mapper.RackMapper;
import jsilgado.mecalux.persistence.entity.Rack;
import jsilgado.mecalux.persistence.entity.RackTypes;
import jsilgado.mecalux.persistence.entity.Warehouse;
import jsilgado.mecalux.persistence.entity.WarehouseFamilies;
import jsilgado.mecalux.persistence.repository.RackRepository;
import jsilgado.mecalux.persistence.repository.WarehouseRepository;
import jsilgado.mecalux.service.dto.RackDTO;
import jsilgado.mecalux.service.dto.RackInDTO;
import jsilgado.mecalux.service.impl.RackServiceImpl;
import net.datafaker.Faker;

/**
 * Test RackServiceTest
 */
@RunWith(MockitoJUnitRunner.class)
class RackServiceTest {

	@Mock
	private RackRepository rackRepository;

	@Mock
	private WarehouseRepository warehouseRepository;

	@Mock
	private RackMapper rackMapper;
	
	/**
	 * Servicio
	 */
	private RackService service;

	private RackInDTO rackInDTO;
	
	private RackDTO rackDTO;
	
	private Rack rack;

	private Warehouse warehouse;

	private List<Rack> lstRack;
	
	private List<RackDTO> lstRackDTO;
	
	/**
	 * Inicialización antes de empezar los test
	 */
	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
		service = new RackServiceImpl(rackRepository, warehouseRepository, rackMapper);
		Faker faker = new Faker();

		rack = new Rack();
		rack.setId(UUID.randomUUID());
		
		rackDTO = new RackDTO();
		rackDTO.setId(UUID.randomUUID());

		warehouse = new Warehouse();
		warehouse.setId(UUID.randomUUID());
		warehouse.setSize(faker.number().numberBetween(1, 9));
		warehouse.setWarehouseFamily(WarehouseFamilies.EST);

		rackInDTO = new RackInDTO();
		rackInDTO.setRackType(RackTypes.A);

		lstRack = new ArrayList<>();
		lstRack.add(rack);
		
		lstRackDTO = new ArrayList<>();
		lstRackDTO.add(rackDTO);
	}

	@Test
	void insert_ok() {

		when(warehouseRepository.findById(Mockito.any(UUID.class))).thenReturn(Optional.of(warehouse));

		when(rackRepository.countByWarehouse(Mockito.any(UUID.class))).thenReturn(0L);

		when(rackMapper.rackInDTOToRack(rackInDTO)).thenReturn(rack);
		
		when(rackMapper.rackToRackDTO(rack)).thenReturn(rackDTO);

		when(rackRepository.save(rack)).thenReturn(rack);

		rackDTO = service.insert(UUID.randomUUID(), rackInDTO);

		assertNotNull(rackDTO.getId());

	}

	@Test
	void insert_notOK_Warehouse_NotFound() {

		when(warehouseRepository.findById(Mockito.any(UUID.class))).thenReturn(null);

		Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
			service.insert(UUID.randomUUID(), rackInDTO);
		});

		String expectedMessage = "Warehouse not found";
		String actualMessage = exception.getMessage();

		assertTrue(actualMessage.contains(expectedMessage));

	}

	@Test
	void insert_notOK_WarehouseFull() {

		when(warehouseRepository.findById(Mockito.any(UUID.class))).thenReturn(Optional.of(warehouse));

		when(rackRepository.countByWarehouse(Mockito.any(UUID.class))).thenReturn(999L);

		when(rackMapper.rackInDTOToRack(rackInDTO)).thenReturn(rack);

		when(rackRepository.save(rack)).thenReturn(rack);

		Exception exception = assertThrows(ServiceException.class, () -> {
			service.insert(UUID.randomUUID(), rackInDTO);
		});

		String expectedMessage = "The warehouse is full.";
		String actualMessage = exception.getMessage();

		assertTrue(actualMessage.contains(expectedMessage));

	}

	@Test
	void insert_notOK_RackinWarehouseEST() {

		warehouse.setWarehouseFamily(WarehouseFamilies.EST);
		when(warehouseRepository.findById(Mockito.any(UUID.class))).thenReturn(Optional.of(warehouse));
		when(rackRepository.countByWarehouse(Mockito.any(UUID.class))).thenReturn(0L);
		rackInDTO.setRackType(RackTypes.D);

		Exception exception = assertThrows(ServiceException.class, () -> {
			service.insert(UUID.randomUUID(), rackInDTO);
		});

		String expectedMessage = "In an EST warehouse, only type A, B, C racks can be installed.";
		String actualMessage = exception.getMessage();

		assertTrue(actualMessage.contains(expectedMessage));

	}

	@Test
	void insert_notOK_RackinWarehouseROB() {

		warehouse.setWarehouseFamily(WarehouseFamilies.ROB);
		when(warehouseRepository.findById(Mockito.any(UUID.class))).thenReturn(Optional.of(warehouse));
		when(rackRepository.countByWarehouse(Mockito.any(UUID.class))).thenReturn(0L);
		rackInDTO.setRackType(RackTypes.B);

		Exception exception = assertThrows(ServiceException.class, () -> {
			service.insert(UUID.randomUUID(), rackInDTO);
		});

		String expectedMessage = "In an ROB warehouse, only type A, C, D racks can be installed.";
		String actualMessage = exception.getMessage();

		assertTrue(actualMessage.contains(expectedMessage));

	}


	@Test
	void findByWarehouse_ok() {

		when(warehouseRepository.findById(Mockito.any(UUID.class))).thenReturn(Optional.of(warehouse));

		when(rackRepository.findByWarehouse(Mockito.any(UUID.class))).thenReturn(lstRack);
		
		when(rackMapper.rackToRackDTO(lstRack)).thenReturn(lstRackDTO);

		List<RackDTO> lstRackDTO = service.findByWarehouse(UUID.randomUUID());

		assertFalse(lstRackDTO.isEmpty());

	}

}
