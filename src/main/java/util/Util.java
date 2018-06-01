package util;

import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Logger;

import bpsimWrappers.ControlParametersWrapper;
import bpsimWrappers.CostParametersWrapper;
import bpsimWrappers.ParametersWrapper;

public class Util {

	protected final static Logger LOGGER = Logger.getLogger("ENSO-APP");
	private static Random random = new Random();

	public static ParametersWrapper retriveParamaterType(String id, Class<?> clsType) {
		// Search all the parameters for a given element
		try {
			ArrayList<Object> bpsimObjects = BpsimCollection.taskObjects.get(id); 
			for (Object currObject : bpsimObjects) {
				if (clsType.isInstance(currObject)) {
					return (ParametersWrapper) currObject;
				}
			}
			throw new Exception();
		} catch (Exception ex) {		
			//LOGGER.info("There are no Bpsim objects defined for this element.");
			return null;					
		}
	}

	public static boolean booleanValueFlow(String elementId) {
		try {
			ControlParametersWrapper controlParameters = (ControlParametersWrapper) retriveParamaterType(elementId, ControlParametersWrapper.class);
			// if there is no object exception with distribution or boolean 
			if (controlParameters == null ) throw new Exception("The Parameters for the out flow:" + elementId + " are not well defined.");
			
			if (controlParameters.getProbability() != null) return random.nextBoolean();
			if (controlParameters.getCondition() != null) return controlParameters.getCondition();
			//if (controlParameters.getProbability() != null) return controlParameters.getProbability();

			throw new Exception("The Parameters for the out-flow element:" + elementId + " are not well defined.");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	return false;
	}
}


