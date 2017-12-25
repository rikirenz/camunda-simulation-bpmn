package simulation;

import java.util.logging.Logger;

public class SimulationClock {

	private final static Logger LOGGER = Logger.getLogger("AMAZON-DELIVERY");	
	private static int simulationTime = 0;
	private final Object MUTEXTIME = new Object();

	public SimulationClock() {}
	
	public void addTime(int time) {
		synchronized (MUTEXTIME) {
			simulationTime += time;
		}
		LOGGER.info("Simulation time: " + simulationTime);
	}
	
	public int getCurrentTime() {
		return simulationTime;
	}	
}
