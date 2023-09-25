package jsilgado.mecalux.persistence.entity;

import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import lombok.Data;

@Data
@Entity
@Table(name = "WAREHOUSE")
public class Warehouse {

	@Id
	@GeneratedValue(generator = "uuid2")
	@GenericGenerator(name = "uuid2", strategy = "uuid2" )
	@Column(name = "id", updatable = false, nullable = false, columnDefinition = "VARCHAR(36)")
	private UUID id;

	@Column(name = "client", nullable = false, columnDefinition = "VARCHAR(100)")
	private String client;

	@Column(name = "warehouseFamily", nullable = false, columnDefinition = "VARCHAR(3)")
	@Enumerated(value = EnumType.STRING)
	private WarehouseFamilies warehouseFamily;

	@Column(name = "size", nullable = false)
	private Integer size;

	@OneToMany(mappedBy = "warehouse", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private List<Rack> lstRack;
}
