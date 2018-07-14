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
			SimulationClock simClock =  new SimulationClock();
			SimulationCosts simCosts = new SimulationCosts();
			EnsoApp app = new EnsoApp(
				Paths.get(
					"src","test","java","bpmnprocesses", inputFileName + ".bpmn"
				),
				inputFileName,
				1, 
				0
			);
			// test phase
			app.startApp();
			// verification phase
			long currTime = simClock.getCurrentTime();
			
			assertEquals(timeExpected, currTime);
			assertEquals(costsExpected, simCosts.getTotalCost());
		} finally {
			CleanUp.resetSimulationClock();
		}
    }    
}