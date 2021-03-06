package executionlisteners;

import java.util.Random;
import java.util.logging.Logger;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.ExecutionListener;

import simulation.EventsHandler;

public class ExecutionListenerMaster implements ExecutionListener {
    
	protected final static Logger LOGGER = Logger.getLogger("ENSO-APP");
	private EventsHandler eventsHandler = EventsHandler.getInstance();
	
	public void notify(DelegateExecution execution) throws Exception {
		LOGGER.info(execution.getCurrentActivityName());
		Random randomGenerator = new Random();
		int randomTime = randomGenerator.nextInt(100);
		eventsHandler.addTaskEvent(execution.getActivityInstanceId(), execution.getCurrentActivityName(), randomTime, execution.getProcessInstanceId(), "");
	}
}
