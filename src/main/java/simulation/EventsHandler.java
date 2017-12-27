package simulation;

import java.util.Observable;
import java.util.Observer;
import java.util.logging.Logger;


public class EventsHandler implements Observer {

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
	
	protected EventsHandler() { 
		eventsQueue.addObserver(this);
	}
	
	public void update(Observable o, Object arg) {
		SimulationEvent currEvent = eventsQueue.remove();
		simClock.addTime(currEvent.getTime());
		LOGGER.info("Simulation Event: " + currEvent.getDescription() + " has been processed");
	}
}
