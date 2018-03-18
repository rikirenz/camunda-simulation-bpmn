package executionlisteners;

import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

import javax.xml.bind.JAXBElement;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.ExecutionListener;

import bpsim.ElementParameters;
import bpsim.TriangularDistribution;;
import enso.EnsoApp;
import simulation.EventsHandler;

public class ExecutionListenerGateway implements ExecutionListener {
    
	protected final static Logger LOGGER = Logger.getLogger("ENSO-APP");
	private EventsHandler eventsHandler = EventsHandler.getInstance();
	
	public void notify(DelegateExecution execution) throws Exception {
		ElementParameters test = EnsoApp.bpsimData.getScenario().get(0).getElementParameters().get(0);		
		JAXBElement<TriangularDistribution> distribution = (JAXBElement<TriangularDistribution>) test.getControlParameters().getProbability().getParameterValue().get(0);
		LOGGER.info("Gateway Id:" + execution.getCurrentActivityId());
		LOGGER.info("Gateway Distribution:" + distribution.getValue().getMax() + " " + distribution.getValue().getMin() + " " + distribution.getValue().getMode());

		Random randomGenerator = new Random();
		int randomTime = randomGenerator.nextInt(2);
		
		execution.setVariable("x", randomTime);
		
	}
}
