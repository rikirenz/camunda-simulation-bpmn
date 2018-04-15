package interfaces;

public interface IControlParametersWrapper {
	
	Double getInterTriggerTimer() throws Exception; 
	Long getTriggerCount() throws Exception;
	Double getProbability() throws Exception;
	Boolean getCondition() throws Exception;
	
}