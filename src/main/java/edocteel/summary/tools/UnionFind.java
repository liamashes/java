package edocteel.summary.tools;

public class UnionFind {
  private int[] father;
  private int[] size;

  public UnionFind(int n) {
    father = new int[n];
    size = new int[n + 1];
    for (int i = 0; i <= n; i++) {
      father[i] = i;
      size[i] = 1;
    }
  }

  public int find(int x) {
    int j = x;

    // find root
    while (father[j] != j) {
      j = father[j];
    }

    // path compression
    while (x != j) {
      x = father[x];
      father[x] = j;
    }

    return j;
  }

  public void union(int a, int b) {
    int root_a = find(a);
    int root_b = find(b);

    // same size, append right to left
    if (root_a != root_b) {
      if (size[root_a] < size[root_b]) {
        father[root_a] = father[root_b];
        size[root_b] += size[root_a];
      } else {
        father[root_b] = father[root_a];
        size[root_a] += size[root_b];
      }
    }
  }

  public int getSize(int index) {
    return this.size[index];
  }
}
