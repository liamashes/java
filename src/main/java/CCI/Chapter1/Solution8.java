package CCI.Chapter1;

import static CCI.Chapter1.Solution7.printMatrix;

/**
 * <h><b>Zero Matrix: </b>Write an algorithm such that if an element in an MxN matrix is 0, its
 * entire row and column are set to 0</h>
 */
public class Solution8 {

  public static void setZeros(int[][] matrix) {
    boolean[] row = new boolean[matrix.length];
    boolean[] column = new boolean[matrix[0].length];

    // set flag
    for (int i = 0; i < row.length; i++) {
      for (int j = 0; j < column.length; j++) {
        if (matrix[i][j] == 0) {
          row[i] = true;
          column[j] = true;
        }
      }
    }

    // nullify rows
    for (int i = 0; i < row.length; i++) {
      if (row[i]) nullifyRow(matrix, i);
    }

    // nullify columns
    for (int i = 0; i < column.length; i++) {
      if (column[i]) nullifyColumn(matrix, i);
    }
  }

  private static void nullifyRow(int[][] matrix, int row) {
    for (int i = 0; i < matrix[0].length; i++) {
      matrix[row][i] = 0;
    }
  }

  private static void nullifyColumn(int[][] matrix, int column) {
    for (int i = 0; i < matrix.length; i++) {
      matrix[i][column] = 0;
    }
  }

  public static void main(String[] args) {
    int[][] matrix = {{1, 2, 3, 4}, {5, 0, 7, 8}, {9, 10, 11, 12}, {13, 14, 15, 16}};

    setZeros(matrix);

    printMatrix(matrix);
  }
}
