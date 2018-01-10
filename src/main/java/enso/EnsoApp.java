package enso;

import java.io.File;
import java.nio.file.Path;
import java.util.logging.Logger;
import java.util.List;

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


import simulation.EventsHandler;
import simulation.EventsQueue;
import simulation.SimulationClock;
import simulation.SimulationEvent;

public class EnsoApp {
	
	private Path processBpmnPath;
	private String processBpmnId;
	private EventsQueue eventsQueue = EventsQueue.getInstance();
	private final static Logger LOGGER = Logger.getLogger("ENSO-APP");
	private SimulationClock simClock = new SimulationClock();

	public EnsoApp(Path processBpmnPath, String processBpmnId) {
		this.processBpmnPath = processBpmnPath;
		this.processBpmnId = processBpmnId;
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
		ProcessEngine processEngine = processEngineInit();
		RepositoryService repositoryService = processEngine.getRepositoryService();		
	    BpmnModelInstance instance = loadBpmnProcess(processBpmnPath);
	    DeploymentBuilder deploymentBuilder = repositoryService.createDeployment().name(processBpmnId);
	    deploymentBuilder.addModelInstance(processBpmnId + ".bpmn", instance);
		deploymentBuilder.deploy();
		RuntimeService runtimeService = processEngine.getRuntimeService();
		TaskService taskService = processEngine.getTaskService();
		runtimeService.startProcessInstanceByKey(processBpmnId);
		
		// move on with the simulation.
		while (!eventsQueue.isEmpty()) {
			SimulationEvent currEvent = eventsQueue.remove();
			// if end time more than current time skip
			if (currEvent.getEndTime() > simClock.getCurrentTime()) simClock.setCurrentTime(currEvent.getEndTime());
				
			// move on with the simulation.			
			Task currTask = taskService.createTaskQuery().taskName(currEvent.getName()).singleResult();
			taskService.complete(currTask.getId());
		}
		
		
	}	
}
