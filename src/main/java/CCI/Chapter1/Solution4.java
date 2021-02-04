package CCI.Chapter1;

/**
 * <h>Given a String, write a function to check if it is a permutation of a palindrome.</h>
 *
 * <p>A palindrome is a word or phrase that is the same forwards and backwards.
 *
 * <p>A permutation is a rearrangement of letters.
 *
 * <p>The palindrome does not need to be limited to just dictionary words
 *
 * <p>EXAMPLE
 *
 * <p>Input: Tact Coa
 *
 * <p>Output: True(permutations:"taco cat", "atco cta",etc.)
 */
public class Solution4 {

  /**
   * <h>after removing all non-letter characters</h>
   *
   * <p>String with even length: all even counts of characters
   *
   * <p>String with odd length: exact one character with an odd count
   *
   * @param phrase input string
   * @return whether phrase is palindromes
   */
  public static boolean isPermutationOfPalindrome(String phrase) {
    int[] table = buildCharFrequencyTable(phrase);
    return checkMaxOneOdd(table);
  }

  /**
   * count how many times each character appears
   *
   * @param phrase phrase to be parse
   * @return int array: counts of character appears
   */
  public static int[] buildCharFrequencyTable(String phrase) {
    int[] table = new int[Character.getNumericValue('z') - Character.getNumericValue('a') + 1];

    for (char c : phrase.toCharArray()) {
      int x = getCharNumber(c);
      if (x != -1) {
        table[x]++;
      }
    }

    return table;
  }

  /**
   * map each character to a number. a -> 0, b -> 1, c ->2, etc
   *
   * @param c
   * @return number of the character, -1 if not in a - z
   */
  public static int getCharNumber(char c) {
    int a = Character.getNumericValue('a');
    int b = Character.getNumericValue('z');
    int val = Character.getNumericValue(c);
    if (a <= val && val <= b) {
      return val - a;
    }
    return -1;
  }

  public static boolean checkMaxOneOdd(int[] table) {
    boolean found = false;
    for (int x : table) {
      if (x % 2 == 1) {
        if (found) {
          return false;
        }
        found = true;
      }
    }
    return true;
  }

  /**
   * judge through each character
   *
   * @param phrase input string
   * @return whether phrase is palindromes
   */
  public static boolean isPermutationOfPalindrome2(String phrase) {
    int countOdd = 0;
    int[] table = new int[Character.getNumericValue('z') - Character.getNumericValue('a') + 1];

    for (char c : phrase.toCharArray()) {
      int x = getCharNumber(c);
      if (x != -1) {
        table[x]++;
        if (table[x] % 2 == 1) {
          countOdd++;
        } else {
          countOdd--;
        }
      }
    }

    return countOdd <= 1;
  }

  /**
   * <h>we just need to know if the count is even or odd</h>
   *
   * @param phrase
   * @return
   */
  public static boolean isPermutationOfPalindrome3(String phrase) {
    int bitVector = createBitVector(phrase);
    return checkExactlyOneBitSet(bitVector);
  }

  public static int createBitVector(String phrase) {
    int bitVector = 0;
    for (char c : phrase.toCharArray()) {
      int x = getCharNumber(c);
      bitVector = toggle(bitVector, x);
    }
    return bitVector;
  }

  /**
   * toggle the ith bit in the integer
   *
   * @param vector bit vector record bit status
   * @param index the index of the bit to be toggled
   * @return the vector after process
   */
  public static int toggle(int vector, int index) {
    if (index < 0) return vector;

    int mask = 1 << index;
    if ((vector & mask) == 0) {
      vector |= mask;
    } else {
      vector &= ~mask;
    }

    return vector;
  }

  /**
   * check whether more than one bit is set to 1
   *
   * <p>00010000 & 00001111 = 0
   *
   * <p>00010100 & 00010011 = 00010000
   *
   * @param vector vector to be checked
   * @return result
   */
  public static boolean checkExactlyOneBitSet(int vector) {
    return (vector & (vector - 1)) == 0;
  }
}
