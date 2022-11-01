package jsilgado.mecalux.model.warehouse;

import java.util.List;

import jsilgado.mecalux.persistence.entity.RackTypes;

public abstract class WarehouseModel {


	public abstract List<RackTypes> validRacks();

	public abstract void validateRackInWarehouse(RackTypes rackType);

}
