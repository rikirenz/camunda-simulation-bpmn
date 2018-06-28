package executionlisteners;

import java.util.logging.Logger;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.ExecutionListener;

import bpsimWrappers.ControlParametersWrapper;
import bpsimWrappers.CostParametersWrapper;
import bpsimWrappers.PriorityParametersWrapper;
import bpsimWrappers.TimeParametersWrapper;
import enso.EnsoApp;
import simulation.EventsHandler;
import util.BpsimCollection;
import util.Util;

public class TaskListener implements ExecutionListener {

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
	private Long queueTime;
	private Long transferTime;

	private Double interTriggerTimer;
	private Long triggerCount;

	private Double fixedCost;
	private Double unitCost;

	private Boolean interruptible;
	private Long priority;
	
	private Long totalTime = (long) 0;
	private Double totalCost = 0.0;

	public void notify(DelegateExecution execution) throws Exception {
		LOGGER.info("TASK: " + execution.getCurrentActivityName());

		
		// load parameters
		costParameters = (CostParametersWrapper) Util.retriveParamaterType(execution.getCurrentActivityId(),
				CostParametersWrapper.class);
		timeParameters = (TimeParametersWrapper) Util.retriveParamaterType(execution.getCurrentActivityId(),
				TimeParametersWrapper.class);
		controlParameters = (ControlParametersWrapper) Util.retriveParamaterType(execution.getCurrentActivityId(),
				ControlParametersWrapper.class);
		priorityParameters = (PriorityParametersWrapper) Util.retriveParamaterType(execution.getCurrentActivityId(),
				PriorityParametersWrapper.class);


		// do calcutions using the parameters
		if (timeParameters != null) totalTime = calculateTaskTime(timeParameters);
		if (costParameters != null) totalCost = calculateTaskCost(costParameters);
		if (controlParameters != null) {
			interTriggerTimer = controlParameters.getInterTriggerTimer();
			triggerCount = controlParameters.getTriggerCount(); 
			interruptible = priorityParameters.getInterruptible();
			priority = priorityParameters.getPriority();			
		}

		// boundary event section
		if (BpsimCollection.boundaryEvents.get(execution.getCurrentActivityId()) != null) {
			// find the boundary events
			LOGGER.info("Boundary Events:"
					+ BpsimCollection.boundaryEvents.get(execution.getCurrentActivityId()).toString());

			for (String currBoundary : BpsimCollection.boundaryEvents.get(execution.getCurrentActivityId())) {
				ControlParametersWrapper boundaryControlParameters = (ControlParametersWrapper) Util.retriveParamaterType(currBoundary, ControlParametersWrapper.class);
				// get the probabilities
				// boundaryControlParameters.getCondition();
				// boundaryControlParameters.getProbability();
				// put the event in the queue
				eventsHandler.addBoundaryEvent(currBoundary, totalTime, execution.getProcessInstanceId());
				return;
			}
		}

		// add event to the queue
		eventsHandler.addTaskEvent(execution.getCurrentActivityName(), totalTime, execution.getProcessInstanceId());
	}

	public Long calculateTaskTime(TimeParametersWrapper currTimeParameters) throws Exception {
		waitTime = timeParameters.getWaitTime();
		setupTime = timeParameters.getSetupTime();
		processingTime = timeParameters.getProcessingTime();
		validationTime = timeParameters.getValidationTime();
		reworkTime = timeParameters.getReworkTime();
		reworkTime = timeParameters.getQueueTime();
		queueTime = timeParameters.getQueueTime();
		transferTime = timeParameters.getTransferTime();

		return waitTime + setupTime + processingTime + validationTime + reworkTime + queueTime + transferTime;
	}

	public Double calculateTaskCost(CostParametersWrapper costParameters) throws Exception {
		fixedCost = costParameters.getFixedCost();
		unitCost = costParameters.getUnitCost();

		return unitCost + fixedCost;
	}

}
