package simulation;

import java.text.MessageFormat;

public class SimulationResult {

	private String processId = "";
	private String activityId = "";
	private String resourceId = "";
	private Double unitCost;
	private Double fixedCost;
	private Long time;
	
	
	public SimulationResult(String processId, String activityId, String resourceId, Double unitCost, Double fixedCost, Long time) {		
		if (processId != null) this.processId = processId; 
		if (activityId != null) this.activityId = activityId; 
		if (resourceId != null) this.resourceId = resourceId; 
		if (unitCost != null) this.unitCost = unitCost; 
		if (fixedCost != null) this.fixedCost = fixedCost; 
		if (time != null) this.time = time;
	}
	
	
	public String getProcessId() {
		return processId;
	}
	public void setProcessId(String processId) {
		this.processId = processId;
	}
	public String getActivityId() {
		return activityId;
	}
	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}
	public String getResourceId() {
		return resourceId;
	}
	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}
	public double getUnitCost() {
		return unitCost;
	}
	public void setUnitCost(double unitCost) {
		this.unitCost = unitCost;
	}
	public double getFixedCost() {
		return fixedCost;
	}
	public void setFixedCost(double fixedCost) {
		this.fixedCost = fixedCost;
	}
	public long getTime() {
		return time;
	}
	public void setTime(long time) {
		this.time = time;
	}
	
	public String toString() {		
		return MessageFormat.format(
					"processId: {0}, activityId: {1}, resourceId: {2}, "
				  + "unitCost: {3}, fixedCost: {4}, time: {5}\n", processId, 
				  activityId, resourceId, unitCost, fixedCost, time
				);
	}
}
