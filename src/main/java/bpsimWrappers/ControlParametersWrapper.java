package bpsimWrappers;

import bpsim.ParameterValue;

public class ControlParametersWrapper {

	private ParameterValue InterTriggerTimer;
	private ParameterValue Probability;
	private ParameterValue TriggerCount;
	private ParameterValue Condition;
	
	public ControlParametersWrapper() {}

	public ParameterValue getInterTriggerTimer() {
		return InterTriggerTimer;
	}

	public void setInterTriggerTimer(ParameterValue interTriggerTimer) {
		InterTriggerTimer = interTriggerTimer;
	}

	public ParameterValue getProbability() {
		return Probability;
	}

	public void setProbability(ParameterValue probability) {
		Probability = probability;
	}

	public ParameterValue getTriggerCount() {
		return TriggerCount;
	}

	public void setTriggerCount(ParameterValue triggerCount) {
		TriggerCount = triggerCount;
	}

	public ParameterValue getCondition() {
		return Condition;
	}

	public void setCondition(ParameterValue condition) {
		Condition = condition;
	}
	
	public String toString() {
		return "InterTriggerTimer: " + InterTriggerTimer.toString()  + " - Probability: " + Probability.toString() + 
			   " - TriggerCount: " + TriggerCount.toString() + " - Condition: " + Condition.toString();
	}
}
