package CCI.Chapter1;

/**
 * <h>Implement an algorithm to determine if a string has all unique characters. </h>
 *
 * <p>What if you cannot use additional data structures.
 */
public class Solution1 {

  // create an array to store status
  public static boolean isUniqueChars(String word) {
    // assume using ASCII
    if (word.length() > 128) return false;

    // create an 128 alphabet
    boolean[] charSet = new boolean[128];
    for (int i = 0; i < word.length(); i++) {
      int val = word.charAt(i);
      if (charSet[val]) {
        return false;
      }
      charSet[val] = true;
    }
    return true;
  }

  // use a bit vector to reduce our space usage by a factor of 8
  public static boolean isUniqueChars2(String word) {
    int checker = 0;
    for (int i = 0; i < word.length(); i++) {
      int val = word.charAt(i) - 'a';
      if ((checker & (1 << val)) > 0) {
        return false;
      }
      checker |= (1 << val);
    }
    return true;
  }

  private static void test(String word) {
    int checker = 0;
    for (int i = 0; i < word.length(); i++) {
      int val = word.charAt(i) - 'a';
      boolean result = ((checker & (1 << val)) > 0);
      checker |= (1 << val);
      System.out.println(
          word.charAt(i)
              + " "
              + (int) word.charAt(i)
              + " "
              + val
              + " "
              + (1 << val)
              + " "
              + checker
              + " "
              + result);
    }
  }

  public static void charSet() {
    for (Integer i = 0; i < 128; i++) {
      System.out.println(i + " " + Integer.toBinaryString(i) + " " + ((char) (i + 0)));
    }
  }

  private static void mainProcess() {
    String[] words = {"abcdea", "hello", "apple", "kite", "padle"};
    for (String word : words) {
      System.out.println(word + ": " + isUniqueChars(word) + " " + isUniqueChars2(word));
      test(word);
    }
  }

  private static void subStringProcess() {
    String test = "sdasdasd";
    System.out.println(test.substring(1, test.length()));
  }

  public static void main(String[] args) {
    // mainProcess();
    subStringProcess();
  }
}
