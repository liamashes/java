package other.practice.SameUrlOutOfTwoFiveBillionFile;

import java.io.*;
import java.util.*;

/**
 * 给定a、b两个文件，各存放50亿个url，每个url各占64字节，内存限制是4G，让你找出a、b文件共同的url？
 *
 * <p>每个文件50亿个URL，每个URL最长64个字节，可以估计每个文件安的大小为5000,000,000 ×64bit=320,000,000,000bit ≈
 * 300,000G，远远大于内存限制的4G，同时需要大硬盘（这里不考虑分布式计算）。所以不可能将其完全加载到内存中处理。考虑采取分而治之的方法。
 */
public class Solution {

  public static int PER_FILE_SIZE = 2024;
  public static int FILE_COUNT = 1000;
  public static int SOURCE_FILE_COUNT = 500000;
  public static String TEST_PATH = "/Users/cheng/Downloads/test/plan/SameUrl50/";
  public static String Sample1 = "file1.txt", Sample2 = "file2.txt", ResultFile = "sameUrl.txt";

  /**
   * 遗留问题1：getFileSize(fileName) > PER_FILE_SIZE 的处理方式
   * 遗留问题3：如果FILE_COUNT过大，会占用过多额外的内存导致内存溢出，因此FILE_COUNT需要根据内存限制（预留空间给两个文件列表）、文件大小来调整
   *
   * @param folder
   * @param sameUrlFileName
   * @param aSuffix
   * @param bSuffix
   * @return
   */
  public static boolean writeSameUrlToFile(
      String folder, String sameUrlFileName, String aSuffix, String bSuffix) {
    List<String> fileListA = getFolderFileNameList(folder, aSuffix);
    List<String> fileListB = getFolderFileNameList(folder, bSuffix);
    for (String fileName : fileListA) {
      String bFileName = fileName.replace(".a.", ".b.");
      if (getFileSize(fileName) <= PER_FILE_SIZE
          && fileListB.contains(bFileName)
          && getFileSize(bFileName) <= PER_FILE_SIZE) {
        System.out.println(fileName + "\t" + bFileName);
        Set<String> aSet = getFileHashSet(folder, fileName);
        Set<String> bSet = getFileHashSet(folder, bFileName);
        Set<String> sameUrlSet = getSameUrlSet(aSet, bSet);
        generateFile(sameUrlFileName, sameUrlSet);
      }
    }
    return true;
  }

  public static Set<String> getSameUrlSet(Set<String> a, Set<String> b) {
    Set<String> c = new HashSet<>();
    c.addAll(a);
    c.retainAll(b);
    return c;
  }

