package bpsimWrappers;

import bpsim.ParameterValue;
import interfaces.ICostParametersWrapper;

public class CostParametersWrapper implements ICostParametersWrapper {

	private ParameterValue fixedCost;
	private ParameterValue unitCost;

	public CostParametersWrapper() {}

	public Double getFixedCost() throws Exception {
		return TheTypeBrain.returnDouble(fixedCost);
	}

	public void setFixedCost(ParameterValue fixedCost)  {
		this.fixedCost = fixedCost;
	}

	public Double getUnitCost() throws Exception {
		return TheTypeBrain.returnDouble(unitCost);
	}

	public void setUnitCost(ParameterValue unitCost)  {
		this.unitCost = unitCost;
	}
	
	public String toString() {
		return "fixedCost:" + fixedCost + " - unitCost:" + unitCost;
	}
	
}
