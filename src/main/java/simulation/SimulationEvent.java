package simulation;

public class SimulationEvent implements Comparable<SimulationEvent> {

	private String name;
	private int time;
	private int endTime;
	private SimulationClock simClock =  new SimulationClock();
	
	public SimulationEvent(String name, int time) {
		this.name = name;
		this.time = time;
		this.endTime = simClock.getCurrentTime() + time;
	}
	
	public int getTime() {
		return time;
	}

	public int getEndTime() {
		return endTime;
	}

	public String getName() {
		return name;
	}
	
	public int compareTo(SimulationEvent se) {
		return 0;
	}
	
	public String toString() {
		return "name: "+ name +", time:" + time +", endTime:" + endTime;
	}
}