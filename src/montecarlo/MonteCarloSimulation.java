package montecarlo;

import java.util.Random;

import statistics.*;

/**
 * This class provides methods for simple Monte Carlo simulations.
 *
 * @author JHH
 * @author Luc Wachter
 */
public class MonteCarloSimulation {
    /**
     * Private constructor. Makes it impossible to instantiate.
     */
    private MonteCarloSimulation() {
    }

    /**
     * Simulates experiment exp n times, using rnd as a source of pseudo-random numbers and collect
     * the results in stat.
     *
     * @param exp  experiment to be run each time
     * @param n    number of runs to be performed
     * @param rnd  random source to be used to simulate the experiment
     * @param stat collector to be used to collect the results of each experiment
     */
    public static void simulateNRuns(Experiment exp,
                                     long n,
                                     Random rnd,
                                     StatCollector stat) {
        for (long run = 0; run < n; ++run) {
            stat.add(exp.execute(rnd));
        }
    }

    /**
     * First simulates experiment exp initialNumberOfRuns times, then estimates the number of runs
     * needed for a 95% confidence interval half width no more than maxHalfWidth. If final C.I. is
     * too wide, simulates additionalNumberOfRuns before recalculating the C.I. and repeats the process
     * as many times as needed.
     * <p>
     * Uses rnd as a source of pseudo-random numbers and collects the results in stat.
     *
     * @param exp                    experiment to be run each time
     * @param maxHalfWidth           maximal half width of the confidence interval
     * @param initialNumberOfRuns    initial number of runs to be performed
     * @param additionalNumberOfRuns additional number of runs to be performed if C.I. is too wide
     * @param rnd                    random source to be used to simulate the experiment
     * @param stat                   collector to be used to collect the results of each experiment
     */
    public static void simulateTillGivenCIHalfWidth(Experiment exp,
                                                    double maxHalfWidth,
                                                    long initialNumberOfRuns,
                                                    long additionalNumberOfRuns,
                                                    Random rnd,
                                                    StatCollector stat) {
        final double normalQuantile = 1.959964;

        // Simulate the experiment initialNumberOfRuns
        simulateNRuns(exp, initialNumberOfRuns, rnd, stat);

        // Estimate the number of runs needed for a 95% C.I. half width smaller than maxHalfWidth
        double estimatedNumberOfRunsNeeded =
                Math.pow(((normalQuantile * stat.getStandardDeviation()) / maxHalfWidth), 2);

        long numberOfRunsRemaining = (long) Math.ceil(estimatedNumberOfRunsNeeded) - initialNumberOfRuns;

        // Continues simulation until N experiments have been made
        simulateNRuns(exp, numberOfRunsRemaining, rnd, stat);

        // In case the final C.I. is too wide, simulate additionalNumberOfRuns times more,
        // until the C.I. is narrow enough
        while (stat.get95ConfidenceIntervalHalfWidth() > maxHalfWidth) {
            simulateNRuns(exp, additionalNumberOfRuns, rnd, stat);
        }
    }
}
