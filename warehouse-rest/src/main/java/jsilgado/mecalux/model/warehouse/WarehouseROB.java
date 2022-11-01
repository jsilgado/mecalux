package jsilgado.mecalux.model.warehouse;

import java.util.Arrays;
import java.util.List;

import jsilgado.mecalux.exception.ServiceException;
import jsilgado.mecalux.persistence.entity.RackTypes;

public class WarehouseROB extends WarehouseModel {


	@Override
	public List<RackTypes> validRacks() {

		return Arrays.asList(RackTypes.A, RackTypes.C, RackTypes.D);
	}


	@Override
	public void validateRackInWarehouse(RackTypes rackType) {

		if (!this.validRacks().contains(rackType)) {
			throw new ServiceException("In an ROB warehouse, only type A, C, D racks can be installed.");
		}

	}

}
