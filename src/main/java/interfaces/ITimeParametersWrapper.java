package interfaces;

public interface ITimeParametersWrapper {
	
 	Long getTransferTime() throws Exception; 
	Long getQueueTime() throws Exception; 
	Long getWaitTime() throws Exception; 
	Long getSetupTime() throws Exception; 
	Long getProcessingTime() throws Exception; 
	Long getValidationTime() throws Exception; 
	Long getReworkTime() throws Exception; 
	Long getLagTime() throws Exception; 
	Long getDuration() throws Exception; 
	Long getElapsedTime() throws Exception;

}
