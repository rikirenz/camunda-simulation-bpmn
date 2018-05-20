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
	
	public void addEndEvent(String eventName, long time, String processInstanceId) {
		SimulationEvent simEvnt = new SimulationEndEvent(eventName, time, processInstanceId);
		eventsQueue.add(simEvnt);
	}

	public void addTaskEvent(String taskName, long time, String processInstanceId) {
		SimulationEvent simEvnt = new SimulationTaskEvent(taskName, time, processInstanceId);
		eventsQueue.add(simEvnt);
	}

	public void addStartEvent(String taskName, long startTime) {
		SimulationEvent simEvnt = new SimulationStartEvent(taskName, startTime);
		eventsQueue.add(simEvnt);
	}

}
