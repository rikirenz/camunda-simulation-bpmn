package simulation;

import java.util.LinkedList;
import java.util.Queue;

import interfaces.Observer;
import interfaces.Subject;

public class EventsHandler implements Observer{

	private EventsQueue eventsQueue = new EventsQueue();
	private SimulationClock simClock = new SimulationClock();
	
	public EventsHandler() { 
		eventsQueue.register(this);
	}
	
	public void update(String description) {
		SimulationEvent currEvent = eventsQueue.remove();
		simClock.addTime(currEvent.getTime());
	}
}
