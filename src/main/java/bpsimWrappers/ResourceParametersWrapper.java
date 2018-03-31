package bpsimWrappers;

import bpsim.ParameterValue;

public class ResourceParametersWrapper {

	private ParameterValue availability;
	private ParameterValue quantity;
	private ParameterValue selection;
	
	public ResourceParametersWrapper() {}

	public ParameterValue getAvailability() {
		return availability;
	}

	public void setAvailability(ParameterValue availability) {
		this.availability = availability;
	}

	public ParameterValue getQuantity() {
		return quantity;
	}

	public void setQuantity(ParameterValue quantity) {
		this.quantity = quantity;
	}

	public ParameterValue getSelection() {
		return selection;
	}

	public void setSelection(ParameterValue selection) {
		this.selection = selection;
	}

	public String toString() {
		return "availability: " +  availability + " - quantity:" + quantity + " - selection:" + selection;
		
	}
	
}
