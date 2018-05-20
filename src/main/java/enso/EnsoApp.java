package enso;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;
import java.util.logging.Logger;

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
import org.camunda.bpm.model.bpmn.instance.EventDefinition;
import org.camunda.bpm.model.bpmn.instance.IntermediateThrowEvent;
import org.camunda.bpm.model.bpmn.instance.MessageEventDefinition;
import org.camunda.bpm.model.xml.instance.ModelElementInstance;
import org.camunda.bpm.model.xml.type.ModelElementType;

import bpsimWrappers.ControlParametersWrapper;
import simulation.EventsQueue;
import simulation.SimulationCatchEvent;
import simulation.SimulationClock;
import simulation.SimulationEndEvent;
import simulation.SimulationEvent;
import simulation.SimulationStartEvent;
import simulation.SimulationTaskEvent;
import util.BpsimCollection;

public class EnsoApp {

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
				.setJdbcUrl("jdbc:h2:mem:my-own-db;DB_CLOSE_DELAY=1000").setJobExecutorActivate(true)
				.buildProcessEngine();
	}

	private BpmnModelInstance loadBpmnProcess(Path filePath) {
		return Bpmn.readModelFromFile(new File(filePath.toString()));
	}

	public void startApp() {
		// load the bpsim data in the xml
		new BpsimCollection(processBpmnPath);
		startSimulation();
	}

	private void startSimulation() {
		ProcessEngine processEngine = processEngineInit();
		RepositoryService repositoryService = processEngine.getRepositoryService();
		BpmnModelInstance modelInstance = loadBpmnProcess(processBpmnPath);
		DeploymentBuilder deploymentBuilder = repositoryService.createDeployment().name(processBpmnId);
		deploymentBuilder.addModelInstance(processBpmnId + ".bpmn", modelInstance);
		deploymentBuilder.deploy();

		RuntimeService runtimeService = processEngine.getRuntimeService();
		TaskService taskService = processEngine.getTaskService();
		
		// start events creation
		int startTime = delayBetweenInstances;
		for (int i = 0; i < instancesNumber; i++) {
			SimulationEvent startProcessEvent = new SimulationStartEvent("start event", startTime);
			eventsQueue.add(startProcessEvent);
			startTime += delayBetweenInstances;
		}
				

		// indipendent events creation
		// get all the intermediate events in the simulation		
		// retrive just the indipendent ones
		ArrayList<SimulationCatchEvent> indipendentIntermediateThrowEvents = new ArrayList<SimulationCatchEvent>();
		
		ModelElementType IntermediateThrowEventType = modelInstance.getModel().getType(IntermediateThrowEvent.class);
		Collection<ModelElementInstance> taskInstances = modelInstance.getModelElementsByType(IntermediateThrowEventType);
		for (ModelElementInstance currentElement : taskInstances) {
			IntermediateThrowEvent currIntermediateThrowEvent = (IntermediateThrowEvent) currentElement;

			ArrayList<Object> eventParameters = BpsimCollection.taskObjects.get(currIntermediateThrowEvent.getId());
			// if there are no parameter means it's not indipendent
			if (eventParameters == null) continue;

			for (Object currParameter : eventParameters) {
				if (currParameter instanceof ControlParametersWrapper) {
					ControlParametersWrapper currCtrlWrapper = (ControlParametersWrapper) currParameter;
					try {
						SimulationCatchEvent currSimCatchEvent = new SimulationCatchEvent(
							currIntermediateThrowEvent.getId(), 
							currCtrlWrapper.getInterTriggerTimer().longValue(), 
							"", 
							currCtrlWrapper.getInterTriggerTimer().longValue()
						);
						indipendentIntermediateThrowEvents.add(currSimCatchEvent);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
		
		
		while (!eventsQueue.isEmpty()) {
			SimulationEvent currEvent = eventsQueue.remove();
			if (currEvent instanceof SimulationTaskEvent) {
				LOGGER.info("task event");
				SimulationTaskEvent currTaskEvent = (SimulationTaskEvent) currEvent;
				if (currTaskEvent.getEndTime() > simClock.getCurrentTime())
					simClock.setCurrentTime(currTaskEvent.getEndTime());
				// move on with the simulation
				Task currTask = taskService.createTaskQuery().processInstanceId(currTaskEvent.getProcessId()).taskName(currTaskEvent.getName()).singleResult();
				taskService.complete(currTask.getId());
			} else if (currEvent instanceof SimulationCatchEvent) {				
				LOGGER.info("catch event");
				SimulationCatchEvent currCatchEvent = (SimulationCatchEvent) currEvent;
				if (currCatchEvent.getEndTime() > simClock.getCurrentTime())
					simClock.setCurrentTime(currCatchEvent.getEndTime());									
				// Put the event again in the queue with the time updated
				currCatchEvent.setEndTime(simClock.getCurrentTime() + currCatchEvent.getInterTriggerTime());
				eventsQueue.add(currCatchEvent);
			} else if (currEvent instanceof SimulationEndEvent) {
				eventsQueue.removeEventsByProcessId(((SimulationEndEvent) currEvent).getProcessId());
		    } else {
				LOGGER.info("Start event");
				ProcessInstance instance = runtimeService.startProcessInstanceByKey(processBpmnId);
				// add catchEvent to the new instance of the process
				for (SimulationCatchEvent currCatchEvent : indipendentIntermediateThrowEvents) {
					currCatchEvent.setProcessId(instance.getProcessInstanceId());
					eventsQueue.add(currCatchEvent);
				}
			}
			LOGGER.info("Current Time: " + simClock.getCurrentTime());
		}
	}
}
