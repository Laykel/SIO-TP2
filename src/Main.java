import statistics.*;
import montecarlo.*;

import java.awt.geom.Point2D;
import java.util.Random;

/**
 * Implementation of Experiment used for our simulation.
 *
 * @author Luc Wachter
 */
class DistTwoPoints implements Experiment {
    public double execute(Random rnd) {
        // Generate two points in the unit square and return the distance between them
        return Point2D.distance(rnd.nextDouble(), rnd.nextDouble(), rnd.nextDouble(), rnd.nextDouble());
    }
}

/**
 * Main entry point for the Monte Carlo simulation program
 *
 * @author Luc Wachter
 */
public class Main {
    public static void main(String[] args) {
//        Random rnd = new Random(20190528);
        Random rnd = new Random();

        // The statistics collector for our simulation
        StatCollector stat = new StatCollector();

        // The experiment the simulation must run
        Experiment experiment = new DistTwoPoints();

        // Launch simulation with following parameters:
        //      - Initial number of runs = 1 000 000
        //      - Additional runs to make, if needed = 100 000
        //      - Max half width of the confidence interval = 0.000 05
        MonteCarloSimulation.simulateTillGivenCIHalfWidth(experiment, 50e-6,
                                                          (long) 10e5, (long) 10e4, rnd, stat);

        // Store simulation results in file
        System.out.println("Number of observations: " + stat.getNumberOfObs());
        System.out.println("Average: " + stat.getAverage());
        System.out.println("Standard deviation: " + stat.getStandardDeviation());
        System.out.println("Variance: " + stat.getVariance());
        System.out.println("95% CI half width: " + stat.get95ConfidenceIntervalHalfWidth());
    }
}
