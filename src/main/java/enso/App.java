package enso;

import java.io.FileNotFoundException;
import java.nio.file.Paths;


public class App {

	public static void main( String[] args ) throws FileNotFoundException {
		EnsoApp app = new EnsoApp(
				Paths.get("src","main","java","bpmnprocesses","parallel-amazon.bpmn"),
				"amazon-delivery-test"
		);
		app.startApp();
	}
}
