package simulation;

import junit.framework.TestCase;
import util.CleanUp;

public class EventsQueueTest extends TestCase{
	
	public void testAddRemove() {
		try {
			EventsQueue eq = new EventsQueue();		
			eq.add(new SimulationEvent("", 0));
			SimulationEvent se = eq.remove();
			
			assertEquals("", se.getName());
			assertEquals(0, se.getTime());			
		} finally {
			CleanUp.resetSimulationClock();
		}
	}	
}
