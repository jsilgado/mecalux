package jsilgado.mecalux.model.warehouse;

import java.util.Arrays;
import java.util.List;

import jsilgado.mecalux.exception.ServiceException;
import jsilgado.mecalux.persistence.entity.RackTypes;

public class WarehouseEST extends WarehouseModel {


	@Override
	public List<RackTypes> validRacks() {

		return Arrays.asList(RackTypes.A, RackTypes.B, RackTypes.C);
	}


	@Override
	public void validateRackInWarehouse(RackTypes rackType) {

		if (!this.validRacks().contains(rackType)) {
			throw new ServiceException("In an EST warehouse, only type A, B, C racks can be installed.");
		}

	}

}
