package executionlisteners;

import java.util.logging.Logger;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.ExecutionListener;

import bpsimWrappers.ControlParametersWrapper;
import bpsimWrappers.CostParametersWrapper;
import bpsimWrappers.PriorityParametersWrapper;
import bpsimWrappers.TimeParametersWrapper;
import simulation.EventsHandler;
import util.Util;

public class TransactionListener  implements ExecutionListener {
    
	protected final static Logger LOGGER = Logger.getLogger("ENSO-APP");
	private EventsHandler eventsHandler = EventsHandler.getInstance();

	private ControlParametersWrapper controlParameters;
	private CostParametersWrapper costParameters;
	private PriorityParametersWrapper priorityParameters;
	private TimeParametersWrapper timeParameters;

	private Long waitTime;
	private Long setupTime;	
	private Long processingTime;
	private Long validationTime;
	private Long reworkTime;
	
	private Double interTriggerTimer;
	private Long triggerCount;
	
	private Double fixedCost;
	private Double unitCost;
	
	private Boolean interruptible;
	private Long priority;

	public void notify(DelegateExecution execution) throws Exception {		
				LOGGER.info(this.getClass().getName() + " " + execution.getCurrentActivityId());
		costParameters = (CostParametersWrapper) Util.retriveParamaterType(execution.getCurrentActivityId(), CostParametersWrapper.class);
		timeParameters = (TimeParametersWrapper) Util.retriveParamaterType(execution.getCurrentActivityId(), TimeParametersWrapper.class);
		controlParameters = (ControlParametersWrapper) Util.retriveParamaterType(execution.getCurrentActivityId(), ControlParametersWrapper.class);
		priorityParameters = (PriorityParametersWrapper) Util.retriveParamaterType(execution.getCurrentActivityId(), PriorityParametersWrapper.class);
		
		// element not defined
		if (costParameters == null) return;
		if (timeParameters == null) return;
		if (controlParameters == null) return;
		if (priorityParameters == null) return;
	
		waitTime = timeParameters.getWaitTime();
		setupTime = timeParameters.getSetupTime();	
		processingTime = timeParameters.getProcessingTime();
		validationTime = timeParameters.getValidationTime();
		reworkTime = timeParameters.getReworkTime();
		
		interTriggerTimer = controlParameters.getInterTriggerTimer();
		triggerCount = controlParameters.getTriggerCount();
		
		fixedCost = costParameters.getFixedCost();
		unitCost = costParameters.getUnitCost();
		
		interruptible = priorityParameters.getInterruptible();
		priority = priorityParameters.getPriority();
	}

}
