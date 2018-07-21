package testutils;

import java.util.ArrayList;

import simulation.SimulationCatchEvent;
import simulation.SimulationClock;
import simulation.SimulationCosts;
import util.BpsimCollection;

/**
 *  This class has the responsability to clean 
 *  all the parameters 
 */
public final class CleanUp {

	/**
	 *  Reset the simulation Time and 
	 *  the simulation Costs to 0
	 */
    public static void resetSimulation() {
		SimulationClock simClock = new SimulationClock();
		simClock.resetTime();
		
		SimulationCosts simCosts = new SimulationCosts();
		simCosts.resetCost();
		
		BpsimCollection.indipendentIntermediateThrowEvents = new ArrayList<SimulationCatchEvent>();
    }
    
}
