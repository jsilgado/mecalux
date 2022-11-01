package jsilgado.mecalux.persistence.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import jsilgado.mecalux.persistence.entity.Rack;

@Repository
public interface RackRepository extends JpaRepository<Rack, UUID>{

	 @Query(value = "SELECT COUNT(r) FROM Rack r WHERE r.warehouse.id =:warehouseId")
	 public Long countByWarehouse(@Param("warehouseId") UUID warehouseId);

	 @Query(value = "SELECT r FROM Rack r WHERE r.warehouse.id =:warehouseId")
	 public List<Rack> findByWarehouse(@Param("warehouseId") UUID warehouseId);

}
