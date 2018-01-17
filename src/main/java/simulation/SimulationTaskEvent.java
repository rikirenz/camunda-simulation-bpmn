package simulation;

public class SimulationTaskEvent extends SimulationEvent {

	private int time;
	private String processId;
	private SimulationClock simClock =  new SimulationClock();

	public SimulationTaskEvent(String name, int time, String processId) {
		this.name = name;
		this.time = time;
		this.endTime = simClock.getCurrentTime() + time;
		this.processId = processId;
	}
	
	public int getTime() {
		return time;
	}
	
	public String getProcessId() {
		return processId;
	}
	
	public String toString() {
		return "name: "+ name +", time:" + time +", endTime:" + endTime + ", processId:" + processId;
	}
}




	


	
	

	
	
	