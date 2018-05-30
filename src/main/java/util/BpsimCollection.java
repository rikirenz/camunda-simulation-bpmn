package util;

import java.io.File;
import java.io.StringReader;
import java.io.StringWriter;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.datatype.Duration;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import bpsim.BPSimData;
import bpsim.BetaDistribution;
import bpsim.BinomialDistribution;
import bpsim.ControlParameters;
import bpsim.CostParameters;
import bpsim.DateTimeParameter;
import bpsim.DistributionParameter;
import bpsim.DurationParameter;
import bpsim.ElementParameters;
import bpsim.ErlangDistribution;
import bpsim.GammaDistribution;
import bpsim.LogNormalDistribution;
import bpsim.NegativeExponentialDistribution;
import bpsim.NormalDistribution;
import bpsim.Parameter;
import bpsim.ParameterValue;
import bpsim.PoissonDistribution;
import bpsim.PriorityParameters;
import bpsim.PropertyParameters;
import bpsim.ResourceParameters;
import bpsim.Scenario;
import bpsim.ScenarioParameters;
import bpsim.TimeParameters;
import bpsim.TimeUnit;
import bpsim.TriangularDistribution;
import bpsim.TruncatedNormalDistribution;
import bpsim.UniformDistribution;
import bpsim.UserDistribution;
import bpsim.UserDistributionDataPoint;
import bpsim.WeibullDistribution;
import bpsimWrappers.ControlParametersWrapper;
import bpsimWrappers.CostParametersWrapper;
import bpsimWrappers.PriorityParametersWrapper;
import bpsimWrappers.ResourceParametersWrapper;
import bpsimWrappers.ScenarioWrapper;
import bpsimWrappers.TimeParametersWrapper;

public class BpsimCollection {

	private final static Logger LOGGER = Logger.getLogger("ENSO-APP");
	
	private BPSimData bpsimData;
	private Path xmlFilePath;
	
	public static HashMap<String, ArrayList<Object>> taskObjects;
	public static ScenarioWrapper scenarioObject;
	
