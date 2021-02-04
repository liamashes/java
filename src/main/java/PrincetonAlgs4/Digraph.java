package PrincetonAlgs4;

import java.util.NoSuchElementException;

/**
 * The {@code Digraph} class represents a directed graph of vertices named 0 through <em>V</em> -1.
 */
public class Digraph {
  private static final String NEWLINE = System.getProperty("line.separator");

  private final int V; // number of vertices in this digraph
  private int E; // number of edges

  // adjacency list for vertex x
  // BAG[BAG1,BAG2,...BAGN]
  private Bag<Integer>[] adj;

  private int[] indegree; // indegree of vertex x

  public Digraph(int V) {
    if (V < 0)
      throw new IllegalArgumentException("Number of vertices in a Digraph must be non negative");

    this.V = V;
    this.E = 0;
    adj = (Bag<Integer>[]) new Bag[V];
    indegree = new int[V];
    for (int v = 0; v < V; v++) {
      adj[v] = new Bag<Integer>();
    }
  }

  public Digraph(In in) {
    if (in == null) throw new IllegalArgumentException("argument is null");
    try {
      this.V = in.readInt();
      if (V < 0)
        throw new IllegalArgumentException("number of vertices in a Digraph must be non-negative");

    } catch (NoSuchElementException e) {
      throw new IllegalArgumentException("invalid input format in Digraph constructor", e);
    }
  }
}
