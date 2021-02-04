package other.practice.UniqueNumberIn250Million;

import other.GenerateData;
import other.practice.CommonUtils;

import java.io.*;
import java.util.BitSet;
import java.util.Random;

import static other.practice.CommonUtils.*;

/** 在2.5亿个整数中找出不重复的整数，内存不足以容纳这2.5亿个整数。 */
public class Solution {
  public static int DIGITS_NUMBER = 25000000; // 250000000
  public static int HASH_MEMORY_LIMIT = 1 * 1024 * 1024; // 1024 * 1024 * 1024

  public static void main(String[] args) {
    String sourcePath = "/Users/cheng/Downloads/test/plan/UniqueNumberIn250Million";
    String sampleFile = "sample.txt";
    String hashResultFile = "hashResult.txt";
    String bitMapResultFile = "bitMapResult.txt";
    String bitSetResultFile = "bitSetResult.txt";
    Prepare prepare = new Prepare(sampleFile, sourcePath, false);
    prepare.run();
    HashDivideMerge hashSolution = new HashDivideMerge(sourcePath, sampleFile, hashResultFile);
    hashSolution.handle();
    BitMapSolution bitMapSolution = new BitMapSolution(sourcePath, sampleFile, bitMapResultFile);
    bitMapSolution.handle();
    BitSetSolution bitSetSolution = new BitSetSolution(sourcePath, sampleFile, bitSetResultFile);
    bitSetSolution.handle();
  }

  public static class Prepare {
    private String FileName;
    private String FilePath;
    private boolean isCover;
    private GenerateData generator = new generator();

    public Prepare(String fileName, String filePath, Boolean isCover) {
      FileName = fileName;
      FilePath = filePath;
      this.isCover = isCover;
    }

    public void run() {
      CommonUtils.verifyPath(FilePath);
      generateFile(FilePath, FileName, generator, isCover);
    }

    private class generator implements GenerateData {
      @Override
      public void generateTestData(PrintWriter pw, String fileName) {
        Random random = new Random();
        for (int i = 0; i < DIGITS_NUMBER; i++) {
          int s = random.nextInt(Integer.MAX_VALUE);
          pw.write(s + "");
          pw.write("\n");
        }
      }
    }
  }

  public static class HashDivideMerge {
    String sourcePath;
    String sourceFile;
    String splitPath;
    String splitSuffix;
    String resultFile;
    double percent = 0.4;
    int fileCount = 0;

    public HashDivideMerge(String sourcePath, String sourceFile, String resultFile) {
      this.sourcePath = sourcePath;
      this.sourceFile = sourceFile;
      this.resultFile = resultFile;
      splitPath = sourcePath + "/split";
      splitSuffix = ".txt";
    }

    public void handle() {
      hashDivide();
      uniqueMerge();
    }

