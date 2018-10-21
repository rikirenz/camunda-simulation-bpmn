package acceptance;

import java.nio.file.Paths;
import java.util.logging.Logger;

import org.junit.Test;

import enso.EnsoApp;
import junit.framework.TestCase;
import simulation.SimulationClock;
import simulation.SimulationCosts;
import simulation.SimulationResource;
import testutils.CleanUp;

public class SimulationResourceTest extends TestCase{

	private final static Logger LOGGER = Logger.getLogger("ENSO-APP");

    @Test
    public void test() {
		LOGGER.info("Running the test named SimulationResourceTest");
		
		SimulationResource simResource = new SimulationResource("testResource");
		simResource.setFixedCost(0.0);
		simResource.setUnitCost(0.0);
		simResource.setQuantity((long)3);
		simResource.setTimeResourceReleased((long)12);
		simResource.setTimeResourceReleased((long)13);
		simResource.setTimeResourceReleased((long)14);
		simResource.setTimeResourceReleased((long)15);

		TestCase.assertEquals((long)simResource.getTimeResourceReleased(), 12);
    }


}