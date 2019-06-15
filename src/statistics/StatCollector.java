package statistics;

/**
 * This class provides useful methods for collecting one dimensional data (of type double) and for computing basic statistics.
 *
 * @author JHH
 */
public class StatCollector {
    private long numberOfObs;
    private double sumOfObs;
    private double sumOfSquaredObs;

    /**
     * Creates a new collector and initializes it
     */
    public StatCollector() {
        init();
    }

    /**
     * Initializes the collector
     */
    public void init() {
        numberOfObs = 0L;
        sumOfObs = 0.0;
        sumOfSquaredObs = 0.0;
    }

    /**
     * Adds a new observation to this collector.
     *
     * @param x observation to be added to this collector
     */
    public void add(double x) {
        numberOfObs++;
        sumOfObs += x;
        sumOfSquaredObs += x * x;
    }

    /**
     * Returns the number of observations added to this collector since its last initialization.
     *
     * @return the number of added observations since last initialization
     */
    public long getNumberOfObs() {
        return numberOfObs;
    }

    /**
     * Returns the average of the collected observations since its last initialization.
     * <p>
     * If no observations were added since last initialization, Double.NaN is returned.
     *
     * @return the average value of the collected observations
     */
    public double getAverage() {
        if (numberOfObs == 0) {
            return Double.NaN;
        } else {
            return sumOfObs / numberOfObs;
        }
    }

    /**
     * Returns the sample variance of the collected observations since its last initialization.
     * <p>
     * If this collection contains less than two observations, Double.NaN is returned.
     *
     * @return the sample variance of the collected observations
     */
    public double getVariance() {
        if (numberOfObs < 2) {
            return Double.NaN;
        } else {
            return sumOfSquaredObs / (numberOfObs - 1) - (sumOfObs / numberOfObs) * (sumOfObs / (numberOfObs - 1));
        }
    }

    /**
     * Returns the sample standard deviation of the collected observations since its last initialization.
     * <p>
     * If this collection contains less than two observations, Double.NaN is returned.
     *
     * @return the sample standard deviation of the collected observations
     */
    public double getStandardDeviation() {
        if (numberOfObs < 2) {
            return Double.NaN;
        } else {
            return Math.sqrt(getVariance());
        }
    }

    /**
     * Computes a confidence interval with a confidence level of 95% for the mean of the collected observations
     * and returns half of the interval width.
     * <p>
     * If this collection contains less than two observations, Double.NaN is returned.
     *
     * @return half of the interval width for the C.I. (95%) for the mean of the observations
     */
    public double get95ConfidenceIntervalHalfWidth() {
        final double normalQuantile = 1.959964;

        if (numberOfObs < 2) {
            return Double.NaN;
        } else {
            return normalQuantile * getStandardDeviation() / Math.sqrt(numberOfObs);
        }
    }
}
