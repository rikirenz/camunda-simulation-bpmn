package simulation;

import java.util.logging.Logger;

public class SimulationClock {

	private final static Logger LOGGER = Logger.getLogger("ENSO-APP");
	private static long simulationTime = 0;
	private final Object MUTEXTIME = new Object();

	public SimulationClock() {
	}

	public long getCurrentTime() {
		return simulationTime;
	}

	public void setCurrentTime(long simulationTime) {
		synchronized (MUTEXTIME) {
			this.simulationTime = simulationTime;
		}
	}

	public void resetTime() {
		simulationTime = 0;
	}
}
