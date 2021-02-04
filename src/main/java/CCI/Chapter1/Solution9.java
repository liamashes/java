package CCI.Chapter1;

/**
 * <h><b>String Rotation: </b>Assume you have a method isSubstring which checks if one is a
 * substring of another. Given two strings s1 and s2, write code to check if s2 is a rotation of s1
 * using only one call to isSubString</h>
 */
public class Solution9 {

  /**
   * <blockqueue>find the rotation point, then
   *
   * <p>s1 = xy = waterbottle
   *
   * <p>x = wat
   *
   * <p>y = erbottle
   *
   * <p>s2 = yx = erbottlewat
   *
   * <p>so, yx is always a substring of xyxy, that is s2 is always a substring of s1s1
   *
   * <p></blockqueue>
   *
   * @param s1
   * @param s2
   * @return
   */
  public static boolean isRotation(String s1, String s2) {
    int len = s1.length();

    // check length and is empty
    if (len == s2.length() && len > 0) {
      String s1s1 = s1 + s1;
      return isSubstring(s1s1, s2);
    }
    return false;
  }

  public static boolean isSubstring(String s1, String s2) {
    return true;
  }
}
