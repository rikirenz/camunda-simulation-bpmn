package executionlisteners;

import java.util.logging.Logger;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.ExecutionListener;

import bpsimWrappers.ControlParametersWrapper;
import bpsimWrappers.CostParametersWrapper;
import bpsimWrappers.PriorityParametersWrapper;
import bpsimWrappers.ResourceParametersWrapper;
import bpsimWrappers.TimeParametersWrapper;
import enso.EnsoApp;
import simulation.SimulationCosts;
import simulation.EventsHandler;
import simulation.ResultsCatalog;
import simulation.SimulationResource;
import simulation.SimulationClock;
import util.BpsimCollection;
import util.Util;

public class TaskListener implements ExecutionListener {

	protected final static Logger LOGGER = Logger.getLogger("ENSO-APP");
	private EventsHandler eventsHandler = EventsHandler.getInstance();
	private SimulationClock simClock =  new SimulationClock();
	
	private ControlParametersWrapper controlParameters;
	private CostParametersWrapper costParameters;
	private PriorityParametersWrapper priorityParameters;
	private TimeParametersWrapper timeParameters;
	private ResourceParametersWrapper resourceParameters;

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
		resourceParameters = (ResourceParametersWrapper) Util.retriveParamaterType(execution.getCurrentActivityId(),
				ResourceParametersWrapper.class);
		
		String resourceId = "";
		if (resourceParameters != null) resourceId = resourceParameters.getSelection();
		
		// do calcutions using the parameters

		Double resultUnitCost = 0.0;
		Double resultFixedCost = 0.0;

		if (costParameters != null) {
			calculateTaskCost(costParameters);
			resultUnitCost = costParameters.getUnitCost();
			resultFixedCost = costParameters.getFixedCost();
		}			
		
		if (timeParameters != null) totalTime = calculateTaskTime(timeParameters);
		if (controlParameters != null) {
			interTriggerTimer = controlParameters.getInterTriggerTimer();
			triggerCount = controlParameters.getTriggerCount(); 
			interruptible = priorityParameters.getInterruptible();
			priority = priorityParameters.getPriority();			
		}	

		// boundary event section
		if (BpsimCollection.boundaryEvents.get(execution.getCurrentActivityId()) != null) {
			// find the boundary events
			LOGGER.info("Boundary Events:" + BpsimCollection.boundaryEvents.get(execution.getCurrentActivityId()).toString());

			for (String currBoundary : BpsimCollection.boundaryEvents.get(execution.getCurrentActivityId())) {
				ControlParametersWrapper boundaryControlParameters = (ControlParametersWrapper) Util.retriveParamaterType(currBoundary, ControlParametersWrapper.class);
				// get the probabilities
				// put the event in the queue
				eventsHandler.addBoundaryEvent(currBoundary, totalTime, execution.getProcessInstanceId(), simClock.getCurrentTime());
				return;
			}
		}
		// add event to the queue
		eventsHandler.addTaskEvent(execution.getActivityInstanceId(), execution.getCurrentActivityName(), totalTime, execution.getProcessInstanceId(), resourceId);
		
		ResultsCatalog foo = new ResultsCatalog();
		foo.addResult(execution.getProcessInstanceId(), execution.getActivityInstanceId(), resourceId, resultFixedCost, resultUnitCost, totalTime);

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

	public void calculateTaskCost(CostParametersWrapper costParameters) throws Exception {
		SimulationCosts costs = new SimulationCosts();
		costs.addFixedCost(costParameters.getFixedCost());
		costs.addUnitCost(costParameters.getUnitCost());
	}

}
