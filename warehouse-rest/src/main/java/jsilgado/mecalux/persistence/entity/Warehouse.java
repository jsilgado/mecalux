package jsilgado.mecalux.persistence.entity;

import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "WAREHOUSE")
public class Warehouse extends Audit {

	@Id
	@GeneratedValue(generator = "uuid2")
	@GenericGenerator(name = "uuid2", strategy = "uuid2" )
	@Column(name = "id", updatable = false, nullable = false, columnDefinition = "VARCHAR(36)")
	@Type(type="org.hibernate.type.UUIDCharType")
	private UUID id;

	@Column(name = "client", nullable = false, columnDefinition = "VARCHAR(100)")
	private String client;

	@Column(name = "warehouseFamily", nullable = false, columnDefinition = "VARCHAR(3)")
	@Enumerated(value = EnumType.STRING)
	private WarehouseFamilies warehouseFamily;

	@Column(name = "capacity", nullable = false)
	private Integer capacity;

	@OneToMany(mappedBy = "warehouse", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private List<Rack> lstRack;
	
	@Column(name = "cca3", nullable = true, columnDefinition = "VARCHAR(3)")
	private String cca3;

}
