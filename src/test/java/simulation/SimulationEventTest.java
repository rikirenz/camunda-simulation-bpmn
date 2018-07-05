package simulation;

import junit.framework.TestCase;
import testutils.CleanUp;

public class SimulationEventTest extends TestCase{

	public void testGetTime() {
		try {
			SimulationTaskEvent se = new SimulationTaskEvent("", null, 10, "processId");
			long simulationEventTime = se.getTime();
			assertEquals(10, simulationEventTime);
		} finally {
			CleanUp.resetSimulationClock();	
		}
	}

	public void testGetDescription() {
		try {
			SimulationTaskEvent se = new SimulationTaskEvent("simulation event test", "simulation event test", 0, "processId");
			String simulationEventDescritpion = se.getName();
			assertEquals("simulation event test", simulationEventDescritpion);
		} finally {
			CleanUp.resetSimulationClock();	
		}
	}
	
	public void testCompareTo() {
		try {
			SimulationTaskEvent se1 = new SimulationTaskEvent("simulation event test 1", "simulation event test 1", 10, "processId");
			SimulationTaskEvent se2 = new SimulationTaskEvent("simulation event test 2", "simulation event test 2", 11, "processId");
			int comparingTwoSimulationEvents = se1.compareTo(se2);
			assertEquals(0, comparingTwoSimulationEvents);
		} finally {
			CleanUp.resetSimulationClock();	
		}
	}
	
}
