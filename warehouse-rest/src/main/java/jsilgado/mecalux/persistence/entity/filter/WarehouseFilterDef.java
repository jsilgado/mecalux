package jsilgado.mecalux.persistence.entity.filter;

import io.github.acoboh.query.filter.jpa.annotations.QFDefinitionClass;
import io.github.acoboh.query.filter.jpa.annotations.QFElement;
import jsilgado.mecalux.persistence.entity.Warehouse;

@QFDefinitionClass(Warehouse.class)
public class WarehouseFilterDef {

	@QFElement("client")
	private String client;

	@QFElement("warehouseFamily")
	private String warehouseFamily;

	@QFElement("capacity")
	private Integer capacity;
	
	@QFElement("cca3")
	private String cca3;
}
