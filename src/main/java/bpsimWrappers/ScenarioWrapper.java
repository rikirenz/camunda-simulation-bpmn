package bpsimWrappers;

import bpsim.DateTimeParameter;
import bpsim.DurationParameter;
import bpsim.Parameter;
import bpsim.ParameterValue;
import bpsim.ScenarioParameters;

public class ScenarioWrapper {

	private String id;
	private String name;
	private String description;
	private int replication;
	private long seed;
	private String baseTimeUnit;
	private String currencyUnit;
	private String baseResultFrequency;
	private String traceFormat;
	private ParameterValue duration;
	private ParameterValue warmup;
	private ParameterValue start;

	public ScenarioWrapper() {}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getReplication() {
		return replication;
	}

	public void setReplication(int replication) {
		this.replication = replication;
	}

	public long getSeed() {
		return seed;
	}

	public void setSeed(long seed) {
		this.seed = seed;
	}

	public String getBaseTimeUnit() {
		return baseTimeUnit;
	}

	public void setBaseTimeUnit(String baseTimeUnit) {
		this.baseTimeUnit = baseTimeUnit;
	}

	public String getCurrencyUnit() {
		return currencyUnit;
	}

	public void setCurrencyUnit(String currencyUnit) {
		this.currencyUnit = currencyUnit;
	}

	public String getBaseResultFrequency() {
		return baseResultFrequency;
	}

	public void setBaseResultFrequency(String baseResultFrequency) {
		this.baseResultFrequency = baseResultFrequency;
	}

	public ParameterValue getDuration() {
		return duration;
	}

	public void setDuration(ParameterValue duration) {
		this.duration = duration;
	}

	public ParameterValue getWarmup() {
		return warmup;
	}

	public void setWarmup(ParameterValue warmup) {
		this.warmup = warmup;
	}

	public ParameterValue getStart() {
		return start;
	}

	public void setStart(ParameterValue start) {
		this.start = start;
	}

	public String getTraceFormat() {
		return traceFormat;
	}

	public void setTraceFormat(String traceFormat) {
		this.traceFormat = traceFormat;
	}
	
	public String toString() {
		return " - id:" + id + " - name:" + name + " - description:" + description + " - replication:" + replication + 
			   " - seed:" + seed + " - baseTimeUnit:" + baseTimeUnit + " - currencyUnit:" + currencyUnit + 
			   " - baseResultFrequency:" + baseResultFrequency + " - duration:" + duration + " - warmup:" + warmup + 
			   " - start:" + start + " - traceFormat:" + traceFormat;
	}
	
}
