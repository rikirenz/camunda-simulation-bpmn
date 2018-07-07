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
		this.endTime = simClock.getCurrentTime() + time;
		this.processId = processId;
	}
	
	public SimulationCatchEvent(String name, long time, long startTime, String processId, long interTriggerTime, long triggerCount) {
		this.name = name;
		this.time = time;
		this.endTime = startTime;
		this.triggerCount = triggerCount;
		this.interTriggerTime = interTriggerTime;
		this.endTime = simClock.getCurrentTime() + time;
		this.processId = processId;
	}

	public long getTime() {
		return time;
	}

	public void setEndTime(long endTime) {
		this.endTime = endTime;
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
		return "name: "+ name +", time:" + time +", startTime:" + endTime + ", processId:" + processId + 
				", interTriggerTime: " + interTriggerTime + ", TriggerCount: " + triggerCount;
	}

	public long getTriggerCount() {
		return triggerCount;
	}

	public void setTriggerCount(long triggerCount) {
		this.triggerCount = triggerCount;
	}
	

	public SimulationCatchEvent getNextEvent() {
		triggerCount--;
		if (triggerCount == 0) return null;
		
		return new SimulationCatchEvent(
			name,
			time,
			endTime + interTriggerTime, 
			processId, 
			interTriggerTime, 
			triggerCount
		);	
	}

}
	



	


	
	

	
	
	