package util;

import java.util.ArrayList;
import java.util.logging.Logger;

import bpsimWrappers.ParametersWrapper;

public class Util {

	protected final static Logger LOGGER = Logger.getLogger("ENSO-APP");

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
}


