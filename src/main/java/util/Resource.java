package util;

public class Resource {

	private String id;
	private Long quantity = (long) 0;
	private Double fixedCost = (double) 0;
	private Double unitCost = (double) 0;
		
	private boolean avaliable = true;
	
	public Resource(String id) {
		this.id = id;
	}

	public Long getQuantity() {
		return quantity;
	}

	public void setQuantity(Long quantity) {
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
		return "quantity: " + quantity + ", fixedCost: " + fixedCost + ", unitCost: " + unitCost;
	}

	
	public synchronized boolean isAvaliable() {
		if (avaliable == true) {
			avaliable = false;
			return true;
		}
		return false;
	}
	
	public void release() {
		avaliable = true;
	}
}