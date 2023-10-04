package jsilgado.mecalux.persistence.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import jsilgado.mecalux.persistence.entity.Warehouse;

@Repository
public interface WarehouseRepository extends JpaSpecificationExecutor<Warehouse>, JpaRepository<Warehouse, UUID> {

}