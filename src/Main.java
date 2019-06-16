import statistics.*;
import montecarlo.*;

import java.awt.geom.Point2D;
import java.io.*;
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
 * <p>
 * Runs the simulation with the DistTwoPoints experiment a large number of times, and writes the results
 * to a text file.
 *
 * @author Luc Wachter
 */
public class Main {
    // Number of simulation to run
    private static final int NBR_OF_SIMULATIONS = 1500;
    // File where the data from the simulations will be written
    private static final String OUTPUT_FILE = "report/analysis/observations.csv";

    // Max half width of the confidence interval = 0.000 05
    private static final double MAX_HALF_WIDTH = 50e-6;
    // Initial number of runs = 1 000 000
    private static final long INITIAL_NBR_OF_RUNS = (long) 10e5;
    // Additional runs to make, if needed = 100 000
    private static final long ADDITIONAL_NBR_OF_RUNS = (long) 10e4;

    /**
     * Launches simulation with following parameters:
     * - Initial number of runs = 1 000 000
     * - Additional runs to make, if needed = 100 000
     * - Max half width of the confidence interval = 0.000 05
     * <p>
     * Writes statistics about the simulations in file.
     */
    public static void main(String[] args) {
        Random rnd = new Random(20190528);

        // The experiment the simulation must run
        Experiment experiment = new DistTwoPoints();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(OUTPUT_FILE))) {
            System.out.println("Writing observations data to " + OUTPUT_FILE);

            // Write headers of CSV file
            writer.write("NumberOfObs;");
            writer.write("Average;");
            writer.write("StandardDeviation;");
            writer.write("Variance;");
            writer.write("95CIHalfWidth\n");

            // Run simulation as many times as defined
            for (int i = 0; i < NBR_OF_SIMULATIONS; i++) {
                // The statistics collector for our simulation
                StatCollector stat = new StatCollector();

                // Launch simulation with defined parameters
                MonteCarloSimulation.simulateTillGivenCIHalfWidth(experiment, MAX_HALF_WIDTH,
                        INITIAL_NBR_OF_RUNS, ADDITIONAL_NBR_OF_RUNS, rnd, stat);

                // Write observations to file
                writer.write(stat.getNumberOfObs() + ";");
                writer.write(stat.getAverage() + ";");
                writer.write(stat.getStandardDeviation() + ";");
                writer.write(stat.getVariance() + ";");
                writer.write(stat.get95ConfidenceIntervalHalfWidth() + "\n");

                System.out.println("Simulation " + (i + 1) + " of " + NBR_OF_SIMULATIONS + " done.");
            }

            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
