package CCI.introduction;

public class CompareBinaryToHex {

  public static int charToDigit(char c) {
    if ('0' <= c && c <= '9') {
      return c - '0';
    } else if (c >= 'A' && c <= 'F') {
      return 10 + c - 'A';
    } else if ('a' <= c && c <= 'f') {
      return c - 'a' + 10;
    }
    return -1;
  }

  public static int convertToBase(String number, int base) {
    // support 2-10 16
    if (base < 2 || (base > 10 && base != 16)) return -1;

    int value = 0;
    for (int i = number.length() - 1; i >= 0; i--) {
      int digit = charToDigit(number.charAt(i));
      if (digit < 0 || digit >= base) {
        return -1;
      }
      int exp = number.length() - 1 - i;
      value += digit * Math.pow(base, exp);
    }
    return value;
  }

  public static boolean compareBinToHex(String binary, String hex) {
    int n1 = convertToBase(binary, 2);
    int n2 = convertToBase(hex, 16);
    if (n1 < 0 || n2 < 0) {
      return false;
    } else {
      return n1 == n2;
    }
  }

  public static void main(String[] args) {
    // digitToValue test
    System.out.println(compareBinToHex("111001011", "1CB"));
  }
}
