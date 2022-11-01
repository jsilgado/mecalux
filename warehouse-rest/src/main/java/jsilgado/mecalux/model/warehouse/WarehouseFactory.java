package jsilgado.mecalux.model.warehouse;

import jsilgado.mecalux.persistence.entity.WarehouseFamilies;

public class WarehouseFactory {

	public static WarehouseModel getWarehouse(WarehouseFamilies warehouseFamily) {

		if (WarehouseFamilies.EST.equals(warehouseFamily)) {
			return new WarehouseEST();

		}
		if (WarehouseFamilies.ROB.equals(warehouseFamily)) {
			return new WarehouseROB();
		}

		return null;
	}

}
