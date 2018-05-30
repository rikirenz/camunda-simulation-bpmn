package executionlisteners;

import java.util.logging.Logger;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.ExecutionListener;

import bpsimWrappers.ResourceParametersWrapper;
import simulation.EventsHandler;
import util.Util;

public class ResourceRoleListener implements ExecutionListener {
    
	protected final static Logger LOGGER = Logger.getLogger("ENSO-APP");
	private EventsHandler eventsHandler = EventsHandler.getInstance();

	private ResourceParametersWrapper resourceParameters;
	private String selection;
	
	public void notify(DelegateExecution execution) throws Exception {			
		LOGGER.info("ResourceRoleListener, id:" + execution.getCurrentActivityName());
		resourceParameters = (ResourceParametersWrapper) Util.retriveParamaterType(execution.getCurrentActivityId(), ResourceParametersWrapper.class);
		
		// element not defined
		if (resourceParameters == null) return;
		selection = resourceParameters.getSelection();
	}
}
