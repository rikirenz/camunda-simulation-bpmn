package bpsimWrappers;

import java.util.Date;
import java.util.Random;
import java.util.logging.Logger;

import javax.xml.datatype.Duration;

import org.apache.commons.math3.distribution.LogNormalDistribution;
import org.apache.commons.math3.distribution.PoissonDistribution;
import org.apache.commons.math3.distribution.BetaDistribution;
import org.apache.commons.math3.distribution.BinomialDistribution;
import org.apache.commons.math3.distribution.ExponentialDistribution;
import org.apache.commons.math3.distribution.GammaDistribution;
import org.apache.commons.math3.distribution.NormalDistribution;
import org.apache.commons.math3.distribution.TriangularDistribution;
import org.apache.commons.math3.distribution.UniformRealDistribution;
import org.apache.commons.math3.distribution.WeibullDistribution;

import bpsim.BooleanParameter;
import bpsim.DateTimeParameter;
import bpsim.DistributionParameter;
import bpsim.DurationParameter;
import bpsim.ErlangDistribution;
import bpsim.FloatingParameter;
import bpsim.NegativeExponentialDistribution;
import bpsim.NumericParameter;
import bpsim.ParameterValue;
import bpsim.StringParameter;
	
/*
 * This class should throws errors because 
 * otherwise is not possible to continue 
 * with the simulation in case a cast is wrong.
 */
public class TypeBrain {
	
	private final static Logger LOGGER = Logger.getLogger("ENSO-APP");
	private static Random randomGenerator = new Random();

	public static Boolean returnBoolean(ParameterValue currParamValue) throws Exception {
		if (currParamValue instanceof BooleanParameter) {
			BooleanParameter bp = (BooleanParameter) currParamValue;
			return bp.isValue();
		} else if (currParamValue instanceof DistributionParameter) {
			double distributionResult = (Double) calculateDistribution(currParamValue, double.class).doubleValue();
			return  (distributionResult % 2) == 0;
		} else {
			throw new Exception(
				"The ParameterValue type is null or is not supported by the method returnBoolean!"
			);
		}
	}

	public static Double returnDouble(ParameterValue currParamValue) throws Exception {
		if (currParamValue instanceof NumericParameter) {
			NumericParameter np = (NumericParameter) currParamValue;
			return np.getValue().doubleValue();
		} else if (currParamValue instanceof FloatingParameter) {
			FloatingParameter np = (FloatingParameter) currParamValue;
			return np.getValue().doubleValue();
		} else if (currParamValue instanceof DistributionParameter) {
			Double distributionResult = (Double) calculateDistribution(currParamValue, double.class).doubleValue();
			return distributionResult.doubleValue();
		} else {
			throw new Exception(
				"The ParameterValue type is null or is not supported by the method returnDouble! " + (currParamValue== null)
			);
		}
	}

	public static Long returnLong(ParameterValue currParamValue) throws Exception {
		if (currParamValue instanceof NumericParameter) {
			NumericParameter np = (NumericParameter) currParamValue;
			return np.getValue().longValue();
		} else if (currParamValue instanceof FloatingParameter) {
			FloatingParameter np = (FloatingParameter) currParamValue;
			return np.getValue().longValue();
		} else if (currParamValue instanceof DistributionParameter) {
			Double distributionResult = calculateDistribution(currParamValue, long.class);
			return distributionResult.longValue();
		} else if (currParamValue instanceof DurationParameter) {
			Duration dp = ((DurationParameter) currParamValue).getValue();
			return new Long(1);
		} else {
			throw new Exception(
				"The ParameterValue type is null or is not supported by the method returnLong!"
			);
		}
	}
	
	public static Date returnDate(ParameterValue currParamValue) throws Exception {
		if (currParamValue instanceof DateTimeParameter) {
			DateTimeParameter dp = (DateTimeParameter) currParamValue;
			return dp.getValue().toGregorianCalendar().getTime();
		} else {
			throw new Exception(
				"The ParameterValue type is null or is not supported by the method returnDate!"
			);
		}		
	}
	
		
	public static String returnString(ParameterValue currParamValue) throws Exception {
		if (currParamValue instanceof StringParameter) {
			StringParameter sp = (StringParameter) currParamValue;
			return sp.getValue();
		} else {
			throw new Exception(
				"The ParameterValue type is null or is not supported by the method returnString!"
			);
		}
	}
	
	private static Double calculateDistribution(ParameterValue currParamValue, Class<?> returnType) {
		Double a = new Double(0);
		if (currParamValue instanceof bpsim.LogNormalDistribution) {
			bpsim.LogNormalDistribution dp = (bpsim.LogNormalDistribution) currParamValue;	
			a =  new LogNormalDistribution(dp.getMean(), dp.getStandardDeviation()).sample();
	    } else if (currParamValue instanceof bpsim.PoissonDistribution) {
	    	bpsim.PoissonDistribution dp = (bpsim.PoissonDistribution) currParamValue;
	    	a = (double) new PoissonDistribution(dp.getMean()).sample();
	    } else if (currParamValue instanceof bpsim.WeibullDistribution) {
	    	bpsim.WeibullDistribution dp = (bpsim.WeibullDistribution) currParamValue;
	    	a = new WeibullDistribution(dp.getScale(), dp.getScale()).sample();
	    } else if (currParamValue instanceof bpsim.UniformDistribution) {
	    	bpsim.UniformDistribution dp = (bpsim.UniformDistribution) currParamValue;
	    	a = new UniformRealDistribution(dp.getMin(), dp.getMax()).sample(); 
	    } else if (currParamValue instanceof bpsim.NormalDistribution) {
	    	bpsim.NormalDistribution dp = (bpsim.NormalDistribution) currParamValue;
	    	a = new NormalDistribution(dp.getMean(),dp.getStandardDeviation()).sample();
	    } else if (currParamValue instanceof bpsim.BetaDistribution) {
	    	bpsim.BetaDistribution dp = (bpsim.BetaDistribution) currParamValue;
	    	a = new BetaDistribution(dp.getShape(), dp.getScale()).sample();
	    } else if (currParamValue instanceof bpsim.NegativeExponentialDistribution) {
	    	bpsim.NegativeExponentialDistribution dp = (bpsim.NegativeExponentialDistribution) currParamValue;
	    	a =  new ExponentialDistribution(dp.getMean()).sample();
	    } else if (currParamValue instanceof bpsim.BinomialDistribution) {
	    	bpsim.BinomialDistribution dp = (bpsim.BinomialDistribution) currParamValue;
	    	a = (double) new BinomialDistribution(dp.getTrials().intValue(), dp.getProbability()).sample();
	    } else if (currParamValue instanceof bpsim.TruncatedNormalDistribution) {
	    	bpsim.TruncatedNormalDistribution dp = (bpsim.TruncatedNormalDistribution) currParamValue;
	    	a = new Double(9);
	    } else if (currParamValue instanceof bpsim.ErlangDistribution) {
	    	bpsim.ErlangDistribution dp = (bpsim.ErlangDistribution) currParamValue;
	    	a = new Double(10);
		} else if (currParamValue instanceof bpsim.TriangularDistribution) {
			bpsim.TriangularDistribution dp = (bpsim.TriangularDistribution) currParamValue;
			a = new TriangularDistribution(dp.getMin(), dp.getMax(), dp.getMode()).sample();
		} else if (currParamValue instanceof bpsim.GammaDistribution) {
			bpsim.GammaDistribution dp = (bpsim.GammaDistribution) currParamValue;
			a = new GammaDistribution(dp.getShape(),dp.getScale()).sample();
		}
		
		// to do implement all the distribution		 
		return a;
	}	
}
