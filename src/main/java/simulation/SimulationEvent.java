package simulation;

public abstract class SimulationEvent implements Comparable<SimulationEvent> {

	protected String name;
	protected long startTime;
	
	public SimulationEvent() {}
	
	public SimulationEvent(String name, int startTime) {
		this.name = name;
		this.startTime = startTime;
	}
	
	public String getName() {
		return name;
	}
	
	public long getStartTime() {
		return startTime;
	}
	
	public int compareTo(SimulationEvent se) {
		return 0;
	}
	
	public String toString() {
		return "name: "+ name + " startTime" + startTime;
	}
}