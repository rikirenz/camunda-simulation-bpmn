package simulation;

import junit.framework.TestCase;

public class SimulationClockTest  extends TestCase {

	public void testAddAndGetTime() {
		SimulationClock simClock = new SimulationClock();
		simClock.addTime(10);
		int simulationTime = simClock.getCurrentTime();		
        assertEquals(10, simulationTime);
	}
	
}
