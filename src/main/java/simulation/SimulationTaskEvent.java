package simulation;

public class SimulationTaskEvent extends SimulationEvent {

	private long time;
	private String id;
	private String processId;
	private String resourceId;
	private SimulationClock simClock =  new SimulationClock();

	public SimulationTaskEvent(String id, String name, long time, String processId, String resourceId) {
		this.id = id;
		this.name = name;
		this.time = time;
		this.startTime = simClock.getCurrentTime();
		this.processId = processId;
		this.setResourceId(resourceId);
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
	
	public String getResourceId() {
		return resourceId;
	}

	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}
	
	public String toString() {
		return "id:" + id + ", name: "+ name +", time:" + time +", startTime:" + startTime + ", processId:" + processId + ", resourceId:" + resourceId;
	}

}




	


	
	

	
	
	