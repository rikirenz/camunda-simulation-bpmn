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

	public void addEvent(String taskName, int time) {
		SimulationEvent simEvnt = new SimulationEvent(taskName, time);
		eventsQueue.add(simEvnt);
		LOGGER.info("Current Task: " + simEvnt.toString());
	}

}
