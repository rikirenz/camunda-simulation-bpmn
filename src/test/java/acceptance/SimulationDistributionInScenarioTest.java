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
public class SimulationDistributionInScenarioTest extends TestCase{

	private final static Logger LOGGER = Logger.getLogger("ENSO-APP");
	
	@Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {
        	{"testAllDistribution", (78), (0)}
        });
    }

    private String inputFileName;
    private int timeExpected;
    private double costsExpected;

	    public SimulationDistributionInScenarioTest(String inputFileName, int timeExpected, double costsExpected) {
	        this.inputFileName = inputFileName;
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
			assertEquals(costsExpected, simCosts.getTotalCost());
		} finally {
			CleanUp.resetSimulation();
		}
    }    
}