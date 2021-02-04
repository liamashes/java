package CCI.Chapter1;

/**
 *
 *
 * <h2><em>String Compression:</em>Implement a method to perform basic string compression using the
 * counts of repeated characters</h2>
 *
 * <blockquote>
 *
 * For example, the string aabcccccaaa would become a2b1c5a3. if the compressed string would not
 * become smaller than the original string, your method should return the original string. you can
 * assume the string has only uppercase and lowercase letters(a-z)
 *
 * </blockquote>
 */
public class Solution6 {

  /**
   * iterate through the string, copying characters to a new string and counting the repeats
   *
   * <p><b>time complexity:</b> O(p + k²), p is the size of the original string and k is the number
   * of character sequences. It's slow because string concatenation operates in O(n²) time
   *
   * <p><b>why O(n²):</b>On each concatenation, a new copy of the string is created, and the two
   * strings are copied over, character by character. The first iteration requires us to copy x
   * characters. The second iteration requires copying 2x characters. The third iteration requires
   * 3x, and so on. The total time therefore is O( x + 2x + . . . + nx). This reduces toO(xn²).
   *
   * @param str
   * @return compressed string
   */
  public static String compress(String str) {
    String compressedString = "";
    int countConsecutive = 0;

    for (int i = 0; i < str.length(); i++) {
      countConsecutive++;

      // if next character is different from current, append this char to result.
      if (i + 1 >= str.length() || str.charAt(i) != str.charAt(i + 1)) {
        //
        compressedString += "" + str.charAt(i) + Integer.toString(countConsecutive);
        countConsecutive = 0;
      }
    }
    return compressedString.length() < str.length() ? compressedString : str;
  }

  public static String compress2(String str) {
    StringBuilder compressedString = new StringBuilder();
    int countConsecutive = 0;

    for (int i = 0; i < str.length(); i++) {
      countConsecutive++;

      // if next character is different from current, append this char to result.
      if (i + 1 >= str.length() || str.charAt(i) != str.charAt(i + 1)) {
        //
        compressedString.append(str.charAt(i));
        compressedString.append(countConsecutive);
        countConsecutive = 0;
      }
    }
    return compressedString.length() < str.length() ? compressedString.toString() : str;
  }

  public static String compress2(String str, int size) {
    StringBuilder compressedString = new StringBuilder(size);
    int countConsecutive = 0;

    for (int i = 0; i < str.length(); i++) {
      countConsecutive++;

      // if next character is different from current, append this char to result.
      if (i + 1 >= str.length() || str.charAt(i) != str.charAt(i + 1)) {
        //
        compressedString.append(str.charAt(i));
        compressedString.append(countConsecutive);
        countConsecutive = 0;
      }
    }
    return compressedString.length() < str.length() ? compressedString.toString() : str;
  }

  /**
   *
   *
   * <h2>pre-check whether we should create s string or not</h2>
   *
   * <p><b>downside:</b>cause a second loop through the characters and also add nearly duplicated
   * code
   *
   * @param str
   * @return
   */
  public static String compress3(String str) {
    int finalLength = countCompression(str);
    if (finalLength >= str.length()) return str;

    return compress2(str, finalLength);
  }

  public static int countCompression(String str) {
    int compressedLength = 0;
    int countConsecutive = 0;
    for (int i = 0; i < str.length(); i++) {
      countConsecutive++;

      if (i + 1 >= str.length() || str.charAt(i) != str.charAt(i + 1)) {
        compressedLength += 1 + String.valueOf(countConsecutive).length();
        countConsecutive = 0;
      }
    }
    return compressedLength;
  }
}
