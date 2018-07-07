package simulation;

import junit.framework.TestCase;
import testutils.CleanUp;

public class EventsHandlerTest extends TestCase{

	public void testAddEvent() {
		try {
			SimulationClock simClock = new SimulationClock();
			EventsHandler eventsHandler = EventsHandler.getInstance();
			eventsHandler.addTaskEvent("test process", "test process", 10, "processId", "");
			
			EventsQueue eventsQueue = EventsQueue.getInstance();

			SimulationTaskEvent currEvent = (SimulationTaskEvent) eventsQueue.remove();
			// test current time
			assertEquals(10, currEvent.getTime());
		} finally {
			CleanUp.resetSimulationClock();
		}
	}
}
