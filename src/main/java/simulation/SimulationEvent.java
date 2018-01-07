package simulation;

public class SimulationEvent implements Comparable<SimulationEvent> {

	private String taskId;
	private int time;
	private int endTime;
	private SimulationClock simClock =  new SimulationClock();
	
	public SimulationEvent(String taskId, int time) {
		this.taskId = taskId;
		this.time = time;
		this.endTime = simClock.getCurrentTime() + time;
	}
	
	public int getTime() {
		return time;
	}

	public int getEndTime() {
		return endTime;
	}

	public String getTaskId() {
		return taskId;
	}
	
	public int compareTo(SimulationEvent se) {
		return 0;
	}
	
	public String toString() {
		return "Description: "+ taskId +", time:" + time +", endTime:" + endTime;
	}
}