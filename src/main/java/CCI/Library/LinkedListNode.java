package CCI.Library;

public class LinkedListNode {

  public LinkedListNode next;
  public LinkedListNode prev;
  public LinkedListNode last;

  public int data;

  public LinkedListNode(int d, LinkedListNode n, LinkedListNode p) {
    data = d;
    setNext(n);
    setPrev(p);
  }

  public LinkedListNode() {}

  public void setNext(LinkedListNode n) {
    next = n;
    if (this == last) {
      last = n;
    }
    if (n != null && n.prev != this) {
      n.setPrev(this);
    }
  }

  public void setPrev(LinkedListNode p) {
    prev = p;
    if (p != null && p.next != this) {
      p.setNext(this);
    }
  }

  public String printForward() {
    if (next != null) {
      return data + "->" + next.printForward();
    } else {
      return ((Integer) data).toString();
    }
  }

  public LinkedListNode clone() {
    LinkedListNode next2 = null;
    if (next != null) {
      next2 = next.clone();
    }
    LinkedListNode head2 = new LinkedListNode(data, next2, null);
    return head2;
  }
}
