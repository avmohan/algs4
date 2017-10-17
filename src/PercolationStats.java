import static edu.princeton.cs.algs4.StdOut.println;

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

/**
 * Created by avmohan on 16/10/17.
 */
public class PercolationStats {

  private final double mean;
  private final double stddev;
  private final double confidenceLo;
  private final double confidenceHi;

  // perform trials independent experiments on an n-by-n grid
  public PercolationStats(int n, int trials) {
    if (n <= 0 || trials <= 0) {
      throw new IllegalArgumentException();
    }
    double[] results = new double[trials];
    for (int trial = 0; trial < trials; trial++) {
      Percolation percolation = new Percolation(n);
      int[] openSequence = generateOpenSequence(n);
      for (int cell : openSequence) {
        percolation.open(getRow(cell, n), getCol(cell, n));
        if (percolation.percolates()) {
          break;
        }
      }
      results[trial] = (percolation.numberOfOpenSites() * 1.0) / (n * n);
    }
    mean = StdStats.mean(results);
    stddev = StdStats.stddev(results);
    double confidenceDelta = (1.96 * stddev) / Math.sqrt(trials);
    confidenceLo = mean - confidenceDelta;
    confidenceHi = mean + confidenceDelta;
  }

  private int[] generateOpenSequence(int n) {
    int[] sequence = new int[n * n];
    for (int i = 0; i < n * n; i++) {
      sequence[i] = i + 1;
    }
    StdRandom.shuffle(sequence);
    return sequence;
  }

  private static int getRow(int cell, int n) {
    return (cell - 1) / n + 1;
  }

  private static int getCol(int cell, int n) {
    return cell % n + 1;
  }

  // sample mean of percolation threshold
  public double mean() {
    return mean;
  }

  // sample standard deviation of percolation threshold
  public double stddev() {
    return stddev;
  }

  // low  endpoint of 95% confidence interval
  public double confidenceLo() {
    return confidenceLo;
  }

  // high endpoint of 95% confidence interval
  public double confidenceHi() {
    return confidenceHi;
  }

  // test client (described below)
  public static void main(String[] args) {
    int n = Integer.parseInt(args[0]);
    int trials = Integer.parseInt(args[1]);
    PercolationStats stats = new PercolationStats(n, trials);
    println(String.format("%-23s", "mean") + " = " + stats.mean());
    println(String.format("%-23s", "stddev") + " = " + stats.stddev());
    println(String.format("%-23s",
        "95% confidence interval") + " = [" + stats.confidenceLo() + ", " + stats.confidenceHi()
        + "]");
  }

}
