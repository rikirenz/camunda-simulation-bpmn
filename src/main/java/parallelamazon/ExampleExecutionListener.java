package parallelamazon;

import java.util.Random;
import java.util.logging.Logger;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.ExecutionListener;

import simulation.EventsHandler;
import simulation.EventsQueue;
import simulation.SimulationEvent;

public class ExampleExecutionListener implements ExecutionListener {
    
	private final static Logger LOGGER = Logger.getLogger("ENSO-APP");

	private EventsHandler eventsHandler = EventsHandler.getInstance();
	
	private EventsQueue eventsQueue = EventsQueue.getInstance();
	
	public void notify(DelegateExecution execution) throws Exception {
		Random randomGenerator = new Random();
		int randomTime = randomGenerator.nextInt(100);
		LOGGER.info("This task requires " + randomTime + " seconds");
		SimulationEvent simEvnt = new SimulationEvent("standard process", randomTime);
		eventsQueue.add(simEvnt);
	}
}
