package executionlisteners;

import java.util.logging.Logger;

import org.camunda.bpm.engine.delegate.DelegateExecution;

import simulation.EventsHandler;

public class ExecutionListenerFixedTime extends ExecutionListenerMaster{
	
	protected final static Logger LOGGER = Logger.getLogger("ENSO-APP");
	private EventsHandler eventsHandler = EventsHandler.getInstance();

	
	@Override
	public void notify(DelegateExecution execution) throws Exception {
				LOGGER.info(this.getClass().getName() + " " + execution.getCurrentActivityId());
		// @todo get the time from the variables of the bpmn
		int fixedTime = 10;
		eventsHandler.addTaskEvent(execution.getCurrentActivityName(), fixedTime, execution.getProcessInstanceId());
	}
}
