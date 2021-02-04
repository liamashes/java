package edocteel.summary.PreInPostOrder;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

/**
 * Given a binary tree, return the postorder traversal of its nodes' values.
 *
 * <p>For example: Given binary tree {1,#,2,3},
 *
 * <p>1 \ 2 / 3 return [3,2,1].
 *
 * <p>Note: Recursive solution is trivial, could you do it iteratively?
 */
public class BinaryTreePostorderTraversal {
  // recursive
  public static class Solution {
    public List<Integer> postorderTraversal(TreeNode root) {
      List<Integer> res = new ArrayList<>();
      postorderTraversalHelper(root, res);
      return res;
    }

    public void postorderTraversalHelper(TreeNode node, List<Integer> list) {
      if (node == null) {
        return;
      }
      postorderTraversalHelper(node.left, list);
      postorderTraversalHelper(node.right, list);
      list.add(node.val);
    }
  }

  // iteration
  public static class Solution1 {
    public List<Integer> postorderTraversal(TreeNode root) {
      List<Integer> res = new LinkedList<>();
      if (root == null) {
        return res;
      }
      Stack<TreeNode> stack = new Stack<>();
      stack.push(root);

      while (!stack.isEmpty()) {
        TreeNode node = stack.pop();
        res.add(0, node.val);
        if (node.left != null) {
          stack.push(node.left);
        }
        if (node.right != null) {
          stack.push(node.right);
        }
      }

      return res;
    }
  }

  // morris travel
  // remove stack

  /**
   * http://www.cnblogs.com/AnnieKim/archive/2013/06/15/morristraversal.html
   * 后续遍历稍显复杂，需要建立一个临时节点dump，令其左孩子是root。并且还需要一个子过程，就是倒序输出某两个节点之间路径上的各个节点。
   *
   * <p>步骤：
   *
   * <p>当前节点设置为临时节点dump。
   *
   * <p>1. 如果当前节点的左孩子为空，则将其右孩子作为当前节点。
   *
   * <p>2. 如果当前节点的左孩子不为空，在当前节点的左子树中找到当前节点在中序遍历下的前驱节点。
   *
   * <p>a) 如果前驱节点的右孩子为空，将它的右孩子设置为当前节点。当前节点更新为当前节点的左孩子。
   *
   * <p>b) 如果前驱节点的右孩子为当前节点，将它的右孩子重新设为空。倒序输出从当前节点的左孩子到该前驱节点这条路径上的所有节点。当前节点更新为当前节点的右孩子。
   *
   * <p>3. 重复以上1、2直到当前节点为空。
   *
   * <p>在这里，我们需要创建一个临时的根节点dummy，把它的左孩子设为树的根root。同时还需要一个subroutine来倒序输出一条右孩子路径上的结点。跟迭代法一样我们需要维护cur指针和pre指针来追溯访问的结点。具体步骤是重复以下两步直到结点为空：
   * 1. 如果cur指针的左孩子为空，那么cur设为其右孩子。 2. 否则，在cur的左子树中找到中序遍历下的前驱结点pre（其实就是左子树的最右结点）。接下来分两种子情况：
   * （1）如果pre没有右孩子，那么将他的右孩子接到cur。将cur更新为它的左孩子。
   * （2）如果pre的右孩子已经接到cur上了，说明这已经是回溯访问了，可以处理访问右孩子了，倒序输出cur左孩子到pre这条路径上的所有结点，并把pre的右孩子重新设为空（结点已经访问过了，还原现场）。最后将cur更新为cur的右孩子。
   * 空间复杂度同样是O(1)，而时间复杂度也还是O(n)，倒序输出的过程只是加大了常数系数，并没有影响到时间的量级。如果对这个复杂度结果不是很熟悉的朋友，可以先看看Binary Tree
   * Inorder Traversal中的分析，在那个帖子中讲得比较详细。 起始，理解了算法，代码都好写；按照图来就行了。
   */
  public static class Solution2 {
    public List<Integer> postorderTraversal(TreeNode root) {
      List<Integer> res = new LinkedList<>();
      if (root == null) {
        return res;
      }
      Stack<TreeNode> stack = new Stack<>();
      stack.push(root);

      while (!stack.isEmpty()) {
        TreeNode node = stack.pop();
        res.add(0, node.val);
        if (node.left != null) {
          stack.push(node.left);
        }
        if (node.right != null) {
          stack.push(node.right);
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
