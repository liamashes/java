package CCI.Chapter1;

/**
 * <h>One way</h>
 *
 * <p>there are three types of edits that can be performed on strings
 *
 * <ul>
 *   <li>insert a character
 *   <li>remove a character
 *   <li>replace a character
 * </ul>
 *
 * <p>given two strings, write a function to check if they are one edit(or zero edits) away
 */
public class Solution5 {
  // brute force algorithm not bother to implement

  /**
   * <h>consider the meaning of each of those operations</h>
   *
   * <p><b>Replacement:</b>different only in one place
   *
   * <p><b>Insertion:</b>identical - except for a shift at some point in the string
   *
   * <p><b>Removal:</b>inverse of insertion
   *
   * <p><b>time complexity:</b>O(n), n is the length of the shorter string
   *
   * @param first
   * @param second
   * @return
   */
  public static boolean oneEditAway(String first, String second) {
    if (first.length() == second.length()) {
      return oneEditReplace(first, second);
    }

    if (first.length() + 1 == second.length()) {
      return oneEditInsert(first, second);
    }

    if (first.length() == second.length() + 1) {
      return oneEditInsert(second, first);
    }

    return false;
  }

  public static boolean oneEditReplace(String s1, String s2) {
    boolean foundDifference = false;
    for (int i = 0; i < s1.length(); i++) {
      if (s1.charAt(i) != s2.charAt(i)) {
        if (foundDifference) {
          return false;
        }
        foundDifference = true;
      }
    }
    return true;
  }

  /**
   * @param s1 input string 1
   * @param s2 input string 2
   * @return where these 2 string are one insert away
   */
  public static boolean oneEditInsert(String s1, String s2) {
    int index1 = 0;
    int index2 = 0;

    while (index2 < s2.length() && index1 < s1.length()) {
      if (s1.charAt(index1) != s2.charAt(index2)) {
        if (index1 != index2) {
          return false;
        }
        index2++;
      } else {
        index1++;
        index2++;
      }
    }
    return true;
  }

  /**
   * merging 2 methods into 1
   *
   * @param first
   * @param second
   * @return
   */
  public static boolean oneEditAway2(String first, String second) {
    // length checks
    if (Math.abs(first.length() - second.length()) > 1) {
      return false;
    }

    String s1 = first.length() < second.length() ? first : second;
    String s2 = first.length() < second.length() ? second : first;

    int index1 = 0;
    int index2 = 0;
    boolean foundDifference = false;
    boolean isSameLength = first.length() == second.length();
    while (index1 < s1.length() && index2 < s2.length()) {
      if (s1.charAt(index1) != s2.charAt(index2)) {
        if (foundDifference) return false;
        foundDifference = true;

        if (isSameLength) {
          index1++;
        }

      } else {
        index1++;
      }
      index2++;
    }

    return true;
  }
}
