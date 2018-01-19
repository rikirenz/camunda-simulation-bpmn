	package integration;

import java.nio.file.Paths;

import enso.EnsoApp;
import junit.framework.TestCase;
import simulation.SimulationClock;
import testutils.CleanUp;

public class SimulationTimeTest extends TestCase{

	public void testLinearEvents() {
		try {
			
			;
			EnsoApp app = new EnsoApp(
					Paths.get(
						"src","test","java","bpmnprocesses","linear-amazon-test.bpmn"
					),
					"amazon-delivery-test", 
					1, 
					0
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
					Paths.get(
						"src","test","java","bpmnprocesses","parallel-amazon-test.bpmn"
					),
					"amazon-delivery-test",
					1,
					0
			);
			app.startApp();
			
			SimulationClock simClock =  new SimulationClock();
			int currTime = simClock.getCurrentTime();

			assertEquals(10, currTime);

		} finally {
			CleanUp.resetSimulationClock();
		}
	}
	
	public void testMultipleProcess() {
		try {
			EnsoApp app = new EnsoApp(
					Paths.get(
						"src","test","java","bpmnprocesses","parallel-amazon-test.bpmn"
					),
					"amazon-delivery-test",
					10,
					10
			);
			app.startApp();
			
			SimulationClock simClock =  new SimulationClock();
			int currTime = simClock.getCurrentTime();

			assertEquals(100, currTime);

		} finally {
			CleanUp.resetSimulationClock();
		}
	}
	
	public void testMultipleWithDifferentTimes() {
		try {
			EnsoApp app = new EnsoApp(
					Paths.get(
						"src","test","java","bpmnprocesses","linear-amazon-fixed-different-time-test.bpmn"
					),
					"amazon-delivery-test",
					3,
					0
			);
			app.startApp();
			
			SimulationClock simClock =  new SimulationClock();
			int currTime = simClock.getCurrentTime();

			assertEquals(26, currTime);

		} finally {
			CleanUp.resetSimulationClock();
		}
	}
	
	
}