    public void hashDivide() {
      fileCount = (int) (DIGITS_NUMBER * 4 / (HASH_MEMORY_LIMIT * percent));
      cleanFolder(splitPath);
      String srcFile = sourcePath + "/" + sourceFile;
      BufferedReader reader = null;
      try {
        reader = new BufferedReader(new FileReader(srcFile));
        String tmpLine;
        while ((tmpLine = reader.readLine()) != null) {
          String splitFile = splitPath + "/" + BkdrHash(tmpLine) % fileCount + splitSuffix;
          generateFile(splitFile, tmpLine);
        }
        reader.close();
      } catch (Exception e) {
        e.printStackTrace();
      } finally {
        try {
          reader.close();
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    }

    public void uniqueMerge() {
      CommonUtils.Unique unique =
          new CommonUtils.Unique(fileCount, splitSuffix, splitPath, resultFile, sourcePath);
      unique.getUnique();
    }
  }

  public static class BitMapSolution {
    String sourcePath;
    String sourceFile;
    String resultFile;

    public BitMapSolution(String sourcePath, String sourceFile, String resultFile) {
      this.sourcePath = sourcePath;
      this.sourceFile = sourceFile;
      this.resultFile = resultFile;
    }

    public void handle() {
      BitMap2 bitMap = new BitMap2(Integer.MAX_VALUE);
      BufferedReader reader = null;
      try {
        // check
        resetFile(sourcePath, resultFile);
        checkFile(sourcePath, sourceFile);

        // set bitMap
        File file = new File(sourcePath, sourceFile);
        reader = new BufferedReader(new FileReader(file));
        String line;
        while ((line = reader.readLine()) != null) {
          bitMap.setBit(Integer.parseInt(line));
        }
        reader.close();

        File outFile = new File(sourcePath, resultFile);
        FileOutputStream fos = new FileOutputStream(outFile, true);
        OutputStreamWriter osw = new OutputStreamWriter(fos, "UTF-8");
        BufferedWriter bw = new BufferedWriter(osw);
        PrintWriter pw = new PrintWriter(bw, true);

        for (int i = 0; i < Integer.MAX_VALUE; i++) {
          if (bitMap.getBit(i).equals("01")) {
            pw.write(i + "");
            pw.write("\n");
          }
        }
        pw.close();
        bw.close();
        osw.close();
        fos.close();

      } catch (Exception e) {
        e.printStackTrace();
      } finally {
        try {
          reader.close();

        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    }
  }

  /**
   * int中32位只能表示一个数，而用BitMap可以表示32一个数: int[] bitMap: bitMap[0]:可以表示数字0~31 比如表示0：00000000 00000000
   * 00000000 00000000 比如表示1 : 00000000 00000000 00000000 00000001 bitMap[1]:可以表示数字32~63
   * bitMap[2]:可以表示数字64~95 …… 因此要将一个数插入到bitMap中要进过三个步骤：
   *
   * <p>1)找到所在bitMap中的index也就是bitMap数组下标 比如我们要在第64个位置上插入数据 index = 64 >> 5 = 2，也就是说64应该插入到bitMap[2]中
   *
   * <p>2)找到64在bitMap[2]中的偏移位置 offset = 64 & 31 = 31说明63在bitMap[1]中32位的最高位
   *
   * <p>3)将最高位设为1
   */
  public static class BitMap2 {

    private long[] bitsMap1;
    private long[] bitsMap2;
    private int length;
    private int ADDRESS_BITS_PER_WORD = 6;

    public BitMap2(int size) {
      int initialSize = wordIndex(size) + (wordOffset(size) > 0 ? 1 : 0);
      bitsMap1 = new long[initialSize];
      bitsMap2 = new long[initialSize];
      length = size;
    }

    public int wordIndex(int index) {
      return index >> ADDRESS_BITS_PER_WORD;
    }

    public int wordOffset(int index) {
      return index; // & 63
    }

    // 分配：每个数分配2bit，00表示不存在，01表示出现一次，11表示多次，10无意义
    public String getBit(int index) {
      int first = getBit1(index);
      int second = getBit2(index);
      String bitInfo = "" + first + second;
      return bitInfo;
    }

    // 扫描：如果是00变01，01变10，10保持不变
    public void setBit(int index) {
      String bitInfo = getBit(index);
      switch (bitInfo) {
        case "00":
          setBit2(index);
          break;
        case "01":
          setBit1(index);
          break;
      }
    }

    private int getBit0(int index, long[] bitsMap) {
      checkIndex(index);
      return (int) ((bitsMap[wordIndex(index)] & (1L << wordOffset(index))) >>> wordOffset(index));
    }

    public int getBit1(int index) {
      return getBit0(index, bitsMap1);
    }

    public int getBit2(int index) {
      return getBit0(index, bitsMap2);
    }

    private void setBit0(int index, long[] bitsMap) {
      checkIndex(index);
      int belowIndex = wordIndex(index);
      int offset = wordOffset(index);
      bitsMap[belowIndex] |= (1L << offset);
    }

    public void setBit1(int index) {
      setBit0(index, bitsMap1);
    }

    public void setBit2(int index) {
      setBit0(index, bitsMap2);
    }

    private void checkIndex(int index) {
      if (index < 0 || index > length) {
        throw new IllegalArgumentException("length value illegal!");
      }
    }

    private static class test {

      private static final int[] BIT_VALUE = {
        0x00000001,
        0x00000002,
        0x00000004,
        0x00000008,
        0x00000010,
        0x00000020,
        0x00000040,
        0x00000080,
        0x00000100,
        0x00000200,
        0x00000400,
        0x00000800,
        0x00001000,
        0x00002000,
        0x00004000,
        0x00008000,
        0x00010000,
        0x00020000,
        0x00040000,
        0x00080000,
        0x00100000,
        0x00200000,
        0x00400000,
        0x00800000,
        0x01000000,
        0x02000000,
        0x04000000,
        0x08000000,
        0x10000000,
        0x20000000,
        0x40000000,
        0x80000000
      };

      public static void main(String[] args) {
        int a = 0x2f; // 小写十六进制（等价于0x002f）
        System.out.println(Integer.toBinaryString(a));

        int b = 0x2F; // 大写十六进制
        System.out.println(Integer.toBinaryString(b));

        int c = 10; // 标准十进制
        System.out.println(Integer.toBinaryString(c));

        int d = 010; // 以零开头，表示八进制
        System.out.println(Integer.toBinaryString(d));

        char e = 0xff; // char为2个字节，16位
        byte f = 0xf; // byte为8位
        short g = 0xff; // short为2个字节，16位
        System.out.println(Integer.toBinaryString(e));
        System.out.println(Integer.toBinaryString(f));
        System.out.println(Integer.toBinaryString(g));

        System.out.println(64 & 31);
        System.out.println(BIT_VALUE[64 & 31] + " " + Integer.toBinaryString(BIT_VALUE[64 & 31]));

        int bitIndex = 63;
        System.out.println((1 << bitIndex) + " " + Integer.toBinaryString((1 << bitIndex)));
        System.out.println(
            BIT_VALUE[bitIndex & 31] + " " + Integer.toBinaryString(BIT_VALUE[bitIndex & 31]));
      }
    }
  }

  /** 官方BitMap */
  public static class BitSetSolution {
    String sourcePath;
    String sourceFile;
    String resultFile;

    public BitSetSolution(String sourcePath, String sourceFile, String resultFile) {
      this.sourcePath = sourcePath;
      this.sourceFile = sourceFile;
      this.resultFile = resultFile;
    }

    public void handle() {
      BitSet bitSet1 = new BitSet();
      BitSet bitSet2 = new BitSet();

      BufferedReader reader = null;
      try {
        // check
        resetFile(sourcePath, resultFile);
        checkFile(sourcePath, sourceFile);

        // set bitMap
        File file = new File(sourcePath, sourceFile);
        reader = new BufferedReader(new FileReader(file));
        String line;
        while ((line = reader.readLine()) != null) {
          if (bitSet2.get(Integer.parseInt(line))) {
            continue;
          }

          if (bitSet1.get(Integer.parseInt(line))) {
            bitSet2.set(Integer.parseInt(line));
            continue;
          }

          bitSet1.set(Integer.parseInt(line));
        }

        System.out.println(bitSet1.length() + " " + bitSet1.size() + " " + bitSet1.cardinality());
        reader.close();

        File outFile = new File(sourcePath, resultFile);
        FileOutputStream fos = new FileOutputStream(outFile, true);
        OutputStreamWriter osw = new OutputStreamWriter(fos, "UTF-8");
        BufferedWriter bw = new BufferedWriter(osw);
        PrintWriter pw = new PrintWriter(bw, true);

        for (int i = 0; i < Integer.MAX_VALUE; i++) {
          if (bitSet1.get(i) && !bitSet2.get(i)) {
            pw.write(i + "");
            pw.write("\n");
          }
        }
        pw.close();
        bw.close();
        osw.close();
        fos.close();

      } catch (Exception e) {
        e.printStackTrace();
      } finally {
        try {
          reader.close();

        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    }
  }
}
