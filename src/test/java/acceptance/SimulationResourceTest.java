package acceptance;

import java.nio.file.Paths;
import java.util.logging.Logger;

import org.junit.Test;

import enso.EnsoApp;
import junit.framework.TestCase;
import simulation.SimulationClock;
import simulation.SimulationCosts;
import testutils.CleanUp;

public class SimulationResourceTest extends TestCase{

	private final static Logger LOGGER = Logger.getLogger("ENSO-APP");

    @Test
    public void test() {
		LOGGER.info("Running the test named SimulationResourceTest");
		assertEquals(true, true);
    }


}