package enso;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Collection;

import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.ProcessEngineConfiguration;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.repository.DeploymentBuilder;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.model.bpmn.Bpmn;
import org.camunda.bpm.model.bpmn.BpmnModelInstance;

public class App {
    
	public static void main( String[] args ) throws FileNotFoundException {	
    	ProcessEngineConfiguration.createStandaloneProcessEngineConfiguration();
		ProcessEngineConfiguration.createStandaloneInMemProcessEngineConfiguration();
		ProcessEngine processEngine = ProcessEngineConfiguration.createStandaloneInMemProcessEngineConfiguration()
				  .setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_TRUE)
				  .setJdbcUrl("jdbc:h2:mem:my-own-db;DB_CLOSE_DELAY=1000")
				  .setJobExecutorActivate(true)
				  .buildProcessEngine();
		
		RepositoryService repositoryService = processEngine.getRepositoryService();
		
	    BpmnModelInstance instance = Bpmn.readModelFromFile(new File("C:\\projects\\enso\\src\\main\\java\\bpmprocesses\\parallel-amazon.bpmn"));
	    DeploymentBuilder deploymentBuilder = repositoryService.createDeployment().name("test");
		deploymentBuilder.addModelInstance("amazon-delivery-test.bpmn", instance);
		Collection<String> names = deploymentBuilder.getResourceNames();		
		System.out.println(Arrays.toString(names.toArray()));
		deploymentBuilder.deploy();

		RuntimeService runtimeService = processEngine.getRuntimeService();
		ProcessInstance test = runtimeService.startProcessInstanceByKey("amazon-delivery-test");
    }
}
