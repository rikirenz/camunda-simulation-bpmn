package bpsimWrappers;

import java.util.logging.Logger;

import bpsim.ParameterValue;
import interfaces.IControlParametersWrapper;

public class ControlParametersWrapper implements IControlParametersWrapper{

	private final static Logger LOGGER = Logger.getLogger("ENSO-APP");
	
	private ParameterValue interTriggerTimer;
	private ParameterValue probability;
	private ParameterValue triggerCount;
	private ParameterValue condition;
	
	public ControlParametersWrapper() {}

	public Double getInterTriggerTimer() throws Exception {
		return TheTypeBrain.returnDouble(interTriggerTimer);
	}

	public void setInterTriggerTimer(ParameterValue interTriggerTimer) {
		this.interTriggerTimer = interTriggerTimer;
	}

	public Double getProbability() throws Exception {
		return TheTypeBrain.returnDouble(probability);
	}

	public void setProbability(ParameterValue probability) {
		this.probability = probability;
	}

	public Long getTriggerCount() throws Exception {
		return TheTypeBrain.returnLong(triggerCount);
	}

	public void setTriggerCount(ParameterValue triggerCount) {
		this.triggerCount = triggerCount;
	}

	public Boolean getCondition() throws Exception {
		return TheTypeBrain.returnBoolean(condition);
	}

	public void setCondition(ParameterValue condition) {
		this.condition = condition;
	}
	
}
