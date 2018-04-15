package executionlisteners;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

import javax.xml.bind.JAXBElement;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.ExecutionListener;

import bpsim.ElementParameters;
import bpsim.TriangularDistribution;
import bpsimWrappers.ControlParametersWrapper;
import bpsimWrappers.CostParametersWrapper;
import bpsimWrappers.PriorityParametersWrapper;
import bpsimWrappers.ResourceParametersWrapper;
import bpsimWrappers.TimeParametersWrapper;
import enso.EnsoApp;
import simulation.EventsHandler;
import util.BpsimCollection;
import util.BpsimQueryTool;

public class ExecutionListenerGateway implements ExecutionListener {
    
	protected final static Logger LOGGER = Logger.getLogger("ENSO-APP");
	private EventsHandler eventsHandler = EventsHandler.getInstance();
	
	public void notify(DelegateExecution execution) throws Exception {
		String currentId = execution.getCurrentActivityId();
		JAXBElement<TriangularDistribution> distribution = null;
	
		// Search all the parameters for a given element	
		ArrayList<Object> bpsimObjects = BpsimCollection.taskObjects.get(execution.getCurrentActivityId());
		for (Object currObject : bpsimObjects) {
			if (currObject instanceof ControlParametersWrapper) {
				ControlParametersWrapper currControlParameter = (ControlParametersWrapper) currObject;			
				execution.setVariable("shipOrPay", currControlParameter.getProbability());
			} else if (currObject instanceof CostParametersWrapper) {
				CostParametersWrapper costParameter = (CostParametersWrapper) currObject;
			} else if (currObject instanceof PriorityParametersWrapper) {
				PriorityParametersWrapper currPriorityParameter = (PriorityParametersWrapper) currObject;
			} else if (currObject instanceof ResourceParametersWrapper) {
				ResourceParametersWrapper currResourceParameter = (ResourceParametersWrapper) currObject;
			} else if (currObject instanceof TimeParametersWrapper) {
				TimeParametersWrapper currControlParameter = (TimeParametersWrapper) currObject;
			}
		}				
	}
}
