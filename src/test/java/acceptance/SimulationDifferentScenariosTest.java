package acceptance;

import static org.junit.Assert.assertEquals;

import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collection;
import java.util.logging.Logger;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import enso.EnsoApp;
import junit.framework.TestCase;
import simulation.SimulationClock;
import simulation.SimulationCosts;
import testutils.CleanUp;


@RunWith(Parameterized.class)
public class SimulationDifferentScenariosTest extends TestCase{

	private final static Logger LOGGER = Logger.getLogger("ENSO-APP");
	
	@Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {     
        	{"singleTaskProcess", 365, 210},
        	{"parallelTasks", (365*2), (210*3)}, // 2 resources, 3 Tasks
        	{"parallelTasksMultipleResources", 26, (210*4)}, // 2 resources, 3 Tasks
        	{"parallelTasksMultipleResources2", 25, (210*4)}, // 2 resources, 3 Tasks
        	{"parallelTasksMultipleResources3", 38, (210*5)}, // 2 resources, 3 Tasks
        	{"xor", 365, 210},
        	{"boundaryEventTimer", (365*2), (210*2)},
        	{"intermediateEvent", 365, 210},
        	{"transaction", (365*2), (210*2)},
        	{"nonInterruptingSubprocessEvent", (365*2), (210*4)},
        	{"interruptingSubprocessEvent", (365*2), (210*3)},
        });
    }

    private String inputFileName;
    private int timeExpected;
    private double costsExpected;

    public SimulationDifferentScenariosTest(String inputFileName, int timeExpected, double costsExpected) {
        this.inputFileName = inputFileName;
        this.timeExpected = timeExpected;
        this.costsExpected = costsExpected;
    }

    @Test
    public void test() {
		try {
			// init phase
			LOGGER.info("Running the test named:" + inputFileName);
			SimulationClock simClock =  new SimulationClock();
			SimulationCosts simCosts = new SimulationCosts();
			EnsoApp app = new EnsoApp(
				Paths.get(
					"src","test","java","bpmnprocesses", inputFileName + ".bpmn"
				),
				inputFileName,
				1, 
				0,
				false
			);
			// test phase
			app.startApp();
			// verification phase
			long currTime = simClock.getCurrentTime();
			
			assertEquals(timeExpected, currTime);
			assertEquals(costsExpected, simCosts.getTotalCost());
		} finally {
			CleanUp.resetSimulation();
		}
    }    
}