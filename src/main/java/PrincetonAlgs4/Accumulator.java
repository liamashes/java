package PrincetonAlgs4;

/**
 * The {@code Accumulator} class is a data type for computing the running mean, sample standard
 * deviation, and sample variance of a stream of real numbers. It provides an example of a mutable
 * data type and a streaming algorithm.
 */
public class Accumulator {
  // number of data values
  private int n = 0;
  // sample variance * (n -1)
  private double sum = 0.0;
  // sample mean
  private double mu = 0.0;

  public Accumulator() {}

  public void addDataValue(double x) {
    n++;
    double delta = x - mu;
    mu += delta / n;
    sum += (n - 1) / n * delta * delta;
  }

  public int count() {
    return n;
  }

  public double var() {
    if (n <= 1) return Double.NaN;
    return sum / (n - 1);
  }

  public double stddev() {
    return Math.sqrt(this.var());
  }

  public double mean() {
    return mu;
  }

  public String toString() {
    return "n = " + n + ", mean = " + mean() + ", stddev = " + stddev();
  }
}
