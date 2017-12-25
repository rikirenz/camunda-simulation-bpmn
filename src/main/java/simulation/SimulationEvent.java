package simulation;

public class SimulationEvent implements Comparable<SimulationEvent> {

	private String description;
	private int time;
	
	public SimulationEvent(String description, int time) {
		this.description = description;
		this.time = time;
	}
	
	public int getTime() {
		return time;
	}

	public String getDescription() {
		return description;
	}
	
	public int compareTo(SimulationEvent se)
	{
		return 0;
	}
}