package simulation;

import java.util.Observable;
import java.util.Observer;
import java.util.logging.Logger;

import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.runtime.ProcessInstanceModificationBuilder;


public class EventsHandler {

	private final static Logger LOGGER = Logger.getLogger("ENSO-APP");
	private static EventsHandler instance;
	
	private EventsQueue eventsQueue = EventsQueue.getInstance();
	private SimulationClock simClock = new SimulationClock();
	
	
	public static EventsHandler getInstance() {
	   if(instance == null) {
		   instance = new EventsHandler();
	   }
	   return instance;
	}


	public void update(RuntimeService runtimeService, String processId) {
		while (!eventsQueue.isEmpty()) {
			SimulationEvent currEvent = eventsQueue.remove();
			// if end time more than current time skip
			if (currEvent.getEndTime() > simClock.getCurrentTime()) simClock.setCurrentTime(currEvent.getEndTime());
			// move on with the simulation.

			runtimeService.createProcessInstanceModification(processId)
				.startAfterActivity(currEvent.getTaskId())
				.execute();

		}
	}
}
