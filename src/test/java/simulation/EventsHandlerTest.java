package simulation;

import junit.framework.TestCase;
import util.CleanUp;

public class EventsHandlerTest extends TestCase{

	public void testUpdate() {
		try {
			// put an event in the queue
			SimulationEvent simEvnt = new SimulationEvent("test process", 10);
			EventsQueue eventsQueue = EventsQueue.getInstance();
			SimulationClock simClock = new SimulationClock();

			// put an event in the queue
			eventsQueue.add(simEvnt);

			// test current time
			assertEquals(10, simClock.getCurrentTime());
		} finally {
			CleanUp.resetSimulationClock();
		}
	}
}
