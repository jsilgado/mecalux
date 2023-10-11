package jsilgado.mecalux.persistence.repository;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;

import jsilgado.mecalux.persistence.entity.Rack;
import jsilgado.mecalux.persistence.entity.Warehouse;
import jsilgado.mecalux.persistence.entity.enums.RackTypes;
import jsilgado.mecalux.persistence.entity.enums.WarehouseFamilies;
import net.datafaker.Faker;

/**
 * Test RackRepositoryTest
 */
@DataJpaTest
@MockBean(JpaMetamodelMappingContext.class)
@TestPropertySource(locations = "classpath:test.properties")
@WithMockUser(username = "Admon", roles = { "ADMIN" })
@AutoConfigureTestDatabase(replace = Replace.NONE)
class RackRepositoryTest {

	@Autowired
	private RackRepository repository;

	@Autowired
	private WarehouseRepository warehouseRepository;

	Faker faker = new Faker();

	/**
	 * Inicialización antes de empezar los test
	 */
	@BeforeEach
	public void setUp() {

	}

	@Test
	void countByWarehouse() {

		Rack rack = insertRack(RackTypes.A);

		Long size = repository.countByWarehouse(rack.getWarehouse().getId());

		assertTrue(size > 0);

	}

	@Test
	void findByWarehouse() {

		Rack rack = insertRack(RackTypes.A);

		List<Rack> lstRack = repository.findByWarehouse(rack.getWarehouse().getId());

		assertFalse(lstRack.isEmpty());

	}

	private Rack insertRack(RackTypes rackTypes) {

		Warehouse warehouse = this.insertWarehouse();
		Rack rack = Rack.builder().rackType(rackTypes).warehouse(warehouse).build();

		return repository.save(rack);
	}

	private Warehouse insertWarehouse() {

		Warehouse warehouse = Warehouse.builder().client(faker.artist().toString())
				.warehouseFamily(WarehouseFamilies.EST).capacity(faker.number().numberBetween(10, 50)).build();

		return warehouseRepository.save(warehouse);

	}

}
