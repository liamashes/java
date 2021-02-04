package edocteel.summary.FindKth;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.PriorityQueue;

/**
 * Given a non-empty list of words, return the k most frequent elements.
 *
 * <p>Your answer should be sorted by frequency from highest to lowest. If two words have the same
 * frequency, then the word with the lower alphabetical order comes first.
 *
 * <p>Example 1:
 *
 * <p>Input: ["i", "love", "leetcode", "i", "love", "coding"], k = 2 Output: ["i", "love"]
 * Explanation: "i" and "love" are the two most frequent words. Note that "i" comes before "love"
 * due to a lower alphabetical order. Example 2:
 *
 * <p>Input: ["the", "day", "is", "sunny", "the", "the", "the", "sunny", "is", "is"], k = 4 Output:
 * ["the", "is", "sunny", "day"] Explanation: "the", "is", "sunny" and "day" are the four most
 * frequent words, with the number of occurrence being 4, 3, 2 and 1 respectively. Note:
 *
 * <p>You may assume k is always valid, 1 ≤ k ≤ number of unique elements. Input words contain only
 * lowercase letters. Follow up:
 *
 * <p>Try to solve it in O(n log k) time and O(n) extra space.
 */
public class Top_K_Frequent_Words {
  public static void main(String[] args) {
    String[] words = {"the", "day", "is", "sunny", "the", "the", "the", "sunny", "is", "is"};
    int k = 4;
    Solution solution = new Solution();
    System.out.println(solution.topKFrequent(words, k));
  }

  // 思路：注意这个题目需要最大到最小输出，用maxheap，Nlogk;
  public static class Solution {
    public List<String> topKFrequent(String[] words, int k) {
      List<String> list = new ArrayList<>();
      HashMap<String, Integer> countMap = new HashMap<>();
      for (String word : words) {
        countMap.put(word, countMap.getOrDefault(word, 0) + 1);
      }
      // initial maxheap, care for a.fre == b.fre
      PriorityQueue<Node> pq =
          new PriorityQueue<>(
              (a, b) -> (a.fre != b.fre ? b.fre - a.fre : a.word.compareTo(b.word)));
      for (String word : countMap.keySet()) {
        pq.offer(new Node(word, countMap.get(word)));
      }
      while (!pq.isEmpty() && k > 0) {
        list.add(pq.poll().word);
        k--;
      }
      return list;
    }

    private class Node {
      public String word;
      public int fre;

      public Node(String word, int fre) {
        this.word = word;
        this.fre = fre;
      }
    }
  }
}
