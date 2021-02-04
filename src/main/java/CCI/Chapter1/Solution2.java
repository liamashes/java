package CCI.Chapter1;

/**
 * <h>Given two strings, write a method to decide if one is a permutation of the other </h>
 *
 * <p>whether comparison is case sensitive and whitespace is significant
 *
 * <p>optimization
 */
public class Solution2 {

  public String sort(String s) {
    char[] content = s.toCharArray();
    java.util.Arrays.sort(content);
    return new String(content);
  }

  /**
   * sort the strings
   *
   * @param s string 1
   * @param t string 2
   * @return if string 1 is a permutation of string 2
   */
  public boolean permutation(String s, String t) {
    if (s.length() != t.length()) {
      return false;
    }
    return sort(s).equals(sort(t));
  }

  /**
   * check if the two strings have identical character counts
   *
   * @param s string 1
   * @param t string 2
   * @return if string 1 is a permutation of string 2
   */
  public boolean permutation2(String s, String t) {
    if (s.length() != t.length()) {
      return false;
    }

    // create an array to store character counts
    int[] charCnt = new int[128];

    char[] s_array = s.toCharArray();
    for (char c : s_array) {
      charCnt[c]++;
    }

    for (int i = 0; i < t.length(); i++) {
      int c = (int) t.charAt(i);
      charCnt[c]--;
      if (charCnt[c] < 0) {
        return false;
      }
    }
    return true;
  }
}
