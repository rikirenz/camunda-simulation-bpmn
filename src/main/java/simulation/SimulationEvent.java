package simulation;

public abstract class SimulationEvent implements Comparable<SimulationEvent> {

	protected String name;
	protected int endTime;
	
	public SimulationEvent() {}
	
	public SimulationEvent(String name, int endTime) {
		this.name = name;
		this.endTime = endTime;
	}
	
	public String getName() {
		return name;
	}
	
	public int getEndTime() {
		return endTime;
	}
	
	public int compareTo(SimulationEvent se) {
		return 0;
	}
	
	public String toString() {
		return "name: "+ name + " EndTime" + endTime;
	}
}