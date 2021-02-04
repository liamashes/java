package edocteel.summary.BLUTD.Dijkstra;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;

/**
 * Find the Kth Smallest Sum of a Matrix With Sorted Rows
 *
 * <p>You are given an m * n matrix, mat, and an integer k, which has its rows sorted in
 * non-decreasing order.
 *
 * <p>You are allowed to choose exactly 1 element from each row to form an array. Return the Kth
 * smallest array sum among all possible arrays.
 *
 * <p>Example 1:
 *
 * <p>Input: mat = [[1,3,11],[2,4,6]], k = 5 Output: 7 Explanation: Choosing one element from each
 * row, the first k smallest sum are: [1,2], [1,4], [3,2], [3,4], [1,6]. Where the 5th sum is 7.
 * Example 2:
 *
 * <p>Input: mat = [[1,3,11],[2,4,6]], k = 9 Output: 17 Example 3:
 *
 * <p>Input: mat = [[1,10,10],[1,4,5],[2,3,6]], k = 7 Output: 9 Explanation: Choosing one element
 * from each row, the first k smallest sum are: [1,1,2], [1,1,3], [1,4,2], [1,4,3], [1,1,6],
 * [1,5,2], [1,5,3]. Where the 7th sum is 9. Example 4:
 *
 * <p>Input: mat = [[1,1,10],[2,2,9]], k = 7 Output: 12
 *
 * <p>Constraints:
 *
 * <p>m == mat.length n == mat.length[i] 1 <= m, n <= 40 1 <= k <= min(200, n ^ m) 1 <= mat[i][j] <=
 * 5000 mat[i] is a non decreasing array.
 */
public class _1439 {

  public static void main(String[] args) {
    int[][] matrix = {{1, 10, 10}, {1, 4, 5}, {2, 3, 6}};
    Solution1 solution1 = new Solution1();
    int res = solution1.kthSmallest(matrix, 7);
    System.out.println(res);
  }

  public static class Solution1 {
    public int kthSmallest(int[][] mat, int k) {
      if (mat == null || mat.length == 0 || mat[0].length == 0) {
        return 0;
      }
      PriorityQueue<Node> pq = new PriorityQueue<Node>((a, b) -> (a.sum - b.sum));
      List<Integer> indexs = new ArrayList<Integer>();
      // 一定要判断去重复，因为到达一个状态，可能有多个值，多个path过来的；
      HashSet<List<Integer>> visited = new HashSet<>();
      int n = mat.length;
      int m = mat[0].length;
      for (int j = 0; j < n; j++) {
        indexs.add(0);
      }
      pq.offer(new Node(indexs, getSum(indexs, mat)));

      int count = 0;
      while (!pq.isEmpty()) {
        Node node = pq.poll();
        if (visited.contains(node.indexs)) {
          continue;
        }
        visited.add(node.indexs);
        count++;
        if (count == k) {
          return node.sum;
        }
        for (Node neighbor : getAllNeighbor(node, mat)) {
          pq.offer(neighbor);
        }
      }
      return -1;
    }

    private List<Node> getAllNeighbor(Node node, int[][] mat) {
      List<Integer> indexs = node.indexs;
      List<Node> res = new ArrayList<Node>();
      for (int i = 0; i < indexs.size(); i++) {
        List<Integer> newindexs = new ArrayList<Integer>();
        newindexs.addAll(indexs);
        if (newindexs.get(i) + 1 < mat[0].length) {
          newindexs.set(i, newindexs.get(i) + 1);
          res.add(new Node(newindexs, getSum(newindexs, mat)));
        }
      }
      return res;
    }

    private int getSum(List<Integer> indexs, int[][] mat) {
      int sum = 0;
      for (int i = 0; i < mat.length; i++) {
        sum += mat[i][indexs.get(i)];
      }
      return sum;
    }

    private class Node {
      public List<Integer> indexs;
      public int sum;

      public Node(List<Integer> indexs, int sum) {
        this.indexs = indexs;
        this.sum = sum;
      }
    }
  }
}
