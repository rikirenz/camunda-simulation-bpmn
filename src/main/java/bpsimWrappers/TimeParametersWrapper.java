package bpsimWrappers;

import bpsim.ParameterValue;
import interfaces.ITimeParametersWrapper;

public class TimeParametersWrapper implements ITimeParametersWrapper {

	private ParameterValue duration;
	private ParameterValue lagTime;
	private ParameterValue waitTime;
	private ParameterValue queueTime;
	private ParameterValue setUpTime;
	private ParameterValue reworkTime;
	private ParameterValue elapsedTime;
	private ParameterValue transferTime;
	private ParameterValue validationTime;
	private ParameterValue processingTime;
	
	public TimeParametersWrapper() {}

	public Long getDuration() throws Exception {
		return TheTypeBrain.returnLong(duration);
	}

	public void setDuration(ParameterValue currDuration) {
		this.duration = currDuration;
	}

	public Long getLagTime() throws Exception {
		return TheTypeBrain.returnLong(lagTime);
	}

	public void setLagTime(ParameterValue currLagTime) {
		this.lagTime = currLagTime;
	}

	public Long getWaitTime() throws Exception {
		return TheTypeBrain.returnLong(waitTime);
	}

	public void setWaitTime(ParameterValue currWaitTime) {
		this.waitTime = currWaitTime;
	}

	public Long getQueueTime() throws Exception {
		return TheTypeBrain.returnLong(queueTime);
	}

	public void setQueueTime(ParameterValue currQueueTime) {
		this.queueTime = currQueueTime;
	}

	public Long getSetupTime() throws Exception {
		return TheTypeBrain.returnLong(setUpTime);
	}

	public void setSetupTime(ParameterValue currSetUpTime) {
		this.setUpTime = currSetUpTime;
	}

	public Long getReworkTime() throws Exception {
		return TheTypeBrain.returnLong(reworkTime);
	}

	public void setReworkTime(ParameterValue currReworkTime) {
		this.reworkTime = currReworkTime;
	}

	public Long getElapsedTime() throws Exception {
		return TheTypeBrain.returnLong(elapsedTime);
	}

	public void setElapsedTime(ParameterValue currElapsedTime) {
		this.elapsedTime = currElapsedTime;
	}

	public Long getTransferTime() throws Exception {
		return TheTypeBrain.returnLong(transferTime);
	}

	public void setTransferTime(ParameterValue currTransferTime) {
		this.transferTime = currTransferTime;
	}

	public Long getValidationTime() throws Exception {
		return TheTypeBrain.returnLong(validationTime);
	}

	public void setValidationTime(ParameterValue currValidationTime) {
		this.validationTime = currValidationTime;
	}

	public Long getProcessingTime() throws Exception {
		return TheTypeBrain.returnLong(processingTime);
	}

	public void setProcessingTime(ParameterValue currProcessingTime) {
		this.processingTime = currProcessingTime;
	} 
}
	