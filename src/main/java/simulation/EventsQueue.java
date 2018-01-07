package simulation;

import java.util.Observable;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.logging.Logger;


public class EventsQueue {

	private final static Logger LOGGER = Logger.getLogger("ENSO-APP");
	private static PriorityBlockingQueue<SimulationEvent> eventsQueue = new PriorityBlockingQueue<SimulationEvent>();
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
	
}
