package CCI.Chapter1;

/**
 * <h>URLify: Write a method to replace all spaces in a string with '%20'</h>
 *
 * <p>assume the string having sufficient space at the end to hold the additional characters
 *
 * <p>give the "true" length of the string
 *
 * <p>Example
 *
 * <p>Input: "Mr John Smith ", 13
 *
 * <p>Output: "Mr%20John%20Smith"
 */
public class Solution3 {

  /**
   * start from the end and working backwards.This is useful because we have an extra buffer at the
   * end, which allows us to change characters without worrying about what we're overwriting
   *
   * @param str
   * @param trueLength
   */
  public static void replaceSpaces(char[] str, int trueLength) {
    int spaceCount = 0, index, i = 0;
    for (i = 0; i < trueLength; i++) {
      if (str[i] == ' ') {
        spaceCount++;
      }
    }

    index = trueLength + spaceCount * 2;
    if (trueLength < str.length) str[trueLength] = '\0';
    for (i = trueLength - 1; i >= 0; i--) {
      if (str[i] == ' ') {
        str[index - 1] = '0';
        str[index - 2] = '2';
        str[index - 3] = '%';
        index = index - 3;
      } else {
        str[index - 1] = str[i];
        index--;
      }
    }
  }

  public static void main(String[] args) {
    String s = "Mr John Smith    ";
    char[] cs = s.toCharArray();
    int sl = s.trim().length();

    System.out.println(cs);
    replaceSpaces(cs, sl);
    System.out.println(cs);
  }
}
