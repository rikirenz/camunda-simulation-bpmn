package executionlisteners;

import java.util.logging.Logger;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.ExecutionListener;


public class TerminateEventListener implements ExecutionListener {
    
	protected final static Logger LOGGER = Logger.getLogger("ENSO-APP");
		
	public void notify(DelegateExecution execution) throws Exception {
		LOGGER.info("===================== The process Ended =====================");
	}
}
