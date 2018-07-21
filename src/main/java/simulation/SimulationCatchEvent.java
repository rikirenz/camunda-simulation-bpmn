package simulation;

import java.util.logging.Logger;

public class SimulationCatchEvent extends SimulationEvent {

	private long interTriggerTime;
	private long triggerCount;
	private long time;
	private String processId;
	private SimulationClock simClock =  new SimulationClock();

	public SimulationCatchEvent(String name, long time, String processId, long interTriggerTime, long triggerCount) {
		this.name = name;
		this.time = time;
		this.triggerCount = triggerCount;
		this.interTriggerTime = interTriggerTime;
		this.startTime = simClock.getCurrentTime() + time;
		this.processId = processId;
	}
	
	public SimulationCatchEvent(String name, long time, long startTime, String processId, long interTriggerTime, long triggerCount) {
		this.name = name;
		this.time = time;
		this.startTime = startTime;
		this.triggerCount = triggerCount;
		this.interTriggerTime = interTriggerTime;
		this.processId = processId;
	}

	public long getTime() {
		return time;
	}

	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}

	
	public String getProcessId() {
		return processId;
	}

	public void setProcessId(String processId) {
		this.processId = processId;
	}

	
	public long getInterTriggerTime() {
		return interTriggerTime;
	}
		
	public String toString() {
		return "name: "+ name +", time:" + time +", startTime:" + startTime + ", processId:" + processId + 
				", interTriggerTime: " + interTriggerTime + ", TriggerCount: " + triggerCount;
	}

	public long getTriggerCount() {
		return triggerCount;
	}

	public void setTriggerCount(long triggerCount) {
		this.triggerCount = triggerCount;
	}
	

	public SimulationCatchEvent getNextEvent() {
		triggerCount = triggerCount - 1;
		if (triggerCount <= 0) return null;
		
		return new SimulationCatchEvent(
			name,
			time,
			startTime + interTriggerTime, 
			processId, 
			interTriggerTime, 
			triggerCount
		);	
	}

}
	



	


	
	

	
	
	