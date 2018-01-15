package enso;

import java.io.FileNotFoundException;
import java.nio.file.Paths;
import java.util.logging.Logger;


public class App {

	private final static Logger LOGGER = Logger.getLogger("ENSO-APP");

	public static void main( String[] args ) throws FileNotFoundException {
		// init app
		EnsoApp app = new EnsoApp(
			Paths.get("src","main","java","bpmprocesses", System.getProperty("bpmn")),
			"amazon-delivery-test",
			Integer.valueOf(System.getProperty("instancesNumber")),
			Integer.valueOf(System.getProperty("delayBetweenInstances"))
			
		);
		// start app
		app.startApp();
		// kill app
		System.exit(0);
	}
}
