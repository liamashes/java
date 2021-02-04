package CCI.Chapter1;

/**
 * <b>Rotate Matrix:</b> Given an image represented by an NxN matrix, where each pixel in the image
 * is 4 bytes, write a method to rotate the image by 90 degrees.Can you do this in place?
 */
public class Solution7 {

  /**
   * top -> right -> bottom -> left, start from the outermost layer
   *
   * <p><b>time complexity:</b> O(n²)
   *
   * @param matrix
   * @return
   */
  public static int[][] rotate(int[][] matrix) {

    if (matrix.length == 0 || matrix.length != matrix[0].length) return null;

    int n = matrix.length;
    for (int layer = 0; layer < n / 2; layer++) {
      int first = layer;
      int last = n - 1 - layer;
      for (int i = first; i < last; i++) {
        int offset = i - first;

        // save top
        int top = matrix[first][i];

        // left -> top
        matrix[first][i] = matrix[last - offset][first];

        // bottom -> left
        matrix[last - offset][first] = matrix[last][last - offset];

        // right -> bottom
        matrix[last][last - offset] = matrix[i][last];

        // top -> right
        matrix[i][last] = top; // right<- saved top
      }
    }

    return matrix;
  }

  public static void main(String[] args) {
    matrixTest();
  }

  private static void matrixTest() {
    int[][] matrix = {{1, 2, 3, 4}, {5, 6, 7, 8}, {9, 10, 11, 12}, {13, 14, 15, 16}};

    printMatrix(matrix);
    int[][] result = rotate(matrix);
    printMatrix(result);
  }

  public static void printMatrix(int[][] matrix) {
    System.out.println();
    for (int i = 0; i < matrix.length; i++) {
      for (int j = 0; j < matrix[i].length; j++) {
        System.out.print(matrix[i][j] + " ");
      }
      System.out.println();
    }
    System.out.println();
  }
}
