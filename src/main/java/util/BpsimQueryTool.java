package util;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import bpsim.BPSimData;
import bpsim.ElementParameters;
import bpsim.Scenario;

public class BpsimQueryTool {

	protected final static Logger LOGGER = Logger.getLogger("ENSO-APP");
	private BPSimData bpsim;
	
	public BpsimQueryTool(BPSimData currentBpsim) {
		bpsim = currentBpsim;
	}
	
	public List<ElementParameters> searchAllElementById(String elementId){
		if (elementId == null) throw new NullPointerException("Element Id cannot be null"); 

		List<ElementParameters> matchedElements = new ArrayList<ElementParameters>();
		List<Scenario> scenarioList = bpsim.getScenario();
		
		// Search all ElementParameters associated with the elementId
		for(Scenario currScenario : scenarioList) {
			for(ElementParameters currElement : currScenario.getElementParameters()) {
				if (elementId.equals(currElement.getElementRef().toString())) {
					matchedElements.add(currElement);
				}
			}
		} 
		
		return matchedElements;
	}
}
