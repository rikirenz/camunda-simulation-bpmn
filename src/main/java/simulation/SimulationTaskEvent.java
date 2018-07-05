package simulation;

public class SimulationTaskEvent extends SimulationEvent {

	private long time;
	private String id;
	private String processId;
	private SimulationClock simClock =  new SimulationClock();

	public SimulationTaskEvent(String id, String name, long time, String processId) {
		this.id = id;
		this.name = name;
		this.time = time;
		this.startTime = simClock.getCurrentTime() + time;
		this.processId = processId;
	}
	
	public long getTime() {
		return time;
	}
	
	public String getProcessId() {
		return processId;
	}
	
	public String getId() {
		return id;
	}
	
	public String toString() {
		return "id:" + id + ", name: "+ name +", time:" + time +", endTime:" + startTime + ", processId:" + processId;
	}
}




	


	
	

	
	
	