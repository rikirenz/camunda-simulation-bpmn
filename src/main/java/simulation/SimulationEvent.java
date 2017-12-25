package simulation;

public class SimulationEvent {

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
}