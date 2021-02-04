package PrincetonAlgs4;

/**
 * The {@code Topological} class represents a data type for determining a topological order of a
 * <em>directed acyclic graph</em> (DAG). A digraph has a topological order if and only if it is a
 * DAG. The <em>hasOrder</em> operation determines whether the digraph has a topological order, and
 * if so, the <em>order</em> operation returns one.
 *
 * <p>This implementation uses depth-first search. The constructor takes &Theta;(<em>V</em> +
 * <em>E</em>) time in the worst case, where <em>V</em> is the number of vertices and <em>E</em> is
 * the number of edges. Each instance methods takes &Theta;(1) time. It uses &Theta;(<em>V</em>)
 * extra space (not including the digraph).
 */
public class Topological {
  private Iterable<Integer> order;
  private int[] rank; // rank of vertex v in order

  public Topological() {}
}
