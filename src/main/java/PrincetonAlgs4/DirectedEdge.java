package PrincetonAlgs4;

/**
 * The {@code DirectedEdge} class represents a weighted edge in an {@link EdgeWeightedDigraph}. Each
 * edge consists of two integers(naming the two vertices) and a real-value weight. The data type
 * provides methods for accessing the two endpoints of the directed edge and the weight.
 */
public class DirectedEdge {
  private final int v;
  private final int w;
  private final double weight;

  public DirectedEdge(int v, int w, double weight) {
    if (v < 0) throw new IllegalArgumentException("vertex names must be nonnegative integers");
    if (w < 0) throw new IllegalArgumentException("vertex names must be nonnegative integers");
    if (Double.isNaN(weight)) throw new IllegalArgumentException("weight is NaN");
    this.v = v;
    this.w = w;
    this.weight = weight;
  }

  public static void main(String[] args) {
    DirectedEdge edge = new DirectedEdge(12, 34, 5.67);
    StdOut.println(edge.toString());
  }

  public int from() {
    return v;
  }

  public int to() {
    return w;
  }

  public double weight() {
    return weight;
  }

  @Override
  public String toString() {
    return v + "->" + w + "" + String.format("%5.2f", weight);
  }
}
