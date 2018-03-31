package executionlisteners;

import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

import javax.xml.bind.JAXBElement;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.ExecutionListener;

import bpsim.ElementParameters;
import bpsim.TriangularDistribution;
import enso.EnsoApp;
import simulation.EventsHandler;
import util.BpsimQueryTool;

public class ExecutionListenerGateway implements ExecutionListener {
    
	protected final static Logger LOGGER = Logger.getLogger("ENSO-APP");
	private EventsHandler eventsHandler = EventsHandler.getInstance();
	
	public void notify(DelegateExecution execution) throws Exception {
		String currentId = execution.getCurrentActivityId();
		JAXBElement<TriangularDistribution> distribution = null;
	
		// Search all the parameters for a given element
		// 				we miss this part
		
		// set the variable in camunda
		Random randomGenerator = new Random();
		execution.setVariable("shipOrPay", randomGenerator.nextInt(2));
		
	}
}
