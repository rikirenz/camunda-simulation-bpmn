package bpsimWrappers;

import bpsim.ParameterValue;

public class PriorityParametersWrapper {

	private ParameterValue interruptible;
	private ParameterValue priority;

	public PriorityParametersWrapper() {}

	public ParameterValue getInterruptible() {
		return interruptible;
	}

	public void setInterruptible(ParameterValue interruptible) {
		this.interruptible = interruptible;
	}

	public ParameterValue getPriority() {
		return priority;
	}

	public void setPriority(ParameterValue priority) {
		this.priority = priority;
	}

	public String toString() {
		return "interruptible" + interruptible + "- :" + priority;
	}
	
}
