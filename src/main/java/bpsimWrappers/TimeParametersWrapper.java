package bpsimWrappers;

import bpsim.ParameterValue;

public class TimeParametersWrapper {

	private ParameterValue Duration;
	private ParameterValue LagTime;
	private ParameterValue WaitTime;
	private ParameterValue QueueTime;
	private ParameterValue SetUpTime;
	private ParameterValue ReworkTime;
	private ParameterValue ElapsedTime;
	private ParameterValue TransferTime;
	private ParameterValue ValidationTime;
	private ParameterValue ProcessingTime;
	
	
	public TimeParametersWrapper() {}


	public ParameterValue getDuration() {
		return Duration;
	}


	public void setDuration(ParameterValue duration) {
		Duration = duration;
	}


	public ParameterValue getLagTime() {
		return LagTime;
	}


	public void setLagTime(ParameterValue lagTime) {
		LagTime = lagTime;
	}


	public ParameterValue getWaitTime() {
		return WaitTime;
	}


	public void setWaitTime(ParameterValue waitTime) {
		WaitTime = waitTime;
	}


	public ParameterValue getQueueTime() {
		return QueueTime;
	}


	public void setQueueTime(ParameterValue queueTime) {
		QueueTime = queueTime;
	}


	public ParameterValue getSetUpTime() {
		return SetUpTime;
	}


	public void setSetUpTime(ParameterValue setUpTime) {
		SetUpTime = setUpTime;
	}


	public ParameterValue getReworkTime() {
		return ReworkTime;
	}


	public void setReworkTime(ParameterValue reworkTime) {
		ReworkTime = reworkTime;
	}


	public ParameterValue getElapsedTime() {
		return ElapsedTime;
	}


	public void setElapsedTime(ParameterValue elapsedTime) {
		ElapsedTime = elapsedTime;
	}


	public ParameterValue getTransferTime() {
		return TransferTime;
	}


	public void setTransferTime(ParameterValue transferTime) {
		TransferTime = transferTime;
	}


	public ParameterValue getValidationTime() {
		return ValidationTime;
	}


	public void setValidationTime(ParameterValue validationTime) {
		ValidationTime = validationTime;
	}


	public ParameterValue getProcessingTime() {
		return ProcessingTime;
	}


	public void setProcessingTime(ParameterValue processingTime) {
		ProcessingTime = processingTime;
	} 
	
	public String toString() {
		return "Duration: " + Duration.toString() + " - LagTime: " + LagTime.toString() + " - WaitTime: " + WaitTime.toString() + 
			   " - QueueTime: " + QueueTime.toString() + " - SetUpTime: " + SetUpTime.toString() + " - ReworkTime: " + ReworkTime.toString() + 
			   " - ElapsedTime: " + ElapsedTime.toString() + " - TransferTime: " + TransferTime.toString() + 
			   " - ValidationTime: " + ValidationTime.toString() + " - ProcessingTime: " + ProcessingTime.toString();
	}
}
