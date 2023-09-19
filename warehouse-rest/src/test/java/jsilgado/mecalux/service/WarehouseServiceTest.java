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
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import jsilgado.mecalux.persistence.entity.Warehouse;
import jsilgado.mecalux.persistence.entity.WarehouseFamilies;
import jsilgado.mecalux.persistence.repository.WarehouseRepository;
import jsilgado.mecalux.service.dto.WarehouseInDTO;
import net.datafaker.Faker;


/**
 * Test WarehouseService
 */
class WarehouseServiceTest {


	@Mock
	private WarehouseRepository warehouseRepository;


    /**
     * Servicio
     */
    @InjectMocks
    private WarehouseService service;


    private WarehouseInDTO warehouseInDTO;

    private Warehouse warehouse;


    private List<Warehouse> lstWarehouse;


    /**
     * Inicialización antes de empezar los test
     */
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        Faker faker = new Faker();

        warehouse = new Warehouse();
        warehouse.setId(UUID.randomUUID());
        warehouse.setSize(faker.number().numberBetween(1, 9));
        warehouse.setWarehouseFamily(WarehouseFamilies.EST);

        warehouseInDTO = new WarehouseInDTO();
        warehouseInDTO.setSize(faker.number().numberBetween(1, 9));
        warehouseInDTO.setWarehouseFamily(WarehouseFamilies.EST);

    	lstWarehouse = new ArrayList<>();
    	lstWarehouse.add(warehouse);
    }


    @Test
    void getAll_Ok() {

    	when(warehouseRepository.findAll()).thenReturn(lstWarehouse);

    	List<Warehouse> all = service.getAll();

    	assertFalse(all.isEmpty());

    }

    @Test
    void create_Ok() {

    	when(warehouseRepository.save(Mockito.any(Warehouse.class))).thenReturn(warehouse);

    	Warehouse entity = service.insert(warehouseInDTO);

    	assertNotNull(entity.getId());

    }



    @Test
	void getRackPermutations_Ok() {

		warehouse.setSize(4);

		when(warehouseRepository.findById(Mockito.any(UUID.class))).thenReturn(Optional.of(warehouse));

		List<String> lst = service.getRacksPermutations(UUID.randomUUID());

		// VR: Racks Permitted ^ Size
		// The warehouse size is 4 and racks are 3. 3 ^ 4 = 81
		assertEquals(lst.size(), 81);

	}

}
