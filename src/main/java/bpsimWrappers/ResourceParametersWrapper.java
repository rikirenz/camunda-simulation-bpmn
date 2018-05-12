package bpsimWrappers;

import bpsim.ParameterValue;
import interfaces.IResourceParametersWrapper;

public class ResourceParametersWrapper extends ParametersWrapper implements IResourceParametersWrapper {

	private ParameterValue availability;
	private ParameterValue quantity;
	private ParameterValue selection;
	private ParameterValue role;

	public ResourceParametersWrapper() {
	}

	public Boolean getAvailability() throws Exception {
		return TypeBrain.returnBoolean(availability);
	}

	public void setAvailability(ParameterValue availability) {
		this.availability = availability;
	}

	public Long getQuantity() throws Exception {
		return TypeBrain.returnLong(quantity);
	}

	public void setQuantity(ParameterValue quantity) {
		this.quantity = quantity;
	}

	public String getSelection() throws Exception {
		return TypeBrain.returnString(selection);
	}

	public void setSelection(ParameterValue selection) {
		this.selection = selection;
	}

	public String getRole() throws Exception {
		return TypeBrain.returnString(role);
	}

	public void setRole(ParameterValue role) {
		this.role = role;
	}

}
