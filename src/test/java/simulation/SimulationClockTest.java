package simulation;

import junit.framework.TestCase;
import util.CleanUp;

public class SimulationClockTest  extends TestCase {

	public void testAddAndGetTime() {
		try {
			SimulationClock simClock = new SimulationClock();
			simClock.addTime(10);
			int simulationTime = simClock.getCurrentTime();		
	        assertEquals(10, simulationTime);
		} finally {
			CleanUp.resetSimulationClock();	
		}
	}	
}
