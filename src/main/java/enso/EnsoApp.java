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
import javax.xml.xpath.XPathExpressionException;
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
import simulation.ResultsCatalog;
import simulation.SimulationResource;
import simulation.SimulationBoundaryEvent;
import simulation.SimulationCatchEvent;
import simulation.SimulationClock;
import simulation.SimulationCosts;
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
	private boolean enableResults;

	private int instancesNumber;
	private int delayBetweenInstances;

	private EventsQueue eventsQueue = EventsQueue.getInstance();
	private final static Logger LOGGER = Logger.getLogger("ENSO-APP");
	private SimulationClock simClock = new SimulationClock();

	private ProcessEngine processEngine;
	private RuntimeService runtimeService;
	private TaskService taskService;

	public EnsoApp(Path processBpmnPath, String processBpmnId, int instancesNumber, int delayBetweenInstances,
			boolean enableResults) {
		this.processBpmnPath = processBpmnPath;
		this.processBpmnId = processBpmnId;
		this.instancesNumber = instancesNumber;
		this.delayBetweenInstances = delayBetweenInstances;
		this.enableResults = enableResults;
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
			BpmnPreprocesser bpmnPreprocesser = new BpmnPreprocesser(
					builder.parse(new File(processBpmnPath.toString())));
			bpmnDocument = bpmnPreprocesser.getProcessedBpmn();
		} catch (Exception ex) {
			LOGGER.warning("Not Able to load the document");
			ex.printStackTrace();
			System.exit(1);
		}

		// load the bpsim data in the xml
		BpsimCollection bpsimCollection = new BpsimCollection(bpmnDocument);
		initializeSimulation();
		runSimulation();
		if (enableResults)
			printResult();
		cleanSimulation();
	}

	private void cleanSimulation() {
		processEngine.close();
	}

	private void initializeSimulation() {
		processEngine = processEngineInit();
		RepositoryService repositoryService = processEngine.getRepositoryService();
		BpmnModelInstance modelInstance = Util.loadBpmnProcess(bpmnDocument);
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

	private void printResult() {
		new ResultsCatalog().printResults();
	}

	private void runSimulation() {
		while (!eventsQueue.isEmpty()) {
			SimulationEvent currEvent = eventsQueue.remove();
			if (currEvent instanceof SimulationTaskEvent) {
				SimulationTaskEvent currTaskEvent = (SimulationTaskEvent) currEvent;
				SimulationResource currResource = BpsimCollection.resourcesElementsAvaliability.get(currTaskEvent.getResourceId());
				if (currResource == null || currResource.isAvaliable()) {
					if ((currTaskEvent.getStartTime() + currTaskEvent.getTime()) > simClock.getCurrentTime())
						simClock.setCurrentTime((currTaskEvent.getStartTime() + currTaskEvent.getTime()));
					if (currResource != null) currResource.setTimeResourceReleased(currTaskEvent.getStartTime() + currTaskEvent.getTime());
					Task currTask = taskService.createTaskQuery().processInstanceId(currTaskEvent.getProcessId()).activityInstanceIdIn(currTaskEvent.getId()).singleResult();
					if (currTask != null) taskService.complete(currTask.getId());
				} else {
					if (currResource.getTimeResourceReleased() >= currTaskEvent.getStartTime()) {
						if (simClock.getCurrentTime() < (currResource.getTimeResourceReleased() + currTaskEvent.getTime())) 
							simClock.setCurrentTime((currResource.getTimeResourceReleased() + currTaskEvent.getTime()));
					} else {
						simClock.setCurrentTime((simClock.getCurrentTime() + currTaskEvent.getTime()));
					}
					currResource.setTimeResourceReleased(currResource.getTimeResourceReleased() + currTaskEvent.getTime());
					currResource.useResource();

					
					Task currTask = taskService.createTaskQuery().processInstanceId(currTaskEvent.getProcessId())
							.activityInstanceIdIn(currTaskEvent.getId()).singleResult();
					taskService.complete(currTask.getId());
				}
			} else if (currEvent instanceof SimulationIntermediateEvent) {
				LOGGER.info("Intermediate event");
				SimulationIntermediateEvent currCatchEvent = (SimulationIntermediateEvent) currEvent;
				if (currCatchEvent.getStartTime() > simClock.getCurrentTime())
					simClock.setCurrentTime(currCatchEvent.getStartTime());
				LOGGER.info("SimulationIntermediateEvent: " + currCatchEvent.getProcessId() + " - "
						+ currCatchEvent.getName());
			} else if (currEvent instanceof SimulationCatchEvent) {
				LOGGER.info("catch event");
				SimulationCatchEvent currCatchEvent = (SimulationCatchEvent) currEvent;
				if (currCatchEvent.getStartTime() > simClock.getCurrentTime())
					simClock.setCurrentTime(currCatchEvent.getStartTime());

				// Put the event again in the queue with the time updated
				SimulationCatchEvent nextCatchEvent = currCatchEvent.getNextEvent();
				if (nextCatchEvent != null)
					eventsQueue.add(nextCatchEvent);

				// trigger the event in the process
				if (runtimeService.createEventSubscriptionQuery().processInstanceId(currCatchEvent.getProcessId()).count() > 0) {
					EventSubscription subscription = runtimeService.createEventSubscriptionQuery()
							.processInstanceId(currCatchEvent.getProcessId()).eventName(currCatchEvent.getName())
							.singleResult();
					runtimeService.messageEventReceived(subscription.getEventName(), subscription.getExecutionId());
				}

			} else if (currEvent instanceof SimulationBoundaryEvent) {
				SimulationBoundaryEvent currBoundaryEvent = (SimulationBoundaryEvent) currEvent;
				if ((currBoundaryEvent.getStartTime() + currBoundaryEvent.getTime()) > simClock.getCurrentTime())
					simClock.setCurrentTime(currBoundaryEvent.getStartTime() + currBoundaryEvent.getTime());

				// trigger the event in the process
				EventSubscription subscription = runtimeService.createEventSubscriptionQuery()
						.processInstanceId(currBoundaryEvent.getProcessId()).eventName(currBoundaryEvent.getName())
						.singleResult();
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
			LOGGER.info("Current Event: " + currEvent.getName() + " Current Time: " + simClock.getCurrentTime());
		}
	}
}
