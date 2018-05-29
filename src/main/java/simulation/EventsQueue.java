package simulation;

import java.util.concurrent.PriorityBlockingQueue;
import java.util.logging.Logger;

import org.camunda.bpm.engine.task.Task;

public class EventsQueue {

	private final static Logger LOGGER = Logger.getLogger("ENSO-APP");
	private static PriorityBlockingQueue<SimulationEvent> eventsQueue = new PriorityBlockingQueue<SimulationEvent>(1, new SimulationEventsComparator());
	private static EventsQueue instance;
	
	protected EventsQueue() { }
	
	public static EventsQueue getInstance() {
	   if(instance == null) {
		   instance = new EventsQueue();
	   }
	   return instance;
	}
	
	public void add(SimulationEvent currentEvent) {		
		eventsQueue.add(currentEvent);
	}

	public SimulationEvent remove() {		
		return eventsQueue.remove();
	}
	
	public boolean isEmpty() {
		return eventsQueue.isEmpty();
	}
	
	public int size() {
		return eventsQueue.size();
	}
	
	public void removeEventsByProcessId(String processId) {
		for (SimulationEvent currEvent : eventsQueue) {
			if (currEvent instanceof SimulationTaskEvent) {
				SimulationTaskEvent currTaskEvent = (SimulationTaskEvent) currEvent;
				if (currTaskEvent.getProcessId().equals(processId)) eventsQueue.remove(currTaskEvent);
			}
		} 
	}
}
