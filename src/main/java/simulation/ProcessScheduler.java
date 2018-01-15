package simulation;

import java.util.TimerTask;
import java.util.logging.Logger;

import org.camunda.bpm.engine.RuntimeService;

public class ProcessScheduler extends TimerTask{

	private final static Logger LOGGER = Logger.getLogger("ENSO-APP");
	private RuntimeService runtimeService;
	private String processBpmnId;
	private int instancesNumber;
	
	public ProcessScheduler(RuntimeService runtimeService, String processBpmnId, int instancesNumber) {
		this.runtimeService = runtimeService;
		this.processBpmnId = processBpmnId;
		this.instancesNumber = instancesNumber;
	}
	
    public void run() {
    	LOGGER.info("==========================Running new Instance ================================== number:" + instancesNumber);
    	runtimeService.startProcessInstanceByKey(processBpmnId);
    	instancesNumber--;
        if (instancesNumber == 0) {
        	this.cancel();
        }
    }
}
