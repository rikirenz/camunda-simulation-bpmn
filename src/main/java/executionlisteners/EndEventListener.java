package executionlisteners;

import java.util.logging.Logger;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.ExecutionListener;

import simulation.EventsHandler;


public class EndEventListener implements ExecutionListener {
    
	protected final static Logger LOGGER = Logger.getLogger("ENSO-APP");
	private EventsHandler eventsHandler = EventsHandler.getInstance();

	public void notify(DelegateExecution execution) throws Exception {			
		LOGGER.info("===================== The process Ended =====================");
	}	
}
