package enso;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.logging.Logger;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.ProcessEngineConfiguration;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.repository.DeploymentBuilder;
import org.camunda.bpm.engine.runtime.EventSubscription;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.Task;
import org.camunda.bpm.model.bpmn.Bpmn;
import org.camunda.bpm.model.bpmn.BpmnModelInstance;
import org.camunda.bpm.model.bpmn.impl.instance.Outgoing;
import org.camunda.bpm.model.bpmn.instance.IntermediateCatchEvent;
import org.camunda.bpm.model.bpmn.instance.IntermediateThrowEvent;
import org.camunda.bpm.model.bpmn.instance.MessageEventDefinition;
import org.camunda.bpm.model.bpmn.instance.StartEvent;
import org.camunda.bpm.model.xml.instance.ModelElementInstance;
import org.camunda.bpm.model.xml.type.ModelElementType;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import bpsimWrappers.ControlParametersWrapper;
import simulation.EventsQueue;
import simulation.SimulationBoundaryEvent;
import simulation.SimulationCatchEvent;
import simulation.SimulationClock;
import simulation.SimulationEvent;
import simulation.SimulationIntermediateEvent;
import simulation.SimulationStartEvent;
import simulation.SimulationTaskEvent;
import util.BpmnPreprocesser;
import util.BpsimCollection;
import util.Util;

public class EnsoApp {

	private Path processBpmnPath;
	private Document bpmnDocument;
	private String processBpmnId;

	private int instancesNumber;
	private int delayBetweenInstances;

	private EventsQueue eventsQueue = EventsQueue.getInstance();
	private final static Logger LOGGER = Logger.getLogger("ENSO-APP");
	private SimulationClock simClock = new SimulationClock();

	private RuntimeService runtimeService;
	private TaskService taskService;

	
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
				.setJdbcUrl("jdbc:h2:mem:my-own-db;DB_CLOSE_DELAY=1000").setJobExecutorActivate(true)
				.buildProcessEngine();
	}

	public void startApp() {		
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	        DocumentBuilder builder = factory.newDocumentBuilder();	        	
	        BpmnPreprocesser bpmnPreprocesser = new BpmnPreprocesser(builder.parse(new File(processBpmnPath.toString())));
	        bpmnDocument = bpmnPreprocesser.getProcessedBpmn();
		} catch (Exception ex) {
			
			
		}
		
		// load the bpsim data in the xml
		BpsimCollection bpsimCollection = new BpsimCollection(bpmnDocument);	
		startSimulation();
		runSimulation();
	}
		
	
	private void startSimulation() {
		ProcessEngine processEngine = processEngineInit();
		RepositoryService repositoryService = processEngine.getRepositoryService();
		BpmnModelInstance modelInstance = Util.loadBpmnProcess(bpmnDocument);
				
		//LOGGER.info(Util.convertDocumnetToString(bpmnDocument));
				
		Util.writeStringToFile(Util.convertDocumnetToString(bpmnDocument), "preProcessedDoc.bpmn");
		
		DeploymentBuilder deploymentBuilder = repositoryService.createDeployment().name(processBpmnId);
		deploymentBuilder.addModelInstance(processBpmnId + ".bpmn", modelInstance);
		deploymentBuilder.deploy();
		runtimeService = processEngine.getRuntimeService();
		taskService = processEngine.getTaskService();
		
		// start events creation
		int startTime = delayBetweenInstances;
		for (int i = 0; i < instancesNumber; i++) {
			SimulationEvent startProcessEvent = new SimulationStartEvent("start event", startTime);
			eventsQueue.add(startProcessEvent);
			startTime += delayBetweenInstances;
		}
	}

	private void runSimulation() {
		while (!eventsQueue.isEmpty()) {
			SimulationEvent currEvent = eventsQueue.remove();
			if (currEvent instanceof SimulationTaskEvent) {
				LOGGER.info("task event");
				
				SimulationTaskEvent currTaskEvent = (SimulationTaskEvent) currEvent;
				if (currTaskEvent.getStartTime() > simClock.getCurrentTime())
					simClock.setCurrentTime(currTaskEvent.getStartTime());
				// move on with the simulation
				LOGGER.info(currTaskEvent.getName());
				Task currTask = taskService.createTaskQuery().processInstanceId(currTaskEvent.getProcessId()).activityInstanceIdIn(currTaskEvent.getId()).singleResult();
				taskService.complete(currTask.getId());
				
			}else if (currEvent instanceof SimulationIntermediateEvent ) {
				LOGGER.info("Intermediate event");
				SimulationIntermediateEvent currCatchEvent = (SimulationIntermediateEvent) currEvent;
				if (currCatchEvent.getStartTime() > simClock.getCurrentTime())
					simClock.setCurrentTime(currCatchEvent.getStartTime());
				LOGGER.info("SimulationIntermediateEvent: " + currCatchEvent.getProcessId() + " - " + currCatchEvent.getName());

			} else if (currEvent instanceof SimulationCatchEvent) {
				LOGGER.info("catch event");				
				SimulationCatchEvent currCatchEvent = (SimulationCatchEvent) currEvent;
				if (currCatchEvent.getStartTime() > simClock.getCurrentTime())
					simClock.setCurrentTime(currCatchEvent.getStartTime());
				// Put the event again in the queue with the time updated
				eventsQueue.add(currCatchEvent.getNextEvent());
				
				LOGGER.info("" + currCatchEvent.getProcessId() + " - " + currCatchEvent.getName());
				// trigger the event in the process
				EventSubscription subscription = runtimeService.createEventSubscriptionQuery().
						processInstanceId(currCatchEvent.getProcessId()).eventName(currCatchEvent.getName()).singleResult();
				runtimeService.messageEventReceived(subscription.getEventName(), subscription.getExecutionId());

			}else if (currEvent instanceof SimulationBoundaryEvent) {
				LOGGER.info("boundary event");				
				SimulationBoundaryEvent currBoundaryEvent = (SimulationBoundaryEvent) currEvent;
				if (currBoundaryEvent.getStartTime() > simClock.getCurrentTime())
					simClock.setCurrentTime(currBoundaryEvent.getStartTime());

				// trigger the event in the process
				
				LOGGER.info(currBoundaryEvent.getName());
				EventSubscription subscription = runtimeService.createEventSubscriptionQuery().
						processInstanceId(currBoundaryEvent.getProcessId()).eventName(currBoundaryEvent.getName()).singleResult();
				runtimeService.messageEventReceived(subscription.getEventName(), subscription.getExecutionId());
			} else {
				LOGGER.info("Start event");
				ProcessInstance instance = runtimeService.startProcessInstanceByKey(processBpmnId);
				// add catchEvent to the new instance of the process
				for (SimulationCatchEvent currCatchEvent : BpsimCollection.indipendentIntermediateThrowEvents) {
					currCatchEvent.setProcessId(instance.getProcessInstanceId());
					eventsQueue.add(currCatchEvent);
				}
			}
			LOGGER.info("Current Time: " + simClock.getCurrentTime());
		}
	}
}