  public static Set<String> getFileHashSet(String folder, String fileName) {
    Set<String> url = new HashSet<>();
    File file = new File(folder + "/" + fileName);
    if (!file.exists() || file.length() == 0) {
      return url;
    }
    BufferedReader reader;
    try {
      reader = new BufferedReader(new FileReader(file));
      String tempLine;
      while ((tempLine = reader.readLine()) != null) {
        url.add(tempLine);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return url;
  }

  public static void main(String[] args) {
    String path = TEST_PATH;
    String f1 = path + Sample1;
    String f2 = path + Sample2;
    // pre(f1, f2);
    String storePath = path + "splitFile";
    String OutFileName = storePath + ResultFile;
    handle(f1, f2, storePath, OutFileName);
  }

  /**
   * 拆分文件
   *
   * <p>小文件求交操作
   */
  public static void handle(String f1, String f2, String storePath, String sameUrlFile) {
    String f1Suffix = ".a.txt", f2Suffix = ".b.txt";

    splitBigFile(f1, f1Suffix, storePath, FILE_COUNT);
    splitBigFile(f2, f2Suffix, storePath, FILE_COUNT);

    writeSameUrlToFile(storePath, sameUrlFile, f1Suffix, f2Suffix);
  }

  /**
   * 遗留问题2：每次遍历都需要打开一次文件，如何优化
   *
   * @param fileName
   * @param suffix
   * @param storePath
   * @param countToSplit
   */
  public static void splitBigFile(
      String fileName, String suffix, String storePath, long countToSplit) {
    File file = new File(fileName);
    if (!file.exists()) {
      System.out.println("file " + fileName + " not exists, mission aborted");
      return;
    }

    File storeDir = new File(storePath);
    if (storeDir.exists()) {
      storeDir.delete();
    }

    storeDir.mkdirs();

    BufferedReader reader = null;
    try {
      reader = new BufferedReader(new FileReader(file));
      String tempLine = null;
      while ((tempLine = reader.readLine()) != null) {
        String splitFileName = storePath + "/";
        splitFileName += BkdrHash(tempLine) % countToSplit;
        splitFileName += suffix;
        generateFile(splitFileName, tempLine);
      }
      reader.close();

    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      if (reader != null) {
        try {
          reader.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
  }

  public static long BkdrHash(String str) {
    int seed = 131;
    int hash = 0;
    for (char c : str.toCharArray()) {
      hash = hash * seed + c;
    }
    return hash & 0x7FFFFFFF;
  }

  public static long getFileSize(String fileName) {
    File file = new File(fileName);
    if (file.exists()) {
      return file.length();
    }
    return 0;
  }

  // pre
  public static void pre(String f1, String f2) {

    generateFile(f1, SOURCE_FILE_COUNT);
    generateFile(f2, SOURCE_FILE_COUNT);
  }

  /**
   * which gives you:
   *
   * <p>Decide whether to append to the text file or overwrite it.
   *
   * <p>Decide whether to make it
   *
   * <p>auto-flush or not.
   *
   * <p>Specify the charset.
   *
   * <p>Make it buffered, which improves the streaming performance.
   *
   * <p>Convenient methods (such as println() and its overloaded ones).
   *
   * @param fileName
   */
  public static void generateFile(String fileName, int lines) {
    boolean append = true;
    boolean autoFlush = true;
    String charset = "UTF-8";
    try {
      File file = new File(fileName);
      if (!file.exists()) file.createNewFile();
      FileOutputStream fos = new FileOutputStream(file, append);
      OutputStreamWriter osw = new OutputStreamWriter(fos, charset);
      BufferedWriter bw = new BufferedWriter(osw);
      PrintWriter pw = new PrintWriter(bw, autoFlush);
      generateUrl(pw, lines);
      pw.close();
      bw.close();
      osw.close();
      fos.close();

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static void generateFile(String fileName, String info) {
    boolean append = true;
    boolean autoFlush = true;
    String charset = "UTF-8";
    try {
      File file = new File(fileName);
      if (!file.exists()) file.createNewFile();
      FileOutputStream fos = new FileOutputStream(file, append);
      OutputStreamWriter osw = new OutputStreamWriter(fos, charset);
      BufferedWriter bw = new BufferedWriter(osw);
      PrintWriter pw = new PrintWriter(bw, autoFlush);
      pw.write(info);
      pw.write("\n");
      pw.close();
      bw.close();
      osw.close();
      fos.close();

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static void generateFile(String fileName, Set<String> info) {
    boolean append = true;
    boolean autoFlush = true;
    String charset = "UTF-8";
    try {
      File file = new File(fileName);
      if (!file.exists()) file.createNewFile();
      FileOutputStream fos = new FileOutputStream(file, append);
      OutputStreamWriter osw = new OutputStreamWriter(fos, charset);
      BufferedWriter bw = new BufferedWriter(osw);
      PrintWriter pw = new PrintWriter(bw, autoFlush);
      for (String str : info) {
        pw.write(str);
        pw.write("\n");
      }
      pw.close();
      bw.close();
      osw.close();
      fos.close();

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static void generateUrl(PrintWriter printWriter, int lines) {
    int N = lines; // 500000000;
    String url = "-0123456789abcdefghijklmnopqrstuvwxyz";
    Random random = new Random();
    for (int i = 0; i < N; i++) {
      int size = random.nextInt(64);
      String s = "http://www.";
      for (int j = 0, l = 1; j < size; j++) {
        s += url.charAt(random.nextInt(36));
        l = (s.charAt(s.length() - 1) == '-' || j >= size - 1) ? 1 : 0;
      }

      s += ".com/";
      printWriter.write(s);
      printWriter.write("\n");
    }
  }

  public static List<String> getFolderFileNameList(String folder, String suffix) {
    List<String> list = new ArrayList<>();
    File file = new File(folder);
    if (!file.isDirectory()) {
      return list;
    }

    FilenameFilter suffixFilter =
        new FilenameFilter() {
          @Override
          public boolean accept(File dir, String name) {
            if (name.endsWith(suffix)) {
              return true;
            }
            return false;
          }
        };

    for (File tempFile : file.listFiles(suffixFilter)) {
      list.add(tempFile.getName());
    }
    return list;
  }
}
