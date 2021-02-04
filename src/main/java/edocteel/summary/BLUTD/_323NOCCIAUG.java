package edocteel.summary.BLUTD;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/** number of connected components in an undirected graph */
public class _323NOCCIAUG {

  public static void main(String[] args) {
    Solution solution = new Solution();
    int n = 5;
    int[][] edges = {{0, 1}, {1, 2}, {2, 3}, {3, 4}};
    System.out.println(solution.CountComponents(n, edges, 1));
    n = 5;
    edges = new int[][] {{0, 1}, {1, 2}, {3, 4}};
    System.out.println(solution.CountComponents(n, edges, 1));
  }

  static class Solution {

    /**
     * combine methods
     *
     * @param n number of vertices
     * @param edges edges
     * @param type 0: bfs 1:dfs
     * @return
     */
    public int CountComponents(int n, int[][] edges, int type) {
      if (edges == null) return 0;

      HashMap<Integer, List<Integer>> graph = new HashMap<Integer, List<Integer>>(n);
      for (int[] edge : edges) {
        int a = edge[0];
        int b = edge[1];
        graph.put(a, new LinkedList<Integer>());
        graph.put(b, new LinkedList<Integer>());
        graph.get(a).add(b);
        graph.get(b).add(a);
      }

      int count = 0;
      boolean[] visited = new boolean[n];
      for (int i = 0; i < n; i++) {
        if (!visited[i]) {
          switch (type) {
            case 0:
              bfs(visited, graph, i);
              break;
            case 1:
              dfs(visited, graph, i);
              break;

            default:
              break;
          }
          count++;
        }
      }

      return count;
    }

    /**
     * bfs: visit all nodes connected to start(the node)
     *
     * @param visited whether the node is visited
     * @param graph connections among nodes
     * @param start the node
     */
    private void bfs(boolean[] visited, HashMap<Integer, List<Integer>> graph, int start) {
      Queue<Integer> queue = new LinkedList<Integer>();
      queue.offer(start);
      visited[start] = true;

      while (!queue.isEmpty()) {
        Integer node = queue.poll();
        if (graph.get(node) != null) {
          for (Integer neighbor : graph.get(node)) {
            if (!visited[neighbor]) {
              queue.offer(neighbor);
              visited[neighbor] = true;
            }
          }
        }
      }
    }

    /**
     * dfs: visit all nodes connected to start(the node)
     *
     * @param visited whether the node is visited
     * @param graph connections among nodes
     * @param start the node
     */
    private void dfs(boolean[] visited, HashMap<Integer, List<Integer>> graph, int start) {
      visited[start] = true;
      if (graph.get(start) != null) {
        for (Integer neighbor : graph.get(start)) {
          if (!visited[neighbor]) {
            visited[neighbor] = true;
            dfs(visited, graph, neighbor);
          }
        }
      }
    }
  }
}
