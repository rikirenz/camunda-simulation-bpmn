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
	
	public void addTaskEvent(String taskId, String taskName, long time, String processInstanceId, String resourceId) {
		SimulationEvent simEvnt = new SimulationTaskEvent(taskId, taskName, time, processInstanceId, resourceId);
		eventsQueue.add(simEvnt);
	}

	public void addIntermediateEvent(String eventName, long time, String processInstanceId) {
		SimulationEvent simEvnt = new SimulationIntermediateEvent(eventName, time, processInstanceId);
		eventsQueue.add(simEvnt);
		
	}
	
	public void addStartEvent(String taskName, long startTime) {
		SimulationEvent simEvnt = new SimulationStartEvent(taskName, startTime);
		eventsQueue.add(simEvnt);
	}

	public void addBoundaryEvent(String eventName, long time, String processInstanceId) {
		SimulationEvent simEvnt = new SimulationBoundaryEvent(eventName, time, processInstanceId);
		eventsQueue.add(simEvnt);
	}

}
