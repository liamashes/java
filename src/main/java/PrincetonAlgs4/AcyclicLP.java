package PrincetonAlgs4;

/**
 * The {@code AcyclicLP} class represents a data type for solving the single-source longest paths
 * problem in edge-weighted directed acyclic graphs (DAGs). The edge weights can be positive,
 * negative or zero.
 *
 * <p>This implementation uses a topological-sort based algorithm. The constructor takes
 * &Theta;(<em>V</em> + <em>E</em>) time in the worst case, where <em>V</em> is the number of
 * vertices and <em>E</em> is the number of edges.
 *
 * <p>Each instance method tasks &Theta;(1) Time. It uses &Theta;(<em>V</em>) extra space (not
 * including the edge-weighted digraph).
 */
public class AcyclicLP {
  // distance of longest s->v path
  private double[] disTo;
}
