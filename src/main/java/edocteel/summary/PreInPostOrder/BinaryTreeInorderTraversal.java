package edocteel.summary.PreInPostOrder;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * Given a binary tree, return the inorder traversal of its nodes' values.
 *
 * <p>For example: Given binary tree {1,#,2,3},
 *
 * <p>1 \ 2 / 3 return [1,3,2].
 *
 * <p>Note: Recursive solution is trivial, could you do it iteratively?
 */
public class BinaryTreeInorderTraversal {
  // recursive
  public static class Solution {
    public List<Integer> inorderTraversal(TreeNode root) {
      List<Integer> list = new ArrayList<>();
      inorderTraversalHelper(root, list);
      return list;
    }

    // left root right
    public void inorderTraversalHelper(TreeNode node, List<Integer> list) {
      if (node == null) {
        return;
      }
      inorderTraversalHelper(node.left, list);
      list.add(node.val);
      inorderTraversalHelper(node.right, list);
    }
  }

  // iteration
  // 用stack 模拟dfs，首先把所有左孩子push到stack，然后pop，如果有右孩子，继续push 右边孩子所有的左孩子；
  // stack里面存的是peek的父亲，所有都是父亲的父亲的父亲；
  public static class Solution1 {
    public List<Integer> inorderTraversal(TreeNode root) {
      List<Integer> list = new ArrayList<>();
      Stack<TreeNode> stack = new Stack<>();

      TreeNode node = root;
      while (node != null) {
        stack.push(node);
        node = node.left;
      }

      while (!stack.isEmpty()) {
        node = stack.pop();
        list.add(node.val);
        if (node.right != null) {
          node = node.right;
          while (node != null) {
            stack.push(node);
            node = node.left;
          }
        }
      }

      return list;
    }
  }

  // morris travel
  // http://www.cnblogs.com/AnnieKim/archive/2013/06/15/morristraversal.html
  //
  // 1. 如果当前节点的左孩子为空，则输出当前节点并将其右孩子作为当前节点。
  //
  // 2. 如果当前节点的左孩子不为空，在当前节点的左子树中找到当前节点在中序遍历下的前驱节点。
  //
  //   a) 如果前驱节点的右孩子为空，将它的右孩子设置为当前节点。当前节点更新为当前节点的左孩子。
  //
  //   b) 如果前驱节点的右孩子为当前节点，将它的右孩子重新设为空（恢复树的形状）。输出当前节点。当前节点更新为当前节点的右孩子。
  //
  // 3. 重复以上1、2直到当前节点为空。

  // http://blog.csdn.net/linhuanmars/article/details/20187257
  // 算法具体分情况如下：
  // 1. 如果当前结点的左孩子为空，则输出当前结点并将其当前节点赋值为右孩子。
  // 2. 如果当前节点的左孩子不为空，则寻找当前节点在中序遍历下的前驱节点（也就是当前结点左子树的最右孩子）。接下来分两种情况：
  // a) 如果前驱节点的右孩子为空，将它的右孩子设置为当前节点（做线索使得稍后可以重新返回父结点）。然后将当前节点更新为当前节点的左孩子。
  // b) 如果前驱节点的右孩子为当前节点，表明左子树已经访问完，可以访问当前节点。将它的右孩子重新设为空（恢复树的结构）。输出当前节点。当前节点更新为当前节点的右孩子。
  public static class Solution2 {
    public List<Integer> inorderTraversal(TreeNode root) {
      List<Integer> res = new ArrayList<>();
      TreeNode cur = root;
      TreeNode pre = null;

      // time:O(n); space:O(1)
      while (cur != null) {
        if (cur.left == null) {
          // 左子树为空，放入当前节点
          res.add(cur.val);
          cur = cur.right;
        } else {
          pre = cur.left;
          // find right most
          while (pre.right != null && pre.right != cur) {
            pre = pre.right;
          }

          if (pre.right == null) {
            // 中序遍历下的前驱节点（当前节点左子树的最右孩子），右节点设置为当前节点（返回线索）
            pre.right = cur;
            cur = cur.left;
          } else {
            // 前驱节点节点为当前节点，代表左子树已经访问完了，恢复结构，输出节点
            pre.right = null;
            res.add(cur.val);
            cur = cur.right;
          }
        }
      }
      return res;
    }
  }

  public class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;

    TreeNode(int x) {
      val = x;
    }

    TreeNode(int val, TreeNode left, TreeNode right) {
      this.left = left;
      this.val = val;
      this.right = right;
    }
  }
}
