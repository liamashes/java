package edocteel.summary.PreInPostOrder;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * Given a binary tree, return the preorder traversal of its nodes' values.
 *
 * <p>For example: Given binary tree {1,#,2,3},
 *
 * <p>1 \ 2 / 3 return [1,2,3].
 *
 * <p>Note: Recursive solution is trivial, could you do it iteratively?
 */
public class BinaryTreePreorderTraversal {
  // recursive
  public static class Solution {
    public List<Integer> preorderTraversal(TreeNode root) {
      List<Integer> list = new ArrayList<>();
      preorderTraversalHelper(root, list);
      return list;
    }

    public void preorderTraversalHelper(TreeNode root, List<Integer> list) {
      if (root == null) {
        return;
      }
      list.add(root.val);
      preorderTraversalHelper(root.left, list);
      preorderTraversalHelper(root.right, list);
    }
  }

  /**
   * Recusion, 用的是stack, 所以，如果要用iterative 写，必须得用stack;
   *
   * <p>思路：
   *
   * <p>1.print out current node;
   *
   * <p>2. push right tree if it is !null;
   *
   * <p>3. 然后go to left tree; ( push left tree, and pop up first node and continue)
   */
  public static class Solution1 {
    public List<Integer> preorderTraversal(TreeNode root) {
      List<Integer> list = new ArrayList<>();
      Stack<TreeNode> stack = new Stack<>();
      if (root == null) {
        return list;
      }
      TreeNode node = root;
      stack.push(node);
      while (!stack.isEmpty()) {
        node = stack.pop();
        list.add(node.val);
        if (node.right != null) {
          stack.push(node.right);
        }
        if (node.left != null) {
          stack.push(node.left);
        }
      }
      return list;
    }
  }

  /**
   * Morris Travel, space O(1), time O(n)
   * http://www.cnblogs.com/AnnieKim/archive/2013/06/15/morristraversal.html
   *
   * <p>前序遍历与中序遍历相似，代码上只有一行不同，不同就在于输出的顺序。
   *
   * <p>步骤：
   *
   * <p>1. 如果当前节点的左孩子为空，则输出当前节点并将其右孩子作为当前节点。
   *
   * <p>2. 如果当前节点的左孩子不为空，在当前节点的左子树中找到当前节点在中序遍历下的前驱节点。
   *
   * <p>a) 如果前驱节点的右孩子为空，将它的右孩子设置为当前节点。输出当前节点（在这里输出，这是与中序遍历唯一一点不同）。当前节点更新为当前节点的左孩子。
   *
   * <p>b) 如果前驱节点的右孩子为当前节点，将它的右孩子重新设为空。当前节点更新为当前节点的右孩子。
   *
   * <p>3. 重复以上1、2直到当前节点为空。
   */
  public static class Solution2 {
    public List<Integer> preorderTraversal(TreeNode root) {
      List<Integer> list = new ArrayList<>();
      TreeNode cur = root;
      // 中序遍历下的前驱节点
      TreeNode pre = null;

      while (cur != null) {
        if (cur.left == null) {
          list.add(cur.val);
          cur = cur.right;
        } else {
          pre = cur.left;
          while (pre.right != null && pre.right != cur) {
            pre = pre.right;
          }
          if (pre.right == null) {
            pre.right = cur;
            list.add(cur.val);
            cur = cur.left;
          } else {
            pre.right = null;
            cur = cur.right;
          }
        }
      }
      return list;
    }
  }

  private class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;

    TreeNode(int x) {
      val = x;
    }
  }
}
