package util;

import simulation.SimulationClock;

public final class CleanUp {

	// reset the simulation Time of the sim Clock to 0
    public static void resetSimulationClock() {
		SimulationClock simClock = new SimulationClock();
		simClock.resetTime();
    }
    
}
