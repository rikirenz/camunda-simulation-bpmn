package enso;

import java.io.File;
import java.io.FileNotFoundException;
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
		EnsoApp app = new EnsoApp(
				"C:\\projects\\camunda-simulation-bpmn\\src\\main\\java\\bpmprocesses\\parallel-amazon.bpmn", 
				"amazon-delivery-test"
		);
		app.startApp();
	}
	
}
