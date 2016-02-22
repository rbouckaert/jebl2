/*
 * EmpiricalDemographicFunction.java
 *
 * (c) 2005 JEBL Development Team
 *
 * This package is distributed under the
 * Lesser Gnu Public Licence (LGPL)
 */
package jebl.evolution.coalescent;

import beast.core.Param;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import beast.core.BEASTObject;
import jebl.evolution.coalescent.DemographicFunction;

/**
 * @author Oliver Pybus
 * @author Andrew Rambaut
 * @version $Id$
 */
public class EmpiricalDemographicFunction extends BEASTObject implements DemographicFunction {

    public EmpiricalDemographicFunction(
		@Param(name="populationSizes", description="auto converted jebl2 parameter") List<Double> populationSizes,
		@Param(name="times", description="auto converted jebl2 parameter") List<Double> times,
		@Param(name="stepwise", description="auto converted jebl2 parameter") Boolean stepwise) {
        this.populationSizes = populationSizes.toArray(new Double[]{});
        this.times = times.toArray(new Double[]{});
        this.stepwise = stepwise;
    }

    /**
     * Gets the value of the demographic function N(t) at time t.
     */
    @Override
	public double getDemographic(double t) {

        assert(t >= 0.0);

        // If time is beyond end of timeseries, then return last value of series
        if (t > times[times.length - 1]) {
            return populationSizes[populationSizes.length - 1];
        }

        if (stepwise) {
            for (int i = 0; i < times.length; i++) {
                if (times[i] >= t) {
                    return populationSizes[i];
                }
            }
        } else {
            for (int i = 0; i < times.length; i++) {
                if (times[i] == t) {
                    return populationSizes[i];
                } else if (times[i] > t) {
                    // Do linear interpolation. I think this works for both t[x]>t[x-1] and t[x]<t[x-1]
                    double proportion = (t - times[i - 1]) / (times[i] - times[i - 1]);
                    double popSize = populationSizes[i-1] + (proportion*(populationSizes[i] - populationSizes[i - 1]));

                    return popSize;
                }
            }
        }

        throw new RuntimeException("Error in jebl.evolution.treesimulation.EmpiricalDemographicFunction.getDemographic: went off the end of the array");
    }


    /**
     * Returns value of demographic intensity function at time t
     * (= integral 1/N(x) dx from 0 to t).
     */
    @Override
	public double getIntensity(double t) {
        throw new UnsupportedOperationException("getIntensity is not implemented in jebl.evolution.treesimulation.EmpiricalDemographicFunction");
    }

    /**
     * Returns value of inverse demographic intensity function
     * (returns time, needed for simulation of coalescent intervals).
     */
    @Override
	public double getInverseIntensity(double x) {
        throw new UnsupportedOperationException("getInverseIntensity is not implemented in jebl.evolution.treesimulation.EmpiricalDemographicFunction");
    }

    @Override
	public boolean hasIntegral() {
        return false;
    }

    @Override
	public double getIntegral(double start, double finish) {
        return 0;
    }

    /**
     * Returns the number of arguments for this function.
     */
    @Override
	public int getArgumentCount() {
        return 0;
    }

    /**
     * Returns the name of the nth argument of this function.
     */
    @Override
	public String getArgumentName(int n) {
        return null;
    }

    /**
     * Returns the value of the nth argument of this function.
     */
    @Override
	public double getArgument(int n) {
        return 0;
    }

    /**
     * Sets the value of the nth argument of this function.
     */
    @Override
	public void setArgument(int n, double value) {
    }

    /**
     * Returns the lower bound of the nth argument of this function.
     */
    @Override
	public double getLowerBound(int n) {
        return 0;
    }

    /**
     * Returns the upper bound of the nth argument of this function.
     */
    @Override
	public double getUpperBound(int n) {
        return 0;
    }

    private Double[] populationSizes;
    private Double[] times;
    private boolean stepwise;
    
	@Override
	public void initAndValidate() {
		// nothing to do
	}


	public List<Double> getPopulationSizes() {
		return Arrays.asList(populationSizes);
	}

	/** should not be used other than by BEAST framework **/
	@Deprecated
	public void setPopulationSizes(Double[] setPopulationSizes) {
		this.populationSizes = populationSizes;
	}
	public void setPopulationSizes(Double setPopulationSize) {
		List<Double> t = getPopulationSizes();
		t.add(setPopulationSize);
		this.populationSizes = t.toArray(new Double[]{});
	}

	public Boolean getStepwise() {
		return stepwise;
	}

	/** should not be used other than by BEAST framework **/
	@Deprecated
	public void setStepwise(Boolean stepwise) {
		this.stepwise = stepwise;
	}

	public List<Double> getTimes() {
		return Arrays.asList(populationSizes);
	}

	/** should not be used other than by BEAST framework **/
	@Deprecated
	public void setTimes(Double[] times) {
		this.times = times;
	}

	public void setTimes(Double times) {
		List<Double> t = getTimes();
		t.add(times);
		this.times = t.toArray(new Double[]{});
	}
}