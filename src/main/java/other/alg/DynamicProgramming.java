package other.alg;

public class DynamicProgramming {

  public static int ClimbStairs(int n) {
    if (n < 1) return -1;
    if (n == 1) return 1;

    int[] iteration = new int[n + 1];
    iteration[1] = 1;
    iteration[2] = 2;

    int i = 3;

    while (i < n + 1) {
      iteration[i] = iteration[i - 1] + iteration[i - 2];
      i++;
    }

    return iteration[n];
  }

  public static int MaxRob(int[] houses) {
    if (houses.length == 0) return 0;
    if (houses.length == 1) return houses[0];

    int[] dp = new int[houses.length];
    dp[0] = houses[0];
    dp[1] = Math.max(houses[0], houses[1]);

    for (int i = 2; i < houses.length; i++) {
      dp[i] = Math.max(dp[i - 2] + houses[i], dp[i - 1]);
    }

    return dp[houses.length - 1];
  }

  public static void main(String[] args) {
    for (int i = 1; i < 5; i++) {
      System.out.println(ClimbStairs(i));
    }
  }
}
