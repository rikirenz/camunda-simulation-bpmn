package simulation;

import java.util.logging.Logger;

public class SimulationClock {

	private final static Logger LOGGER = Logger.getLogger("ENSO-APP");
	private static int simulationTime = 0;
	private final Object MUTEXTIME = new Object();

	public SimulationClock() {
	}

	public int getCurrentTime() {
		return simulationTime;
	}

	public void setCurrentTime(int simulationTime) {
		synchronized (MUTEXTIME) {
			this.simulationTime = simulationTime;
		}
		LOGGER.info("Simulation time: " + this.simulationTime);
	}

	public void resetTime() {
		simulationTime = 0;
	}
}
