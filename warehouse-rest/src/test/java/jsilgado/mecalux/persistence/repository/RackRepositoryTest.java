package jsilgado.mecalux.persistence.repository;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

import jsilgado.mecalux.persistence.entity.Rack;


/**
 * Test RackRepositoryTest
 */
@DataJpaTest
@TestPropertySource(locations = "classpath:test.properties")
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Sql({ "/dataTest.sql" }) 	
class RackRepositoryTest {


	@Autowired
    private RackRepository repository;

	UUID uuid = UUID.fromString("3fa85f64-5717-4562-b3fc-2c963f66afa6");


    /**
     * Inicialización antes de empezar los test
     */
    @BeforeEach
    public void setUp() {
    	 assertNotNull(repository);
    }


    @Test
    void countByWarehouse() {

    	Long size = repository.countByWarehouse(uuid);

    	assertTrue(size > 0);

    }


    @Test
    void findByWarehouse() {

    	List<Rack> lstRack = repository.findByWarehouse(uuid);

    	assertFalse(lstRack.isEmpty());

    }

}
