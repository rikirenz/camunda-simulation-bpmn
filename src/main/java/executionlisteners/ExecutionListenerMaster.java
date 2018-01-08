package executionlisteners;

import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Logger;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.ExecutionListener;

import simulation.EventsHandler;
import simulation.EventsQueue;
import simulation.SimulationEvent;

public class ExecutionListenerMaster implements ExecutionListener {
    
	protected final static Logger LOGGER = Logger.getLogger("ENSO-APP");
	protected EventsQueue eventsQueue = EventsQueue.getInstance();
	
	public static ArrayList<String> waitingTasks = new ArrayList<String>();
	
	public void notify(DelegateExecution execution) throws Exception {
		
		Random randomGenerator = new Random();
		int randomTime = randomGenerator.nextInt(100);
		SimulationEvent simEvnt = new SimulationEvent(execution.getCurrentActivityName(), randomTime);		
		eventsQueue.add(simEvnt);
		LOGGER.info("Current Task: " + simEvnt.toString());
	}
}
