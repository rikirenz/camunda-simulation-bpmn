package executionlisteners;

import java.util.logging.Logger;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.ExecutionListener;

import bpsimWrappers.ControlParametersWrapper;
import simulation.EventsHandler;
import util.Util;

public class SequenceFlowListener  implements ExecutionListener {
    
	protected final static Logger LOGGER = Logger.getLogger("ENSO-APP");
	private EventsHandler eventsHandler = EventsHandler.getInstance();

	private ControlParametersWrapper controlParameters;
	private Double probability;	
	private Boolean condition;

	public void notify(DelegateExecution execution) throws Exception {			
		
		LOGGER.info("===================== Sequence flow =====================");
		
		controlParameters = (ControlParametersWrapper) Util.retriveParamaterType(execution.getCurrentTransitionId(), ControlParametersWrapper.class);
		
		// element not defined
		if (controlParameters == null) return;

		probability = controlParameters.getProbability();
		condition = controlParameters.getCondition();
	}

}
