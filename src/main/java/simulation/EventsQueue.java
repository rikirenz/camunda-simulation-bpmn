package simulation;

import java.util.Observable;
import java.util.concurrent.PriorityBlockingQueue;


public class EventsQueue extends Observable {

	private static PriorityBlockingQueue<SimulationEvent> eventsQueue = new PriorityBlockingQueue<SimulationEvent>();

	public EventsQueue() { }
	
	public void add(SimulationEvent currentEvent) {		
		eventsQueue.add(currentEvent);
		notifyObservers("An element has been added to the queue");
	}

	public SimulationEvent remove() {		
		return eventsQueue.remove();
	
	}
}
