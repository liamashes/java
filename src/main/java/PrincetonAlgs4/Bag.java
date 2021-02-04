package PrincetonAlgs4;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * The {@code Bag} class represents a bag (or multiset) of generic items. It supports insertion and
 * iterating over the items in arbitrary order.
 *
 * <p>core:
 * <li>Node
 * <li>Iterator
 * <li>LinkedIterator
 *
 * @param <Item> the generic type of a item in the bag
 */
public class Bag<Item> implements Iterable<Item> {
  private Node<Item> first; // beginning of bag
  private int n; // number of elements in bag

  public Bag() {
    first = null;
    n = 0;
  }

  public static void main(String[] args) {}

  public boolean isEmpty() {
    return first == null;
  }

  public int size() {
    return n;
  }

  public void add(Item item) {
    Node<Item> oldFirst = first;
    first = new Node<Item>();
    first.item = item;
    first.next = oldFirst;
    n++;
  }

  public Iterator<Item> iterator() {
    return new LinkedIterator(first);
  }

  private static class Node<Item> {
    private Item item;
    private Node<Item> next;
  }

  private class LinkedIterator implements Iterator<Item> {
    private Node<Item> current;

    public LinkedIterator(Node<Item> first) {
      current = first;
    }

    public boolean hasNext() {
      return current != null;
    }

    public void remove() {
      throw new UnsupportedOperationException();
    }

    public Item next() {
      if (!hasNext()) throw new NoSuchElementException();
      Item item = current.item;
      current = current.next;
      return item;
    }
  }
}
