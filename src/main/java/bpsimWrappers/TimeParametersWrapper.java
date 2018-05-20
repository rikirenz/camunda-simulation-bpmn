package bpsimWrappers;

import bpsim.ParameterValue;
import interfaces.ITimeParametersWrapper;

public class TimeParametersWrapper extends ParametersWrapper implements ITimeParametersWrapper {

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
		return TypeBrain.returnLong(duration);
	}

	public void setDuration(ParameterValue currDuration) {
		this.duration = currDuration;
	}

	public Long getLagTime() throws Exception {
		return TypeBrain.returnLong(lagTime);
	}

	public void setLagTime(ParameterValue currLagTime) {
		this.lagTime = currLagTime;
	}

	public Long getWaitTime() throws Exception {
		return TypeBrain.returnLong(waitTime);
	}

	public void setWaitTime(ParameterValue currWaitTime) {
		this.waitTime = currWaitTime;
	}

	public Long getQueueTime() throws Exception {
		return TypeBrain.returnLong(queueTime);
	}

	public void setQueueTime(ParameterValue currQueueTime) {
		this.queueTime = currQueueTime;
	}

	public Long getSetupTime() throws Exception {
		return TypeBrain.returnLong(setUpTime);
	}

	public void setSetupTime(ParameterValue currSetUpTime) {
		this.setUpTime = currSetUpTime;
	}

	public Long getReworkTime() throws Exception {
		return TypeBrain.returnLong(reworkTime);
	}

	public void setReworkTime(ParameterValue currReworkTime) {
		this.reworkTime = currReworkTime;
	}

	public Long getElapsedTime() throws Exception {
		return TypeBrain.returnLong(elapsedTime);
	}

	public void setElapsedTime(ParameterValue currElapsedTime) {
		this.elapsedTime = currElapsedTime;
	}

	public Long getTransferTime() throws Exception {
		return TypeBrain.returnLong(transferTime);
	}

	public void setTransferTime(ParameterValue currTransferTime) {
		this.transferTime = currTransferTime;
	}

	public Long getValidationTime() throws Exception {
		return TypeBrain.returnLong(validationTime);
	}

	public void setValidationTime(ParameterValue currValidationTime) {
		this.validationTime = currValidationTime;
	}

	public Long getProcessingTime() throws Exception {
		return TypeBrain.returnLong(processingTime);
	}

	public void setProcessingTime(ParameterValue currProcessingTime) {
		this.processingTime = currProcessingTime;
	}
	
	public String toString() {
		try {
			return 	"duration: " + TypeBrain.returnLong(duration) + ", lagTime: " + TypeBrain.returnLong(lagTime)  + ", waitTime: " + TypeBrain.returnLong(waitTime)  + 
					", queueTime: " + TypeBrain.returnLong(queueTime)  + ", setUpTime: " + TypeBrain.returnLong(setUpTime)  + 
					", reworkTime: " + TypeBrain.returnLong(reworkTime)  + ", elapsedTime: " + TypeBrain.returnLong(elapsedTime)  + 
					", transferTime: " + TypeBrain.returnLong(transferTime)  + ", validationTime: " + TypeBrain.returnLong(validationTime)  + 
					", processingTime: " + TypeBrain.returnLong(processingTime);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	
	
}
	