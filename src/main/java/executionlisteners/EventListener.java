package executionlisteners;

import java.util.logging.Logger;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.ExecutionListener;

import bpsimWrappers.ControlParametersWrapper;
import simulation.EventsHandler;
import util.Util;

public class EventListener implements ExecutionListener {
    
	protected final static Logger LOGGER = Logger.getLogger("ENSO-APP");
	private EventsHandler eventsHandler = EventsHandler.getInstance();
	
	private Double interTriggerTimer;
	private Long triggerCount;
	private Double probability;	
	private Boolean condition;
	
	private ControlParametersWrapper controlParameters;

	public void notify(DelegateExecution execution) throws Exception {
		LOGGER.info("Event Name:" + execution.getCurrentActivityId());
		controlParameters = (ControlParametersWrapper) Util.retriveParamaterType(execution.getCurrentActivityId(), ControlParametersWrapper.class);
		// element not defined
		if (controlParameters == null) return;
		//interTriggerTimer = controlParameters.getInterTriggerTimer();
		probability = controlParameters.getProbability();
		//condition = controlParameters.getCondition();		
		
		eventsHandler.addIntermediateEvent(execution.getEventName(), 0, execution.getProcessInstanceId());
	}	
}
