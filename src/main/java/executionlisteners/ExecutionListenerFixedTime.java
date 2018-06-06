package executionlisteners;

import java.util.logging.Logger;

import org.camunda.bpm.engine.delegate.DelegateExecution;

import simulation.EventsHandler;

public class ExecutionListenerFixedTime extends ExecutionListenerMaster{
	
	protected final static Logger LOGGER = Logger.getLogger("ENSO-APP");
	private EventsHandler eventsHandler = EventsHandler.getInstance();

	
	@Override
	public void notify(DelegateExecution execution) throws Exception {				
		int fixedTime = 10;
		eventsHandler.addTaskEvent(execution.getCurrentActivityName(), fixedTime, execution.getProcessInstanceId());
	}
}
