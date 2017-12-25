package simulation;

import junit.framework.TestCase;

public class EventsQueueTest extends TestCase{
	
	public void testAddRemove() {
		EventsQueue eq = new EventsQueue();		
		eq.add(new SimulationEvent("", 0));
		SimulationEvent se = eq.remove();
		
		assertEquals("", se.getDescription());
		assertEquals(0, se.getTime());
	}	
}
