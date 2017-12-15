package org.camunda.bpm.getstarted.parallelamazon;

import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import org.camunda.bpm.application.ProcessApplication;
import org.camunda.bpm.application.impl.ServletProcessApplication;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

public class RandomTaskTimeDelegate implements JavaDelegate {

	  private final static Logger LOGGER = Logger.getLogger("LOAN-REQUESTS");

	  public void execute(DelegateExecution execution) throws Exception {
		  Random randomGenerator = new Random();
		  int randomTime = randomGenerator.nextInt(100);
		  LOGGER.info("This task will sleep for " + randomGenerator.nextInt(100) + " seconds");
		  TimeUnit.SECONDS.sleep(randomTime);
	  }
}

