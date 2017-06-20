import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    private double openSites[];
    private int trials;

    // perform trials independent experiments on an n-by-n grid
    public PercolationStats(int n, int trials) {
        this.trials = trials;
        if (n <= 0 || trials <= 0) throw new IllegalArgumentException("Invalid arguments n = " + n + "; trials = " + trials);

        openSites = new double[trials];

        for (int i = 0; i < trials; i++) {
            int[] indexes = StdRandom.permutation(n * n);
            Percolation percolation = new Percolation(n);
            int openCount = 0;
            while (!percolation.percolates()) {
                int row = indexes[openCount] / n;
                int col = Math.max(0, indexes[openCount] % n);
                percolation.open( row + 1,  col + 1);
                openCount++;
            }
            openSites[i] = 1.0 * openCount / (n * n);
            StdOut.println(openCount);
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(openSites);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(openSites);
    }

    // low  endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean() - 1.96 * stddev() / Math.sqrt(trials);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean() + 1.96 * stddev() / Math.sqrt(trials);
    }

    // test client (described below)
    public static void main(String[] args) {
        PercolationStats percolationStats = new PercolationStats(Integer.valueOf(args[0]), Integer.valueOf(args[1]));
        StdOut.println("mean = " + percolationStats.mean());
        StdOut.println("stddev = " + percolationStats.stddev());
        StdOut.println("95% confidence interval = [" + percolationStats.confidenceLo() + "], [" + percolationStats.confidenceHi() + "]");
    }
}