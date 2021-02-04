package CCI.Library;

import java.awt.*;
import java.util.ArrayList;

public class AssortedMethods {

  public static TreeNode randomBST(int N, int min, int max) {
    int d = randomIntInRange(min, max);
    TreeNode root = new TreeNode(d);
    for (int i = 1; i < N; i++) {
      root.insertInOrder(randomIntInRange(min, max));
    }
    return root;
  }

  public static int randomInt(int n) {
    return (int) (Math.random() * n);
  }

  public static int randomIntInRange(int min, int max) {
    return randomInt(max + 1 - min) + min;
  }

  public static boolean randomBoolean() {
    return randomIntInRange(0, 1) == 0;
  }

  public static boolean randomBoolean(int percentTrue) {
    return randomIntInRange(1, 100) <= percentTrue;
  }

  public static int[][] randomMatrix(int M, int N, int min, int max) {
    int[][] matrix = new int[M][N];
    for (int i = 0; i < M; i++) {
      for (int j = 0; j < N; j++) {
        matrix[i][j] = randomIntInRange(min, max);
      }
    }
    return matrix;
  }

  public static int[] randomArray(int N, int min, int max) {
    int[] array = new int[N];
    for (int i = 0; i < N; i++) {
      array[i] = randomIntInRange(min, max);
    }
    return array;
  }

  public static LinkedListNode randomLinkedList(int N, int min, int max) {
    LinkedListNode root = new LinkedListNode(randomIntInRange(min, max), null, null);
    LinkedListNode prev = root;
    for (int i = 1; i < N; i++) {
      int data = randomIntInRange(min, max);
      LinkedListNode next = new LinkedListNode(data, null, null);
      prev.setNext(next);
      next.setPrev(prev);
      prev = next;
    }
    return root;
  }

  public static LinkedListNode linkedListWithValue(int N, int value) {
    return randomLinkedList(N, value, value);
  }

  public static LinkedListNode createLinkedListFromArray(int[] vals) {
    LinkedListNode head = new LinkedListNode(vals[0], null, null);
    LinkedListNode current = head;
    for (int i = 0; i < vals.length; i++) {
      current = new LinkedListNode(vals[i], null, current);
    }
    return head;
  }

  public static String arrayToString(int[] array) {
    StringBuilder sb = new StringBuilder();
    for (int v : array) {
      sb.append(v);
      sb.append(",");
    }
    return sb.toString();
  }

  public static String stringArrayToString(String[] array) {
    StringBuilder sb = new StringBuilder();
    for (String v : array) {
      sb.append(v);
      sb.append(",");
    }
    return sb.toString();
  }

  public static String toFullBinaryString(int a) {
    String s = "";
    for (int i = 0; i < 32; i++) {
      Integer lsb = new Integer(a & 1);
      s = lsb.toString() + s;
      a = a >> 1;
    }
    return s;
  }

  public static String toBaseNString(int a, int base) {
    String s = "";
    while (true) {
      int lastdigit = a % base;
      s = lastdigit + s;
      a = a / base;
      if (a == 0) break;
    }
    return s;
  }

  public static void printMatrix(int[][] matrix) {
    for (int i = 0; i < matrix.length; i++) {
      for (int j = 0; j < matrix[0].length; j++) {
        if (matrix[i][j] < 10 && matrix[i][j] > -10) {
          System.out.print(" ");
        }
        if (matrix[i][j] < 100 && matrix[i][j] > -100) {
          System.out.print(" ");
        }
        if (matrix[i][j] >= 0) {
          System.out.print(" ");
        }
        System.out.print(" " + matrix[i][j]);
      }
      System.out.println();
    }
  }

  public static void printMatrix(boolean[][] matrix) {
    for (int i = 0; i < matrix.length; i++) {
      for (int j = 0; j < matrix[0].length; j++) {

        if (matrix[i][j]) {
          System.out.print(1);
        } else {
          System.out.print(0);
        }
      }
      System.out.println();
    }
  }

  public static void printIntArray(int[] array) {
    for (int i = 0; i < array.length; i++) {
      System.out.print(array[i] + " ");
    }
    System.out.println();
  }

  public static String charArrayToString(char[] array) {
    StringBuilder buffer = new StringBuilder(array.length);
    for (char c : array) {
      if (c == 0) {
        break;
      }
      buffer.append(c);
    }
    return buffer.toString();
  }

  public static String listOfPointsToString(ArrayList<Point> list) {
    StringBuilder buffer = new StringBuilder();
    for (Point p : list) {
      buffer.append("(" + p.x + "," + p.y + ")");
    }
    return buffer.toString();
  }

  public static void main(String[] args) {
    System.out.println(toBaseNString(10, 2));
  }
}
