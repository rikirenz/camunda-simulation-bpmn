package simulation;

import junit.framework.TestCase;

public class SimulationEventTest extends TestCase{

	public void testGetTime() {
		SimulationEvent se = new SimulationEvent(null, 10);
		int simulationEventTime = se.getTime();
		assertEquals(10, simulationEventTime);
	}

	public void testGetDescription() {
		SimulationEvent se = new SimulationEvent("simulation event test", 0);
		String simulationEventDescritpion = se.getDescription();
		assertEquals("simulation event test", simulationEventDescritpion);
	}
	
	public void testCompareTo() {
		SimulationEvent se1 = new SimulationEvent("simulation event test 1", 10);
		SimulationEvent se2 = new SimulationEvent("simulation event test 2", 11);
		int comparingTwoSimulationEvents = se1.compareTo(se2);
		assertEquals(0, comparingTwoSimulationEvents);
	}
	
}
