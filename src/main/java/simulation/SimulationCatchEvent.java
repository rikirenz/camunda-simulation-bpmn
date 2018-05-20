package simulation;

public class SimulationCatchEvent extends SimulationEvent {

	private long interTriggerTime;
	private long time;
	private String processId;
	private SimulationClock simClock =  new SimulationClock();

	public SimulationCatchEvent(String name, long time, String processId, long interTriggerTime) {
		this.name = name;
		this.time = time;
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
		return "name: "+ name +", time:" + time +", endTime:" + endTime + ", processId:" + processId + 
				", interTriggerTime: " + interTriggerTime;
	}
}
	



	


	
	

	
	
	