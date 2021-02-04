package other.practice.MergeCollectionsWithIntersection;

import java.util.HashMap;
import java.util.HashSet;

/**
 * 将多个集合合并成没有交集的集合：给定一个字符串的集合，格式如：{aaa,bbb,ccc},{bbb,ddd},{eee,fff},{ggg},
 * {ddd,hhh}。要求将其中交集不为空的集合合并，要求合并完成的集合之间无交集，例如上例应输出{aaa,bbb,ccc,ddd,hhh}, (eee,fff},{ggg}。
 *
 * <p>(1) 请描述你解决这个问题的思路；
 *
 * <p>(2) 给出主要的处理流程，算法，以及算法的复杂度；
 *
 * <p>(3) 请描述可能的改进。
 *
 * <p>方案1：采用并查集。首先所有的字符串都在单独的并查集中。然后依扫描每个集合，顺序合并将两个相邻元素合并。例如，对于{aaa,bbb,ccc}，首
 * 先查看aaa和bbb是否在同一个并查集中，如果不在，那么把它们所在的并查集合并，然后再看bbb和ccc是否在同一个并查集中，如果不在，那么也把它
 * 们所在的并查集合并。接下来再扫描其他的集合，当所有的集合都扫描完了，并查集代表的集合便是所求。复杂度应该是O(NlgN)的。改进的话，首先可以记
 * 录每个节点的根结点，改进查询。合并的时候，可以把大的和小的进行合，这样也减少复杂度。
 */
public class Solution {

  public static void main(String[] args) {
    String str1[] = {"aaa", "bbb", "ccc"};
    String str2[] = {"bbb", "ddd"};
    String str3[] = {"eee", "fff"};
    String str4[] = {"ggg"};
    String str5[] = {"ddd", "hhh"};
    HashSet<String[]> hashSet = new HashSet<>(5);
    hashSet.add(str1);
    hashSet.add(str2);
    hashSet.add(str3);
    hashSet.add(str4);
    hashSet.add(str5);
    Union union = new Union();
    union.UnionSet(hashSet);
  }

  public static class Union {
    private final int MAX = 100;
    private int[] father = new int[MAX];
    // initial set
    private void MakeSet() {
      for (int i = 0; i < MAX; i++) {
        father[i] = i;
      }
    }

    private int FindFather(int x) {
      if (x != father[x]) father[x] = FindFather(father[x]);
      return father[x];
    }

    private void Union(int x, int y) {
      x = FindFather(x);
      y = FindFather(y);
      father[x] = y;
    }

    private void UnionSet(HashSet<String[]> set) {
      // convert to hashMap
      int k = 0;
      HashMap<String, Integer> hashMap = new HashMap<>();
      for (String[] setV : set) {
        for (String setVV : setV) {
          if (!hashMap.containsKey(setVV)) {
            hashMap.put(setVV, k++);
          }
        }
      }

      // initial, then merge i and i+1
      MakeSet();
      for (String[] setV : set) {
        for (int i = 1; i < setV.length; i++) {
          Union(hashMap.get(setV[i]), hashMap.get(setV[i - 1]));
        }
      }

      // compute kinds
      int len = hashMap.size();
      HashSet<Integer> kind = new HashSet<>();
      for (int i = 0; i < len; i++) {
        if (i == father[i]) {
          kind.add(i);
        }
      }

      // output each kind
      for (int kindV : kind) {
        System.out.println(kindV + ":");
        for (int j = 0; j < hashMap.size() - 1; j++) {
          if (father[hashMap.get(j + 1)] == kindV) {
            System.out.println(hashMap.get(j));
          }
        }
      }
    }
  }
}
