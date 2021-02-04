package edocteel.summary.Sorting;

import java.util.PriorityQueue;

/**
 * Merge k Sorted Lists
 *
 * <p>Merge k sorted linked lists and return it as one sorted list. Analyze and describe its
 * complexity.
 *
 * <p>Example:
 *
 * <p>Input: [ 1->4->5, 1->3->4, 2->6 ] Output: 1->1->2->3->4->4->5->6
 */
public class _23 {
  public class ListNode {
    int val;
    ListNode next;

    ListNode() {};

    ListNode(int val) {
      this.val = val;
    }

    ListNode(int val, ListNode next) {
      this.val = val;
      this.next = next;
    }
  }

  public class Solution1 {
    public ListNode mergeKLists(ListNode[] lists) {
      ListNode dumpy = new ListNode();
      PriorityQueue<ListNode> pq = new PriorityQueue<ListNode>((a, b) -> (a.val - b.val));
      for (ListNode node : lists) {
        if (node != null) {
          pq.add(node);
        }
      }

      ListNode cur = dumpy;
      while (!pq.isEmpty()) {
        ListNode node = pq.poll();
        cur.next = node;
        cur = node;
        if (cur.next != null) {
          pq.add(cur.next);
        }
      }

      return dumpy.next;
    }
  }
}
