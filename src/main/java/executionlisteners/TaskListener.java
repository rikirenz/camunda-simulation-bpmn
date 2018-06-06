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

	public void notify(DelegateExecution execution) throws Exception {

		LOGGER.info("TASK: " + execution.getCurrentActivityName());

		costParameters = (CostParametersWrapper) Util.retriveParamaterType(execution.getCurrentActivityId(),
				CostParametersWrapper.class);
		timeParameters = (TimeParametersWrapper) Util.retriveParamaterType(execution.getCurrentActivityId(),
				TimeParametersWrapper.class);
		controlParameters = (ControlParametersWrapper) Util.retriveParamaterType(execution.getCurrentActivityId(),
				ControlParametersWrapper.class);
		priorityParameters = (PriorityParametersWrapper) Util.retriveParamaterType(execution.getCurrentActivityId(),
				PriorityParametersWrapper.class);

		// element not defined
		if (costParameters == null || timeParameters == null || controlParameters == null
				|| priorityParameters == null) {
			throw new Exception(
					"The Parameters for the task:" + execution.getCurrentActivityId() + " are not well defined.");
		}

		Long totalTime = calculateTaskTime(timeParameters);
		Double totalCost = calculateTaskCost(costParameters);

		interTriggerTimer = controlParameters.getInterTriggerTimer(); // ?
		triggerCount = controlParameters.getTriggerCount(); // ?

		interruptible = priorityParameters.getInterruptible();
		priority = priorityParameters.getPriority();

		LOGGER.info("TASK: " + execution.getCurrentActivityName() + " TOTAL TIME: " + totalTime);

		// boundary event section
		if (BpsimCollection.boundaryEvents.get(execution.getCurrentActivityId()) != null) {
			// find the boundary events
			LOGGER.info("Boundary Events:"
					+ BpsimCollection.boundaryEvents.get(execution.getCurrentActivityId()).toString());
			// get the bpsim information

			for (String currBoundary : BpsimCollection.boundaryEvents.get(execution.getCurrentActivityId())) {
				ControlParametersWrapper boundaryControlParameters = (ControlParametersWrapper) Util
						.retriveParamaterType(currBoundary, ControlParametersWrapper.class);
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
