package simulation;

public class SimulationBoundaryEvent extends SimulationEvent {

	private long time;
	private String processId;
	private SimulationClock simClock =  new SimulationClock();

	public SimulationBoundaryEvent(String name, long time, String processId, long startTime) {
		this.name = name;
		this.time = time;
		this.startTime = startTime;
		this.processId = processId;
	}
	
	public long getTime() {
		return time;
	}
	
	public String getProcessId() {
		return processId;
	}
	
	public String toString() {
		return "name: "+ name +", time:" + time +", startTime:" + startTime + ", processId:" + processId;
	}
}




	


	
	

	
	
	