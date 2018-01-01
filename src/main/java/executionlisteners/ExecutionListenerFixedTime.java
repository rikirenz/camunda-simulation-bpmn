package executionlisteners;

import java.util.Random;

import org.camunda.bpm.engine.delegate.DelegateExecution;

import simulation.SimulationEvent;

public class ExecutionListenerFixedTime extends ExecutionListenerMaster{

	@Override
	public void notify(DelegateExecution execution) throws Exception {
		// @todo get variable from the variables of the bpmn
		int fixedTime = 10;
		LOGGER.info("This task requires " + fixedTime + " seconds");
		SimulationEvent simEvnt = new SimulationEvent("standard process", fixedTime);
		eventsQueue.add(simEvnt);
	}
}
