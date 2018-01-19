package executionlisteners;

import java.util.logging.Logger;

import org.camunda.bpm.engine.delegate.DelegateExecution;

import simulation.EventsHandler;

public class ExecutionListenerDifferentTime extends ExecutionListenerMaster{
	
	protected final static Logger LOGGER = Logger.getLogger("ENSO-APP");
	private EventsHandler eventsHandler = EventsHandler.getInstance();

	static boolean changeTime = false;

	@Override
	public void notify(DelegateExecution execution) throws Exception {
		// @todo get the time from the variables of the bpmn
		
		int taskTime = (changeTime) ? 13 : 7;
		changeTime = !changeTime;
		
		eventsHandler.addTaskEvent(execution.getCurrentActivityName(), taskTime, execution.getProcessInstanceId());

	}
}
