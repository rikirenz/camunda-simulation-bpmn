package testutils;

import simulation.SimulationClock;
import simulation.SimulationCosts;

/**
 *  This class has the responsability to clean 
 *  all the parameters 
 */
public final class CleanUp {

	/**
	 *  Reset the simulation Time and 
	 *  the simulation Costs to 0
	 */
    public static void resetSimulationClock() {
		SimulationClock simClock = new SimulationClock();
		simClock.resetTime();
		
		SimulationCosts simCosts = new SimulationCosts();
		simCosts.resetCost();
    }
    
}
