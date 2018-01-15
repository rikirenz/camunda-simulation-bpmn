package simulation;

public class SimulationStartEvent extends SimulationEvent{
	
	public SimulationStartEvent(String name, int endTime) {
		this.name = name;
		this.endTime = endTime;
	}
}
