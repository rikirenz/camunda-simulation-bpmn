package bpsimWrappers;

import java.util.Date;

import bpsim.ParameterValue;
import interfaces.IScenarioParametersWrapper;

public class ScenarioWrapper extends ParametersWrapper implements IScenarioParametersWrapper{

	private String id;
	private String name;
	private String description;
	private Long replication;
	private Long seed;
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

	public Long getReplication() {
		return replication;
	}

	public void setReplication(long replication) {
		this.replication = replication;
	}

	public Long getSeed() {
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

	public Double getDuration() throws Exception {
		return TypeBrain.returnDouble(duration);
	}
	
	public void setDuration(ParameterValue duration) {
		this.duration = duration;
	}

	public Double getWarmup() throws Exception {
		return TypeBrain.returnDouble(warmup);
	}

	public void setWarmup(ParameterValue warmup) {
		this.warmup = warmup;
	}

	public Date getStart() throws Exception {
		return TypeBrain.returnDate(start);
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

}
