package simulation;

import java.util.Observable;
import java.util.Observer;


public class EventsHandler implements Observer {

	private EventsQueue eventsQueue = new EventsQueue();
	private SimulationClock simClock = new SimulationClock();
	
	public EventsHandler() { 
		eventsQueue.addObserver(this);
	}
	
	public void update(Observable o, Object arg) {
		SimulationEvent currEvent = eventsQueue.remove();
		simClock.addTime(currEvent.getTime());		
	}
}
