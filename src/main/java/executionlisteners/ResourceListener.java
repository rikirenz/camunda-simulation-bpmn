package executionlisteners;

import java.util.logging.Logger;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.ExecutionListener;

import bpsimWrappers.CostParametersWrapper;
import bpsimWrappers.ResourceParametersWrapper;
import simulation.EventsHandler;
import util.Util;

public class ResourceListener implements ExecutionListener {
    
	protected final static Logger LOGGER = Logger.getLogger("ENSO-APP");
	private EventsHandler eventsHandler = EventsHandler.getInstance();

	private ResourceParametersWrapper resourceParameters;
	private CostParametersWrapper costParameters;

	private Boolean availability;
	private Long quantity;
	private String role;
	private Double fixedCost;
	private Double unitCost;
	
	public void notify(DelegateExecution execution) throws Exception {	
		LOGGER.info("ResourceListener, id:" + execution.getCurrentActivityName());
				
		resourceParameters = (ResourceParametersWrapper) Util.retriveParamaterType(execution.getCurrentActivityId(), ResourceParametersWrapper.class);
		costParameters = (CostParametersWrapper) Util.retriveParamaterType(execution.getCurrentActivityId(), CostParametersWrapper.class);
		// element not defined
		if (resourceParameters == null) return;
		
		availability = resourceParameters.getAvailability();
		resourceParameters.getQuantity();
		role = resourceParameters.getRole();
		fixedCost = costParameters.getFixedCost();
		unitCost = costParameters.getUnitCost();
	}
}
