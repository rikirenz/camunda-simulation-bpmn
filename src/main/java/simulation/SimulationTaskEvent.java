package simulation;

public class SimulationTaskEvent extends SimulationEvent {

	private int time;
	private SimulationClock simClock =  new SimulationClock();

	public SimulationTaskEvent(String name, int time) {
		this.name = name;
		this.time = time;
		this.endTime = simClock.getCurrentTime() + time;
	}
	
	public int getTime() {
		return time;
	}
	
	public String toString() {
		return "name: "+ name +", time:" + time +", endTime:" + endTime;
	}
}




	


	
	

	
	
	