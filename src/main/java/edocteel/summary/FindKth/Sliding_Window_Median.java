package edocteel.summary.FindKth;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;

/**
 * Median is the middle value in an ordered integer list. If the size of the list is even, there is
 * no middle value. So the median is the mean of the two middle value.
 *
 * <p>Examples:
 *
 * <p>[2,3,4] , the median is 3
 *
 * <p>[2,3], the median is (2 + 3) / 2 = 2.5
 *
 * <p>Given an array nums, there is a sliding window of size k which is moving from the very left of
 * the array to the very right. You can only see the k numbers in the window. Each time the sliding
 * window moves right by one position. Your job is to output the median array for each window in the
 * original array.
 *
 * <p>For example, Given nums = [1,3,-1,-3,5,3,6,7], and k = 3.
 *
 * <p>Window position Median --------------- ----- [1 3 -1] -3 5 3 6 7 1 1 [3 -1 -3] 5 3 6 7 -1 1 3
 * [-1 -3 5] 3 6 7 -1 1 3 -1 [-3 5 3] 6 7 3 1 3 -1 -3 [5 3 6] 7 5 1 3 -1 -3 5 [3 6 7] 6 Therefore,
 * return the median sliding window as [1,-1,-1,3,5,6].
 */
public class Sliding_Window_Median {
  /**
   * 思路：跟 Find Median from Data Stream类似，用maxheap
   * minheap去维持中位数，不同的地方就是window移动的时候，需要把头部的元素给去掉，注意后面一定要再balance一下；注意balance的时候，minheap.size() <
   * maxheap.size() - 1，不能忍；
   *
   * <p>注意，double的maxheap，不能用(a , b) ->(b - a)，一定要用Collections.reverseOrder();
   */
  public static class Solution {
    PriorityQueue<Integer> maxheap, minheap;

    public double[] medianSlidingWindow(int[] nums, int k) {
      List<Double> list = new ArrayList<>();
      maxheap = new PriorityQueue<>(k / 2 + 1, Collections.reverseOrder());
      minheap = new PriorityQueue<>(k / 2 + 1);

      for (int i = 0; i < nums.length; i++) {
        if (maxheap.size() == 0 || nums[i] <= maxheap.peek()) {
          maxheap.offer(nums[i]);
        } else {
          minheap.offer(nums[i]);
        }
        balance();

        if (i == k - 1) {
          list.add(getMedian());
        }

        if (i > k - 1) {
          remove(nums[i - k]);
          balance();
          list.add(getMedian());
        }
      }

      double[] res = new double[list.size()];
      for (int i = 0; i < list.size(); i++) {
        res[i] = list.get(i);
      }
      return res;
    }

    private double getMedian() {
      if (maxheap.size() == minheap.size()) {
        return 0.5 * (maxheap.peek() + minheap.peek());
      } else {
        return maxheap.peek();
      }
    }

    private void balance() {
      while (maxheap.size() < minheap.size()) {
        maxheap.offer(minheap.poll());
      }

      while (minheap.size() < maxheap.size() - 1) {
        minheap.offer(maxheap.poll());
      }
    }

    private void remove(int num) {
      if (num < maxheap.peek()) {
        maxheap.remove(num);
      } else {
        minheap.remove(num);
      }
    }
  }
}
