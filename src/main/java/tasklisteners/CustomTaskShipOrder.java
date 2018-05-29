package tasklisteners;

import java.util.logging.Logger;

import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;

public class CustomTaskShipOrder implements TaskListener {

	private final static Logger LOGGER = Logger.getLogger("ENSO-APP");
	
	public void notify(DelegateTask delegateTask) {
		LOGGER.info("Custom Delegate Task");
	}
}
