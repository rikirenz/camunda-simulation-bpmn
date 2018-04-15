package interfaces;

import java.util.Date;

public interface IScenarioParametersWrapper {
	
	String getName() throws Exception;
	String getDescription() throws Exception;
	Long getReplication() throws Exception;
	Long getSeed() throws Exception;
	String getBaseTimeUnit() throws Exception;
	String getCurrencyUnit() throws Exception;
	String getBaseResultFrequency() throws Exception;
	Double getDuration() throws Exception;
	Double getWarmup() throws Exception;
	Date getStart() throws Exception;
	String getTraceFormat() throws Exception;
	
}
