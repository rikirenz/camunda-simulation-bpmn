package enso;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Logger;
import java.util.List;
import java.util.Properties;
import java.util.Timer;


import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
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

import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.ProcessEngineConfiguration;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.repository.DeploymentBuilder;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.Task;
import org.camunda.bpm.model.bpmn.Bpmn;
import org.camunda.bpm.model.bpmn.BpmnModelInstance;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import bpsim.BPSimData;
import simulation.EventsHandler;
import simulation.EventsQueue;
import simulation.ProcessScheduler;
import simulation.SimulationClock;
import simulation.SimulationEvent;
import simulation.SimulationStartEvent;
import simulation.SimulationTaskEvent;

public class EnsoApp {
	
	private BPSimData bpsimData;
	private Path processBpmnPath;
	private String processBpmnId;
	
	private int instancesNumber;
	private int delayBetweenInstances;
	
	
	private EventsQueue eventsQueue = EventsQueue.getInstance();
	private final static Logger LOGGER = Logger.getLogger("ENSO-APP");
	private SimulationClock simClock = new SimulationClock();

	public EnsoApp(Path processBpmnPath, String processBpmnId, int instancesNumber, int delayBetweenInstances) {
		this.processBpmnPath = processBpmnPath;
		this.processBpmnId = processBpmnId;
		this.instancesNumber = instancesNumber;
		this.delayBetweenInstances = delayBetweenInstances;
	}
	
	private ProcessEngine processEngineInit() {
    	ProcessEngineConfiguration.createStandaloneProcessEngineConfiguration();
		ProcessEngineConfiguration.createStandaloneInMemProcessEngineConfiguration();
		return ProcessEngineConfiguration.createStandaloneInMemProcessEngineConfiguration()
				  .setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_TRUE)
				  .setJdbcUrl("jdbc:h2:mem:my-own-db;DB_CLOSE_DELAY=1000")
				  .setJobExecutorActivate(true)
				  .buildProcessEngine();
	}
	
	private BpmnModelInstance loadBpmnProcess(Path filePath) {
		return Bpmn.readModelFromFile(new File(filePath.toString()));
	}

	public void startApp() {
		loadBpsimAnnotations();
		startSimulation();
	}
	
	
	private void loadBpsimAnnotations() {
		try {
			JAXBContext jc = JAXBContext.newInstance(BPSimData.class);
	        Unmarshaller unmarshaller = jc.createUnmarshaller();
	        
	        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	        DocumentBuilder builder = factory.newDocumentBuilder();
	        Document doc = builder.parse(new File(this.processBpmnPath.toString()));
	        XPathFactory xPathfactory = XPathFactory.newInstance();
	        XPath xpath = xPathfactory.newXPath();
	        XPathExpression expr = xpath.compile("//*[local-name()='BPSimData']");	        
	        NodeList nl = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
	        if (nl.getLength() < 1) return;

	        
	        ((Element) nl.item(0)).setAttribute("xmlns:bpsim","http://www.bpsim.org/schemas/2.0");
			String fileString = toString(nl.item(0));
			InputSource fileSource = new InputSource(new StringReader(fileString)); 
			bpsimData = (BPSimData) unmarshaller.unmarshal(fileSource);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public static String toString(Node node) {
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
	
	private void startSimulation() {
		ProcessEngine processEngine = processEngineInit();
		RepositoryService repositoryService = processEngine.getRepositoryService();		
	    BpmnModelInstance instance = loadBpmnProcess(processBpmnPath);
	    DeploymentBuilder deploymentBuilder = repositoryService.createDeployment().name(processBpmnId);
	    deploymentBuilder.addModelInstance(processBpmnId + ".bpmn", instance);
		deploymentBuilder.deploy();
		
		
		RuntimeService runtimeService = processEngine.getRuntimeService();
		TaskService taskService = processEngine.getTaskService();
		
		int startTime = delayBetweenInstances;
		for (int i = 0; i < instancesNumber; i++) {
			SimulationEvent startProcessEvent = new SimulationStartEvent("start event", startTime);
			eventsQueue.add(startProcessEvent);
			startTime += delayBetweenInstances;
		}
		
		while (!eventsQueue.isEmpty()) {
			SimulationEvent currEvent = eventsQueue.remove();
			
			if (currEvent instanceof SimulationTaskEvent) {
				SimulationTaskEvent currTaskEvent = (SimulationTaskEvent) currEvent;
				if (currTaskEvent.getEndTime() > simClock.getCurrentTime()) simClock.setCurrentTime(currTaskEvent.getEndTime());
				// move on with the simulation
				Task currTask = taskService.createTaskQuery().processInstanceId(currTaskEvent.getProcessId()).taskName(currTaskEvent.getName()).singleResult();
				taskService.complete(currTask.getId());
				LOGGER.info(currTaskEvent.toString());
			} else {
		    	runtimeService.startProcessInstanceByKey(processBpmnId);
			}
		}
	}
}
