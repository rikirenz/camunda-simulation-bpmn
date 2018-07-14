
package simulation;
import java.util.logging.Logger;
/*
 * This class keeps track of the 
 * costs in the simulation process
 * */
public class SimulationCosts {

	private final static Logger LOGGER = Logger.getLogger("ENSO-APP");
	
	private static double fixedCosts = 0;
	private static double unitCosts = 0;
		
	public double getTotalCost() {
		return fixedCosts + unitCosts;
	}
	
	public double getFixedCost() {
		return fixedCosts;
	}
	
	public double getUnitCost() {
		return unitCosts;
	}
	
	public synchronized void addUnitCost(double unitCost) {
		this.unitCosts = this.unitCosts + unitCost;
	}
	
	public synchronized void addFixedCost(double fixedCost) {	
		this.fixedCosts = this.fixedCosts + fixedCost;
	}
	
	public void resetCost() {
		fixedCosts = 0;
		unitCosts = 0;
	}	

}
