package executionlisteners;

import java.util.logging.Logger;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.ExecutionListener;

import bpsimWrappers.ControlParametersWrapper;
import simulation.EventsHandler;
import util.Util;

public class GatewayListener  implements ExecutionListener {
    
	protected final static Logger LOGGER = Logger.getLogger("ENSO-APP");
	private EventsHandler eventsHandler = EventsHandler.getInstance();

	private ControlParametersWrapper controlParameters;
	private Double interTriggerTimer;
	private Long triggerCount;
	
	public void notify(DelegateExecution execution) throws Exception {			
				
		controlParameters = (ControlParametersWrapper) Util.retriveParamaterType(execution.getCurrentActivityId(), ControlParametersWrapper.class);
		
		// element not defined
		if (controlParameters == null) return;
	
		interTriggerTimer = controlParameters.getInterTriggerTimer();
		triggerCount = controlParameters.getTriggerCount();
	}

}
