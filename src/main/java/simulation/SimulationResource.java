package simulation;

import java.util.concurrent.PriorityBlockingQueue;
import java.util.logging.Logger;

public class SimulationResource {

	private final static Logger LOGGER = Logger.getLogger("ENSO-APP");
	
	private PriorityBlockingQueue<Long> resourcesTimeQueue = new PriorityBlockingQueue<Long>();
	private String id;
	private Long currentQuantity = (long) 0;
	private Double fixedCost = (double) 0;
	private Double unitCost = (double) 0;
	private Long quantity = (long) 0;
	
	
	public SimulationResource(String id) {
		this.id = id;
	}

	public Long getCurrentQuantity() {
		return quantity;
	}

	public void setQuantity(Long quantity) {
		this.currentQuantity = quantity;
		this.quantity = quantity;
	}

	public Double getFixedCost() {
		return fixedCost;
	}

	public void setFixedCost(Double fixedCost) {
		this.fixedCost = fixedCost;
	}

	public Double getUnitCost() {
		return unitCost;
	}

	public void setUnitCost(Double unitCost) {
		this.unitCost = unitCost;
	}
		
	public String toString() {
		return "Resource Id: " + id + ", currentQuantity: " + currentQuantity + ", quantity: " + quantity + ", fixedCost: " + fixedCost + ", unitCost: " + unitCost;
	}
	
	public boolean isAvaliable() {
		if (currentQuantity == 0) return false;
		currentQuantity--;
		return true;
	}
	
	public void useResource() {
		LOGGER.info("====================== " + resourcesTimeQueue.toString());
		resourcesTimeQueue.remove();
	}
	
	public Long getTimeResourceReleased() {
		return resourcesTimeQueue.peek();
	}

	public void setTimeResourceReleased(Long timeLastResourceHandled) {
		resourcesTimeQueue.add(timeLastResourceHandled);
	}
	
}