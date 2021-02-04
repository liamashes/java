package edocteel.summary.FindKth;

import java.util.LinkedList;
import java.util.PriorityQueue;

/**
 * Numbers keep coming, return the median of numbers at every time a new number added.
 *
 * <p>Example
 *
 * <p>Example 1
 *
 * <p>Input: [1,2,3,4,5] Output: [1,1,2,2,3] Explanation: The medium of [1] and [1,2] is 1. The
 * medium of [1,2,3] and [1,2,3,4] is 2. The medium of [1,2,3,4,5] is 3. Example 2
 *
 * <p>Input: [4,5,1,3,2,6,0] Output: [4,4,4,3,3,3,3] Explanation: The medium of [4], [4,5], [4,5,1]
 * is 4. The medium of [4,5,1,3], [4,5,1,3,2], [4,5,1,3,2,6] and [4,5,1,3,2,6,0] is 3. Challenge
 *
 * <p>Total run time in O(nlogn).
 *
 * <p>Clarification
 *
 * <p>What's the definition of Median?
 *
 * <p>The median is not equal to median in math. Median is the number that in the middle of a sorted
 * array. If there are n numbers in a sorted array A, the median is A[(n - 1) / 2]A[(n−1)/2]. For
 * example, if A=[1,2,3], median is 2. If A=[1,19], median is 1.
 */

/**
 * Your MedianFinder object will be instantiated and called as such:
 *
 * <p>MedianFinder obj = new MedianFinder();
 *
 * <p>obj.addNum(num);
 *
 * <p>double param_2 = obj.findMedian();
 */
public class Find_Median_from_Data_Stream {
  public static void main(String[] args) {
    int[] arr = {4, 5, 1, 3, 2, 6, 0};
    LinkedList<Integer> result = new LinkedList<Integer>();
    Solution.MedianFinder obj = new Solution.MedianFinder();
    for (int num : arr) {
      obj.addNum(num);
      result.add((int) obj.findMedian());
    }
    System.out.println(result);
  }

  // 用两个堆来维持关系，左边用最大堆，右边用最小堆，如果num[i] 比最大堆的堆顶小，加入最大堆，否则加入最小堆，然后再调整数目，始终保证最大堆比最小堆数目相等，或者只大于1；
  public static class Solution {
    static class MedianFinder {
      private PriorityQueue<Integer> maxheap;
      private PriorityQueue<Integer> minheap;

      public MedianFinder() {
        maxheap = new PriorityQueue<>((a, b) -> (b - a));
        minheap = new PriorityQueue<>();
      }

      public void addNum(int num) {
        if (maxheap.isEmpty() || num < maxheap.peek()) {
          maxheap.add(num);
        } else {
          minheap.add(num);
        }
        balance();
      }

      public double findMedian() {
        if (maxheap.size() == minheap.size()) {
          return 0.5 * (maxheap.peek() + minheap.peek());
        } else {
          return maxheap.peek();
        }
      }

      private void balance() {
        while (maxheap.size() < minheap.size()) {
          maxheap.add(minheap.poll());
        }

        while (minheap.size() < maxheap.size() - 1) {
          minheap.add(maxheap.poll());
        }
      }
    }
  }
}
