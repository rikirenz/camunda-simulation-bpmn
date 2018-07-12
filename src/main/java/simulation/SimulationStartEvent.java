package simulation;

public class SimulationStartEvent extends SimulationEvent{
	
	public SimulationStartEvent(String name, long startTime) {
		this.name = name;
		this.startTime = startTime;
	}
}
