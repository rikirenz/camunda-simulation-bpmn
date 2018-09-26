package simulation;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import java.io.File;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Logger;

import util.Util;

public class ResultsCatalog {

	private final static Logger LOGGER = Logger.getLogger("ENSO-APP");
	private static Map<String, ArrayList<SimulationResult>> resultsMap = new HashMap<String, ArrayList<SimulationResult>>();
	private SimulationCosts simCosts;
	private SimulationClock simClock;
	
	public ResultsCatalog() {
		simCosts = new SimulationCosts();
		simClock = new SimulationClock();
	}
	
	public void addResult(String processId, String activityId, String resourceId, Double unitCost, Double fixedCost, Long time) {

		SimulationResult newResult = new SimulationResult(processId, activityId, resourceId, unitCost, fixedCost, time);
		if (resultsMap.get(processId) == null) { 
			ArrayList<SimulationResult> individualResult = new ArrayList<SimulationResult>();
			individualResult.add(newResult);
			resultsMap.put(processId, individualResult);
		} else {
			resultsMap.get(processId).add(newResult);			
		}

	}
	
	public void printResults(String outputFileName) {
		String strResults = "Total time : " + simClock.getCurrentTime() + "\n";
		strResults += "Total costs : " + simCosts.getTotalCost() + "\n";		
		
		
		
		// create xml file
		Util.writeStringToFile(strResults, outputFileName);	
		
		try {
			DocumentBuilderFactory dbFactory =
			DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.newDocument();
			
			// root element
			Element rootElement = doc.createElement("SimulationResults");
			doc.appendChild(rootElement);

			// supercars element
			Element totalTime = doc.createElement("totalTime");
			rootElement.appendChild(totalTime);

			Element totalCosts = doc.createElement("totalCosts");
			rootElement.appendChild(totalCosts);
		
			
			for (Entry<String, ArrayList<SimulationResult>> item : resultsMap.entrySet()) {
				Element individualSimulationResults = doc.createElement("IndividualSimulationResults");
				rootElement.appendChild(individualSimulationResults);
				
				Attr attr = doc.createAttribute("processId");
				attr.setValue(item.getKey());
				individualSimulationResults.setAttributeNode(attr);
				
				for (SimulationResult currTaskResult : item.getValue()) {
					Element individualTaskResult = doc.createElement("individualTaskResult");
					individualSimulationResults.appendChild(individualTaskResult);	

					attr = doc.createAttribute("ProcessId");
					attr.setValue(currTaskResult.getProcessId());
					individualTaskResult.setAttributeNode(attr);
					
					attr = doc.createAttribute("ActivityId");
					attr.setValue(currTaskResult.getActivityId());
					individualTaskResult.setAttributeNode(attr);
					
					attr = doc.createAttribute("ResourceId");
					attr.setValue(currTaskResult.getResourceId());
					individualTaskResult.setAttributeNode(attr);
					
					attr = doc.createAttribute("UnitCost");
					attr.setValue(String.valueOf(currTaskResult.getUnitCost()));
					individualTaskResult.setAttributeNode(attr);
					
					attr = doc.createAttribute("FixedCost");
					attr.setValue(String.valueOf(currTaskResult.getFixedCost()));
					individualTaskResult.setAttributeNode(attr);
					
					attr = doc.createAttribute("Time");
					attr.setValue(String.valueOf(currTaskResult.getTime()));
					individualTaskResult.setAttributeNode(attr);

				}
				
			}
			
			
			
			
			
			// setting attribute to element
			Attr attr = doc.createAttribute("value");
			attr.setValue(String.valueOf(simClock.getCurrentTime()));
			totalTime.setAttributeNode(attr);

			attr = doc.createAttribute("value");
			attr.setValue(String.valueOf(simCosts.getTotalCost()));
			totalCosts.setAttributeNode(attr);

			
			
			/*
			// carname element
			Element carname = doc.createElement("carname");
			Attr attrType = doc.createAttribute("type");
			attrType.setValue("formula one");
			carname.setAttributeNode(attrType);
			carname.appendChild(doc.createTextNode("Ferrari 101"));
			supercar.appendChild(carname);

			Element carname1 = doc.createElement("carname");
			Attr attrType1 = doc.createAttribute("type");
			attrType1.setValue("sports");
			carname1.setAttributeNode(attrType1);
			carname1.appendChild(doc.createTextNode("Ferrari 202"));
			supercar.appendChild(carname1);
			 */
			// write the content into xml file
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc); 
			StreamResult result = new StreamResult(new File("C:\\cars.xml"));
			transformer.transform(source, result);

			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
