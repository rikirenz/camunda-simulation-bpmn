package simulation;

public class SimulationEndEvent extends SimulationEvent {

	private long time;
	private String processId;
	private SimulationClock simClock =  new SimulationClock();

	public SimulationEndEvent(String name, long time, String processId) {
		this.name = name;
		this.time = time;
		this.processId = processId;
	}
	
	public String getProcessId() {
		return processId;
	}

	public void setProcessId(String processId) {
		this.processId = processId;
	}
		
	public String toString() {
		return "name: "+ name +", time:" + time +", endTime:" + startTime + ", processId:" + processId;
	}
}
	



	


	
	

	
	
	