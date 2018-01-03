package enso;

import java.awt.List;
import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.logging.Logger;

import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.ProcessEngineConfiguration;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.repository.DeploymentBuilder;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.model.bpmn.Bpmn;
import org.camunda.bpm.model.bpmn.BpmnModelInstance;

public class EnsoApp {
	
	private Path processBpmnPath;
	private String processBpmnId;
	private final static Logger LOGGER = Logger.getLogger("ENSO-APP");

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
		
		LOGGER.info("Numbers of name " + deploymentBuilder.getResourceNames().toString());
		
		RuntimeService runtimeService = processEngine.getRuntimeService();
		ProcessInstance amazonDelivery = runtimeService.startProcessInstanceByKey(processBpmnId);
		
	
		
	}	
}
