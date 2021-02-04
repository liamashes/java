package edocteel.summary.FindKth;

import java.util.LinkedList;
import java.util.List;

/**
 * Given target, a non-negative integer k and an integer array A sorted in ascending order, find the
 * k closest numbers to target in A, sorted in ascending order by the difference between the number
 * and target. Otherwise, sorted in ascending order by number if the difference is same.
 *
 * <p>Example
 *
 * <p>Example 1:
 *
 * <p>Input: A = [1, 2, 3], target = 2, k = 3 Output: [2, 1, 3] Example 2:
 *
 * <p>Input: A = [1, 4, 6, 8], target = 3, k = 3 Output: [4, 1, 6] Challenge
 *
 * <p>O(logn + k) time
 */
public class Find_K_Closest_Elements {
  public static void main(String[] args) {
    int[] arr = {1, 4, 6, 8};
    int target = 3, k = 3;
    Solution solution = new Solution();
    System.out.println(solution.findCLosestElements(arr, k, target));
  }

  /** 思路：先找到起始start point，然后用打擂台的方法O(K)的去取得最近的值； */
  public static class Solution {
    public List<Integer> findCLosestElements(int[] arr, int k, int x) {
      // ordered, so use linked list
      List<Integer> list = new LinkedList<>();
      // check input
      if (arr == null || arr.length == 0) {
        return list;
      }

      // get nearest index
      int index = findNearest(arr, x);

      // initial param and result
      int count = 0;
      list.add(arr[index]);
      count++;
      int i = index - 1;
      int j = index + 1;
      while (count < k) {
        // dynamic param valid check
        if (0 <= i && j < arr.length) {
          if (x - arr[i] <= arr[j] - x) {
            list.add(arr[i]);
            i--;
          } else {
            list.add(arr[j]);
            j++;
          }
        } else if (i < 0 && j < arr.length) {
          list.add(arr[j]);
          j++;
        } else if (0 <= i && j >= arr.length) {
          list.add(arr[i]);
          i--;
        }
        count++;
      }

      return list;
    }

    private int findNearest(int[] A, int target) {
      int start = 0;
      int end = A.length - 1;

      while (start + 1 < end) {
        // half-way
        int mid = start + (end - start) / 2;
        // break point 1
        if (A[mid] == target) {
          return mid;
        } else if (A[mid] > target) {
          end = mid;
        } else {
          start = mid;
        }
      }

      if (target - A[start] <= A[end] - target) {
        return start;
      }
      return end;
    }
  }
}
