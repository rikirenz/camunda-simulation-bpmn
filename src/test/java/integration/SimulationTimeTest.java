package integration;

import java.nio.file.Paths;

import enso.EnsoApp;
import junit.framework.TestCase;
import simulation.SimulationClock;
import util.CleanUp;

public class SimulationTimeTest extends TestCase{

	public void testLinearEvents() {
		try {
			
			;
			EnsoApp app = new EnsoApp(
					Paths.get("src","test","java","bpmnprocesses","linear-amazon-test.bpmn"),
					"amazon-delivery-test"
			);
			app.startApp();

			SimulationClock simClock =  new SimulationClock();
			int currTime = simClock.getCurrentTime();

			assertEquals(20, currTime);

		} finally {
			CleanUp.resetSimulationClock();
		}
	}

	public void testParallelEvents() {
		try {
			EnsoApp app = new EnsoApp(
					Paths.get("src","test","java","bpmnprocesses","parallel-amazon-test.bpmn"),
					"amazon-delivery-test"
			);
			app.startApp();

			SimulationClock simClock =  new SimulationClock();
			int currTime = simClock.getCurrentTime();

			assertEquals(10, currTime);

		} finally {
			CleanUp.resetSimulationClock();
		}
	}
}
