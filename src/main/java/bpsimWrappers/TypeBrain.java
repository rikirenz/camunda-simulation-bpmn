package bpsimWrappers;

import java.util.Date;
import java.util.Random;
import java.util.logging.Logger;

import javax.xml.datatype.Duration;

import bpsim.BetaDistribution;
import bpsim.BinomialDistribution;
import bpsim.BooleanParameter;
import bpsim.DateTimeParameter;
import bpsim.DistributionParameter;
import bpsim.DurationParameter;
import bpsim.ErlangDistribution;
import bpsim.FloatingParameter;
import bpsim.GammaDistribution;
import bpsim.LogNormalDistribution;
import bpsim.NegativeExponentialDistribution;
import bpsim.NormalDistribution;
import bpsim.NumericParameter;
import bpsim.ParameterValue;
import bpsim.PoissonDistribution;
import bpsim.StringParameter;
import bpsim.TriangularDistribution;
import bpsim.TruncatedNormalDistribution;
import bpsim.UniformDistribution;
import bpsim.WeibullDistribution;
	
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
				"The ParameterValue type is null or is not supported by the method returnDouble!"
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
			Long distributionResult = (Long) calculateDistribution(currParamValue, long.class);
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
	
	private static Long calculateDistribution(ParameterValue currParamValue, Class<?> returnType) {
		if (currParamValue instanceof LogNormalDistribution) {
			LogNormalDistribution dp = (LogNormalDistribution) currParamValue;
	    } else if (currParamValue instanceof PoissonDistribution) {
	    	PoissonDistribution dp = (PoissonDistribution) currParamValue;
	    } else if (currParamValue instanceof WeibullDistribution) {
	    	WeibullDistribution dp = (WeibullDistribution) currParamValue;
	    } else if (currParamValue instanceof UniformDistribution) {
	    	UniformDistribution dp = (UniformDistribution) currParamValue;
	    } else if (currParamValue instanceof NormalDistribution) {
	    	NormalDistribution dp = (NormalDistribution) currParamValue;
	    } else if (currParamValue instanceof BetaDistribution) {
	    	BetaDistribution dp = (BetaDistribution) currParamValue;
	    } else if (currParamValue instanceof NegativeExponentialDistribution) {
	    	NegativeExponentialDistribution dp = (NegativeExponentialDistribution) currParamValue;
	    } else if (currParamValue instanceof BinomialDistribution) {
	    	BinomialDistribution dp = (BinomialDistribution) currParamValue;
	    } else if (currParamValue instanceof TruncatedNormalDistribution) {
	    	TruncatedNormalDistribution dp = (TruncatedNormalDistribution) currParamValue;
	    } else if (currParamValue instanceof ErlangDistribution) {
	    	ErlangDistribution dp = (ErlangDistribution) currParamValue;
		} else if (currParamValue instanceof TriangularDistribution) {
			TriangularDistribution dp = (TriangularDistribution) currParamValue;
		} else if (currParamValue instanceof GammaDistribution) {
			GammaDistribution dp = (GammaDistribution) currParamValue;
		}

		// to do implement all the distribution		 
		return new Long(5);
		
	}	
	
}
