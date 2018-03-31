package bpsimWrappers;

import bpsim.ParameterValue;

public class CostParametersWrapper {

	private ParameterValue fixedCost;
	private ParameterValue unitCost;

	public CostParametersWrapper() {}

	public ParameterValue getFixedCost() {
		return fixedCost;
	}

	public void setFixedCost(ParameterValue fixedCost) {
		this.fixedCost = fixedCost;
	}

	public ParameterValue getUnitCost() {
		return unitCost;
	}

	public void setUnitCost(ParameterValue unitCost) {
		this.unitCost = unitCost;
	}
	
	public String toString() {
		return "fixedCost:" + fixedCost + " - unitCost:" + unitCost;
	}
	
}
