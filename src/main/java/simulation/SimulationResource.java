package simulation;

public class SimulationResource {

	private String id;
	private Long currentQuantity = (long) 0;
	private Double fixedCost = (double) 0;
	private Double unitCost = (double) 0;
	private Long quantity = (long) 0;
	private Long timeLastResourceHandled = (long) 0;
	private Long updateCounter = (long) 0;
	
	
	public SimulationResource(String id) {
		this.id = id;
	}

	public Long getCurrentQuantity() {
		return quantity;
	}

	public void setQuantity(Long quantity) {
		this.updateCounter = quantity;
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
		return "currentQuantity: " + currentQuantity + ", quantity: " + quantity + ", fixedCost: " + fixedCost + ", unitCost: " + unitCost;
	}
	
	public boolean isAvaliable() {
		if (currentQuantity == 0) return false;
		currentQuantity--;
		return true;
	}
	
	public Long getTimeLastResourceHandled() {
		return timeLastResourceHandled;
	}

	public void setTimeLastResourceHandled(Long timeLastResourceHandled) {
		if (this.updateCounter == this.quantity)
			this.timeLastResourceHandled = timeLastResourceHandled;
	
		this.updateCounter--;
		
		if (this.updateCounter == 0) this.updateCounter = this.quantity;
	}
	
}