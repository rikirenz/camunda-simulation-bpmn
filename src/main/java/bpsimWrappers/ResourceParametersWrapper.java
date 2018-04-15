package bpsimWrappers;

import bpsim.ParameterValue;
import interfaces.IResourceParametersWrapper;

public class ResourceParametersWrapper implements IResourceParametersWrapper {

	private ParameterValue availability;
	private ParameterValue quantity;
	private ParameterValue selection;

	public ResourceParametersWrapper() {
	}

	public Boolean getAvailability() throws Exception {
		return TheTypeBrain.returnBoolean(availability);
	}

	public void setAvailability(ParameterValue availability) {
		this.availability = availability;
	}

	public Long getQuantity() throws Exception {
		return TheTypeBrain.returnLong(quantity);
	}

	public void setQuantity(ParameterValue quantity) {
		this.quantity = quantity;
	}

	public String getSelection() throws Exception {
		return TheTypeBrain.returnString(selection);
	}

	public void setSelection(ParameterValue selection) {
		this.selection = selection;
	}

}
