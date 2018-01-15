package simulation;

import java.util.logging.Logger;

public class EventsHandler {

	private final static Logger LOGGER = Logger.getLogger("ENSO-APP");
	private static EventsHandler instance;
	
	private EventsQueue eventsQueue = EventsQueue.getInstance();	
	
	public static EventsHandler getInstance() {
	   if(instance == null) {
		   instance = new EventsHandler();
	   }
	   return instance;
	}

	public void addTaskEvent(String taskName, int time) {
		SimulationEvent simEvnt = new SimulationTaskEvent(taskName, time);
		eventsQueue.add(simEvnt);
		LOGGER.info("Current Task: " + simEvnt.toString());
	}

	public void addStartEvent(String taskName, int startTime) {
		SimulationEvent simEvnt = new SimulationStartEvent(taskName, startTime);
		eventsQueue.add(simEvnt);
		LOGGER.info("Current Task: " + simEvnt.toString());
	}

}
