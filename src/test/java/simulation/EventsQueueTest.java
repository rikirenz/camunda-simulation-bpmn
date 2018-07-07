package simulation;

import java.util.logging.Logger;

import junit.framework.TestCase;
import testutils.CleanUp;

public class EventsQueueTest extends TestCase{
	
	private final static Logger LOGGER = Logger.getLogger("ENSO-APP");
	
	public void testAddRemove() {
		try {
			EventsQueue eq = new EventsQueue();		
			eq.add(new SimulationTaskEvent("", "", 0, "processId", ""));
			SimulationTaskEvent se = (SimulationTaskEvent) eq.remove();
			
			assertEquals("", se.getName());
			assertEquals(0, se.getTime());			
		} finally {
			CleanUp.resetSimulationClock();
		}
	}	
	
	public void testAddRemoveWithPriority() {
		try {
			EventsQueue eq = new EventsQueue();		
			eq.add(new SimulationStartEvent("test1", 0));
			eq.add(new SimulationTaskEvent("test5", "test5", 22, "processId", ""));
			eq.add(new SimulationStartEvent("test2", 10));
			eq.add(new SimulationStartEvent("test3", 20));
			eq.add(new SimulationTaskEvent("test4", "test4", 15, "processId", ""));
			eq.add(new SimulationTaskEvent("test6", "test6", 25, "processId", ""));
			
			SimulationEvent se = (SimulationEvent) eq.remove();			
			assertEquals("test1", se.getName());
			se = (SimulationEvent) eq.remove();		
			assertEquals("test2", se.getName());
			se = (SimulationEvent) eq.remove();
			assertEquals("test4", se.getName());
			se = (SimulationEvent) eq.remove();
			assertEquals("test3", se.getName());
			se = (SimulationEvent) eq.remove();
			assertEquals("test5", se.getName());
			se = (SimulationEvent) eq.remove();
			assertEquals("test6", se.getName());
			
		} finally {
			CleanUp.resetSimulationClock();
		}
	}
	
	
	public void testAddRemoveCatchEventsWithPriority() {
		try {
			EventsQueue eq = new EventsQueue();		
			eq.add(new SimulationStartEvent("test1", 0));
			eq.add(new SimulationTaskEvent("", "test5", 22, "processId", ""));
			eq.add(new SimulationStartEvent("test2", 10));
			eq.add(new SimulationStartEvent("test3", 20));
			eq.add(new SimulationTaskEvent("test4", "test4", 15, "processId", ""));
			eq.add(new SimulationTaskEvent("test6", "test6", 25, "processId", ""));
			eq.add(new SimulationCatchEvent("test7", 23, "processId", 23, 2));
			eq.add(new SimulationCatchEvent("test8", 24, "processId", 24, 1));
			
			SimulationEvent se = (SimulationEvent) eq.remove();			
			assertEquals("test1", se.getName());
			se = (SimulationEvent) eq.remove();		
			assertEquals("test2", se.getName());
			se = (SimulationEvent) eq.remove();
			assertEquals("test4", se.getName());
			se = (SimulationEvent) eq.remove();
			assertEquals("test3", se.getName());
			se = (SimulationEvent) eq.remove();
			assertEquals("test5", se.getName());
			se = (SimulationEvent) eq.remove();
			assertEquals("test7", se.getName());
			se = (SimulationEvent) eq.remove();
			assertEquals("test8", se.getName());
			se = (SimulationEvent) eq.remove();
			assertEquals("test6", se.getName());
			
		} finally {
			CleanUp.resetSimulationClock();
		}
	}
}
