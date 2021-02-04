package CCI.Library;

public class TreeNode {
  public int data;
  public TreeNode left;
  public TreeNode right;
  public TreeNode parent;
  private int size = 0;

  public TreeNode(int d) {
    data = d;
    size = 1;
  }

  public void setLeftChild(TreeNode left) {
    this.left = left;
    if (left != null) {
      left.parent = this;
    }
  }

  public void setRightChild(TreeNode right) {
    this.right = right;
    if (right != null) {
      right.parent = this;
    }
  }

  public void insertInOrder(int d) {}
}
