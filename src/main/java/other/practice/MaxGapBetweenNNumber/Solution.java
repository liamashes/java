package other.practice.MaxGapBetweenNNumber;

/**
 * 给定n个实数X1,X2,....Xn，求着n个实数在实轴上向量2个数之间的最大差值，要求线性的时间算法。
 *
 * <p>方案1：最先想到的方法就是先对这n个数据进行排序，然后一遍扫描即可确定相邻的最大间隙。但该方法不能满足线性时间的要求。故采取如下方法：
 *
 * <p>s> 找到n个数据中最大和最小数据max和min。
 *
 * <p>s> 用n-2个点等分区间[min,max]，即将[min, max]等分为n-1个区间（前闭后开区间），将这些区间看作桶，编号为1,2,...,n-2,n-1，且桶 i
 * 的上界和桶i+1的下届相同，即每个桶的大小相同。每个桶的大小为：dblAvrGap=(max-min)/(n-1)。实际上，这些桶的边界构成了一
 * 个等差数列（首项为min，公差为d=dblAvrgap），且认为将min放入第一个桶，将max放入第n-1个桶。
 *
 * <p>s> 将n个数放入n-1个桶中：将每个元素x[i]分配到某个桶（编号为index），其中 index= (x[i]-min)/dblAvrGap 的下限 + 1
 * ，并求出分到每个桶的最大最小数据。
 *
 * <p>s> 最大间隙：除最大最小数据max和min以外的n-2个数据放入n-1个桶中，由抽屉原理可知至少有一个桶是空的，又因为每个桶的大小相同，所以最大间隙
 * 不会在同一桶中出现，一定是某个桶的上界和某个桶的下界之间隙，且该两桶之间的桶一定是空桶。也就是说，最大间隙在桶i的上界和桶j的下界之间产生 j>=(i+1)。一遍扫描即可完成。
 */
public class Solution {
  public static void main(String[] args) {
    double arr[] = {-31, -41, 4, -3, 4, -1, -97, -93, -23, -84};
    System.out.println("Max gap: " + MaxGap(arr, arr.length));
  }

  public static double MaxGap(double[] arr, int len) {
    if (len < 0) return 0;
    double max = arr[0], min = arr[0];
    // find max, min
    for (double arrV : arr) {
      if (arrV > max) max = arrV;
      if (arrV < min) min = arrV;
    }

    // initial buckets
    Bucket[] buckets = new Bucket[len];
    for (int i = 0; i < len; i++) {
      buckets[i] = new Bucket();
      buckets[i].max = min + i * (max - min) / (len - 1);
      buckets[i].min = min + (i + 1) * (max - min) / (len - 1);
      buckets[i].flag = 0;
    }

    // split number into buckets
    double gap = (max - min) / (len - 1);
    for (int i = 0; i < len; i++) {
      int index = (int) ((arr[i] - min) / gap);
      if (index > len) index = len - 1;
      if (buckets[index].flag == 0) {
        buckets[index].max = arr[i];
        buckets[index].min = arr[i];
      }
      if (buckets[index].max < arr[i]) buckets[index].max = arr[i];
      if (buckets[index].min > arr[i]) buckets[index].min = arr[i];
      buckets[index].flag = 1;
    }

    // find max gap
    double maxGap = 0;
    double low = buckets[0].max;
    for (int i = 0; i < len; i++) {
      while (buckets[i].flag == 0) i++;
      if (i < len) {
        double t = buckets[i].min - low;
        maxGap = Math.max(t, maxGap);
        low = buckets[i].max;
      }
    }
    return maxGap;
  }

  private static class Bucket {
    public double min;
    public double max;
    public int flag;
  }
}
