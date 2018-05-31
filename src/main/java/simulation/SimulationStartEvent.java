package simulation;

public class SimulationStartEvent extends SimulationEvent{
	
	public SimulationStartEvent(String name, long endTime) {
		this.name = name;
		this.startTime = endTime;
	}
}
