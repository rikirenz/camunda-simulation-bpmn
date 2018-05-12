package bpsimWrappers;

import bpsim.ParameterValue;
import interfaces.ICostParametersWrapper;

public class CostParametersWrapper extends ParametersWrapper implements ICostParametersWrapper {

	private ParameterValue fixedCost;
	private ParameterValue unitCost;

	public CostParametersWrapper() {}

	public Double getFixedCost() throws Exception {
		return TypeBrain.returnDouble(fixedCost);
	}

	public void setFixedCost(ParameterValue fixedCost)  {
		this.fixedCost = fixedCost;
	}

	public Double getUnitCost() throws Exception {
		return TypeBrain.returnDouble(unitCost);
	}

	public void setUnitCost(ParameterValue unitCost)  {
		this.unitCost = unitCost;
	}
	
	public String toString() {
		return "fixedCost:" + fixedCost + " - unitCost:" + unitCost;
	}
	
}