	public BpsimCollection(Path xmlFilePath) {
		try {
			this.xmlFilePath = xmlFilePath;
			this.bpsimData = loadBpsimAnnotations();
			scenarioObject = loadScenario();
			createTaskObjectsHashMap();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private ScenarioWrapper loadScenario() {
		try {
			if (bpsimData == null) throw new NullPointerException("The bpsim object cannot be null");
	
			// we assume that we have just one scenario per bpmn file
			Scenario currScenario= bpsimData.getScenario().get(0);
			
			// creates the Scenario object
			ScenarioWrapper currScenarioWrapper = new ScenarioWrapper();
			currScenarioWrapper.setId(currScenario.getId());
			currScenarioWrapper.setName(currScenario.getName());
			currScenarioWrapper.setDescription(currScenario.getDescription());
			ScenarioParameters scenarioParameters = currScenario.getScenarioParameters();
			if (scenarioParameters != null) {
				if (scenarioParameters.getReplication() != null) currScenarioWrapper.setReplication(scenarioParameters.getReplication().intValue());
				if (scenarioParameters.getSeed() != null) currScenarioWrapper.setSeed(scenarioParameters.getSeed().longValue());
				if (scenarioParameters.getBaseTimeUnit() != null) currScenarioWrapper.setBaseTimeUnit(scenarioParameters.getBaseTimeUnit().toString());
				if (scenarioParameters.getBaseCurrencyUnit() != null) currScenarioWrapper.setCurrencyUnit(scenarioParameters.getBaseCurrencyUnit().toString());
				if (scenarioParameters.getBaseResultFrequency() != null) currScenarioWrapper.setBaseResultFrequency(scenarioParameters.getBaseResultFrequency().toString());
				if (scenarioParameters.getTraceFormat() != null) currScenarioWrapper.setTraceFormat(scenarioParameters.getTraceFormat());
				if (scenarioParameters.getDuration() != null) currScenarioWrapper.setDuration(scenarioParameters.getDuration().getParameterValue().get(0).getValue());
				if (scenarioParameters.getWarmup() != null) currScenarioWrapper.setWarmup(scenarioParameters.getWarmup().getParameterValue().get(0).getValue());
				if (scenarioParameters.getStart() != null)  currScenarioWrapper.setStart(scenarioParameters.getStart().getParameterValue().get(0).getValue());
			}
			return currScenarioWrapper;
		} catch (Exception e) {
			//e.printStackTrace();
			return null;
		}	
	}
	
	
	private void updateHashMap(String id, Object element) {
		ArrayList currArrayList = taskObjects.get(id);
		currArrayList.add(element);
		taskObjects.put(id, currArrayList);
	}
	
	private void createTaskObjectsHashMap() {
		try {
			if (bpsimData == null) throw new NullPointerException("The bpsim object cannot be null");
			taskObjects = new HashMap<String, ArrayList<Object>>();			
			for(Scenario currScenario : bpsimData.getScenario()) {
				for(ElementParameters currElement : currScenario.getElementParameters()) {
					taskObjects.put(currElement.getElementRef().toString(), new ArrayList());
					// creates the TimeParameters object
					TimeParametersWrapper currTimeParametersWrapper =  new TimeParametersWrapper(); 
					TimeParameters timeParam = currElement.getTimeParameters();					
					if (timeParam != null) {
						// use the wrapper
						if (timeParam.getDuration() != null) currTimeParametersWrapper.setDuration(timeParam.getDuration().getParameterValue().get(0).getValue());
						if (timeParam.getLagTime() != null) currTimeParametersWrapper.setLagTime(timeParam.getLagTime().getParameterValue().get(0).getValue());
						if (timeParam.getWaitTime() != null) currTimeParametersWrapper.setWaitTime(timeParam.getWaitTime().getParameterValue().get(0).getValue());				
						if (timeParam.getQueueTime() != null) currTimeParametersWrapper.setQueueTime(timeParam.getQueueTime().getParameterValue().get(0).getValue());
						if (timeParam.getSetUpTime() != null) currTimeParametersWrapper.setSetupTime(timeParam.getSetUpTime().getParameterValue().get(0).getValue());
						if (timeParam.getReworkTime() != null) currTimeParametersWrapper.setReworkTime(timeParam.getReworkTime().getParameterValue().get(0).getValue());
						if (timeParam.getElapsedTime() != null) currTimeParametersWrapper.setElapsedTime(timeParam.getElapsedTime().getParameterValue().get(0).getValue());
						if (timeParam.getTransferTime() != null) currTimeParametersWrapper.setTransferTime(timeParam.getTransferTime().getParameterValue().get(0).getValue());
						if (timeParam.getValidationTime() != null) currTimeParametersWrapper.setValidationTime(timeParam.getValidationTime().getParameterValue().get(0).getValue());
						if (timeParam.getProcessingTime() != null) currTimeParametersWrapper.setProcessingTime(timeParam.getProcessingTime().getParameterValue().get(0).getValue());
						
						updateHashMap(currElement.getElementRef().toString(), currTimeParametersWrapper);
					}

					// creates the ControlParameters object
					ControlParametersWrapper currControlParametersWrapper = new ControlParametersWrapper(); 
					ControlParameters controlParam = currElement.getControlParameters();

					if (controlParam != null) {
						if (controlParam.getInterTriggerTimer() != null) currControlParametersWrapper.setInterTriggerTimer(controlParam.getInterTriggerTimer().getParameterValue().get(0).getValue());
						if (controlParam.getProbability() != null) currControlParametersWrapper.setProbability( controlParam.getProbability().getParameterValue().get(0).getValue());
						if (controlParam.getTriggerCount() != null) currControlParametersWrapper.setTriggerCount( controlParam.getTriggerCount().getParameterValue().get(0).getValue());
						if (controlParam.getCondition() != null) currControlParametersWrapper.setCondition( controlParam.getCondition().getParameterValue().get(0).getValue());
						// add it to the list
						updateHashMap(currElement.getElementRef().toString(), currControlParametersWrapper);
					}
					
					CostParametersWrapper currCostParametersWrapper = new CostParametersWrapper();
					CostParameters costParam = currElement.getCostParameters();
					if (costParam != null) {
						// add it to the list
						if (costParam.getUnitCost() != null) currCostParametersWrapper.setUnitCost(costParam.getUnitCost().getParameterValue().get(0).getValue());
						if (costParam.getFixedCost() != null) currCostParametersWrapper.setFixedCost(costParam.getFixedCost().getParameterValue().get(0).getValue());
						updateHashMap(currElement.getElementRef().toString(), currCostParametersWrapper);
					}

					ResourceParametersWrapper currResourceParametersWrapper = new ResourceParametersWrapper();
					ResourceParameters resourceParam = currElement.getResourceParameters();
					if (resourceParam != null) {
						if (resourceParam.getAvailability() != null) currResourceParametersWrapper.setAvailability(resourceParam.getAvailability().getParameterValue().get(0).getValue());
						if (resourceParam.getQuantity() != null) currResourceParametersWrapper.setQuantity(resourceParam.getQuantity().getParameterValue().get(0).getValue());
						if (resourceParam.getSelection() != null) currResourceParametersWrapper.setSelection(resourceParam.getSelection().getParameterValue().get(0).getValue());
						// add it to the list
						updateHashMap(currElement.getElementRef().toString(), currResourceParametersWrapper);
					}
										
					PriorityParametersWrapper currPriorityParametersWrapper = new PriorityParametersWrapper();
					PriorityParameters priorityParam = currElement.getPriorityParameters();
					if (priorityParam != null) {
						if (priorityParam.getInterruptible() != null) currPriorityParametersWrapper.setInterruptible(priorityParam.getInterruptible().getParameterValue().get(0).getValue());
						if (priorityParam.getPriority() != null) currPriorityParametersWrapper.setPriority(priorityParam.getPriority().getParameterValue().get(0).getValue());
						// add it to the list
						updateHashMap(currElement.getElementRef().toString(), currPriorityParametersWrapper);
					}
				}
			} 
		} catch (Exception e) {
			//e.printStackTrace();
		}	
	}
	
	private BPSimData loadBpsimAnnotations() {
		try {
			JAXBContext jc = JAXBContext.newInstance(BPSimData.class);
	        Unmarshaller unmarshaller = jc.createUnmarshaller();
	        
	        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	        DocumentBuilder builder = factory.newDocumentBuilder();
	        Document doc = builder.parse(new File(this.xmlFilePath.toString()));
	        XPathFactory xPathfactory = XPathFactory.newInstance();
	        XPath xpath = xPathfactory.newXPath();
	        XPathExpression expr = xpath.compile("//*[local-name()='BPSimData']");	        
	        NodeList nl = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
	        if (nl.getLength() < 1) return null;
	        
	        ((Element) nl.item(0)).setAttribute("xmlns:bpsim","http://www.bpsim.org/schemas/2.0");
			String fileString = toString(nl.item(0));
			InputSource fileSource = new InputSource(new StringReader(fileString));
			return (BPSimData) unmarshaller.unmarshal(fileSource);
		} catch (Exception e) {
			LOGGER.info("Could not Load the bpsim tag");
			e.printStackTrace();
			return null;
		}
	}
	
	private String toString(Node node) {
	    if (node == null) {
	        throw new IllegalArgumentException("node is null.");
	    }

	    try {
	        // Remove unwanted whitespaces
	        node.normalize();
	        XPath xpath = XPathFactory.newInstance().newXPath();
	        XPathExpression expr = xpath.compile("//text()[normalize-space()='']");
	        NodeList nodeList = (NodeList)expr.evaluate(node, XPathConstants.NODESET);

	        for (int i = 0; i < nodeList.getLength(); ++i) {
	            Node nd = nodeList.item(i);
	            nd.getParentNode().removeChild(nd);
	        }

	        // Create and setup transformer
	        Transformer transformer =  TransformerFactory.newInstance().newTransformer();
	        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
	        StringWriter writer = new StringWriter();
	        transformer.transform(new DOMSource(node), new StreamResult(writer));
	        return writer.toString();
	    } catch (TransformerException e) {
	        throw new RuntimeException(e);
	    } catch (XPathExpressionException e) {
	        throw new RuntimeException(e);
	    }
	}

}
