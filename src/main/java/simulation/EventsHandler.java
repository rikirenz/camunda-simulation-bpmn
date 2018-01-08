package simulation;

import java.util.logging.Logger;

import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.task.Task;



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


	public void update(TaskService taskService) {
		while (!eventsQueue.isEmpty()) {
			SimulationEvent currEvent = eventsQueue.remove();
			// if end time more than current time skip
			if (currEvent.getEndTime() > simClock.getCurrentTime()) simClock.setCurrentTime(currEvent.getEndTime());
			
			// move on with the simulation.			
			Task currTask = taskService.createTaskQuery().taskName(currEvent.getName()).singleResult();
			taskService.complete(currTask.getId());
		}
	}
}
