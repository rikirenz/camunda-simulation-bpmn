package simulation;

import junit.framework.TestCase;
import util.CleanUp;

public class SimulationEventTest extends TestCase{

	public void testGetTime() {
		try {
			SimulationEvent se = new SimulationEvent(null, 10);
			int simulationEventTime = se.getTime();
			assertEquals(10, simulationEventTime);
		} finally {
			CleanUp.resetSimulationClock();	
		}
	}

	public void testGetDescription() {
		try {
			SimulationEvent se = new SimulationEvent("simulation event test", 0);
			String simulationEventDescritpion = se.getName();
			assertEquals("simulation event test", simulationEventDescritpion);
		} finally {
			CleanUp.resetSimulationClock();	
		}
	}
	
	public void testCompareTo() {
		try {
			SimulationEvent se1 = new SimulationEvent("simulation event test 1", 10);
			SimulationEvent se2 = new SimulationEvent("simulation event test 2", 11);
			int comparingTwoSimulationEvents = se1.compareTo(se2);
			assertEquals(0, comparingTwoSimulationEvents);
		} finally {
			CleanUp.resetSimulationClock();	
		}
	}
	
}
