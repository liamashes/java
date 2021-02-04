package other.practice.Top100WordIn1GFile;

import java.io.*;
import java.util.*;

/**
 * 有一个1G大小的一个文件，里面每一行是一个词，词的大小不超过16字节，内存限制大小是1M。返回频数最高的100个词。 类似问题：怎么在海量数据中找出重复次数最多的一个？ 解决思想：
 * hash分解+ 分而治之+归并
 *
 * <p>顺序读文件中，对于每个词x，按照hash(x)/(1024*4)存到4096个小文件中。这样每个文件大概是250k左右。如果其中的有的文件超过了1M大小，还可以按照hash继续往下分，直到分解得到的小文件的大小都不超过1M。
 * 对每个小文件，统计每个文件中出现的词以及相应的频率（可以采用trie树/hash_map等），并取出出现频率最大的100个词（可以用含100个结点的最小堆），并把100词及相应的频率存入文件。这样又得到了4096个文件。
 * 下一步就是把这4096个文件进行归并的过程了。（类似与归并排序）
 */
public class Solution {
  public static int FILE_SIZE = 102 * 1024 * 1024; // 1024 * 1024 * 1024
  public static int WORD_LENGTH = 16;
  public static int MEMORY_LIMIT = (int) (0.1 * 1024 * 1024); // 1 * 1024 * 1024
  public static int TOP_K = 100;
  public static int SPLIT_FILE_COUNT = 4079;

  public static void main(String[] args) {
    String sourcePath = "/Users/cheng/Downloads/test/plan/Top100WordIn1GFile";
    String splitPath = sourcePath + "/split";
    String sourceFile = "Sample.txt", splitSuffix = ".txt", resultFile = "Result.txt";
    double sizePercent = 0.25;
    Solution solution = new Solution();
    solution.pre(sourcePath, splitPath, sourceFile);
    SPLIT_FILE_COUNT =
        solution.splitFile(sizePercent, sourcePath + "/" + sourceFile, splitSuffix, splitPath);
    solution.getTopK(splitSuffix, splitPath, sourcePath + "/" + resultFile);
    // merge();
  }

  public static void generateFile(String fileName, Queue<WordFreq> info) {
    boolean append = true;
    boolean autoFlush = true;
    String charset = "UTF-8";
    try {
      File file = new File(fileName);
      if (!file.exists()) {
        file.createNewFile();
      } else {
        file.delete();
      }
      FileOutputStream fos = new FileOutputStream(file, append);
      OutputStreamWriter osw = new OutputStreamWriter(fos, charset);
      BufferedWriter bw = new BufferedWriter(osw);
      PrintWriter pw = new PrintWriter(bw, autoFlush);
      for (WordFreq wordFreq : info) {
        pw.write(wordFreq.word + " " + wordFreq.freq + "\n");
      }
      pw.close();
      bw.close();
      osw.close();
      fos.close();

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void getTopK(String suffix, String outPath, String resultFile) {
    List<String> list = new ArrayList<>();
    // getAllFileName(path, list); // 文件数量过多，内存溢出

    Queue<WordFreq> minHeap = new PriorityQueue<>(TOP_K, (a, b) -> (a.freq - b.freq));
    for (int i = 0; i <= SPLIT_FILE_COUNT; i++) {
      String splitFileName = outPath + "/" + i + suffix;
      File file = new File(splitFileName);
      if (!file.exists()) {
        continue;
      }
      Map<String, Integer> wordFreq = new HashMap<>();
      getFileTopK(splitFileName, wordFreq);
      for (String key : wordFreq.keySet()) {
        int freq = wordFreq.get(key);
        if (minHeap.isEmpty() || minHeap.size() < TOP_K) {
          minHeap.add(new WordFreq(key, freq));
        } else {
          if (freq > minHeap.peek().freq) {
            minHeap.poll();
            minHeap.add(new WordFreq(key, freq));
          }
        }
      }
    }

    generateFile(resultFile, minHeap);
  }

  public void getFileTopK(String fileName, Map<String, Integer> wordFreq) {
    File file = new File(fileName);
    if (!file.exists()) {
      return;
    }
    BufferedReader reader = null;
    try {
      reader = new BufferedReader(new FileReader(file));
      String tmpLine;
      while ((tmpLine = reader.readLine()) != null) {
        wordFreq.put(tmpLine, wordFreq.getOrDefault(tmpLine, 0) + 1);
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

  // 获取所有文件
  public void getAllFileName(String path, List<String> list) {
    File file = new File(path);
    File[] files =
        file.listFiles(
            new FileFilter() {
              @Override
              public boolean accept(File pathname) {
                return true;
              }
            });
    for (int i = 0; i < files.length; i++) {
      // 判断是否是目录，是的话继续递归
      if (files[i].isDirectory()) {
        getAllFileName(files[i].getAbsolutePath(), list);
      } else {
        // 否则添加到list
        // 获取全部文件名
        list.add(files[i].getName());
        // 获取全部包+文件名
        // list.add(files[i].getAbsolutePath());
      }
    }
  }

  public int splitFile(double percent, String sourceFile, String suffix, String outPath) {
    int fileCount = (int) (FILE_SIZE / (MEMORY_LIMIT * percent));
    File outPathCheck = new File(outPath);
    if (outPathCheck.exists()) {
      deleteFolder(outPathCheck);
    }
    outPathCheck.mkdirs();

    File srcFile = new File(sourceFile);
    if (!srcFile.exists()) {
      return 0;
    }

    BufferedReader reader = null;
    try {
      reader = new BufferedReader(new FileReader(srcFile));
      String tmpLine;
      while ((tmpLine = reader.readLine()) != null) {
        String splitFile = outPath + "/" + BkdrHash(tmpLine) % fileCount + suffix;
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

    return fileCount;
  }

  public void generateFile(String fileName, String info) {
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

  public void deleteFolder(File folder) {
    if (!folder.exists()) {
      return;
    }
    File[] files = folder.listFiles();
    if (files != null) {
      for (File file : files) {
        if (file.isDirectory()) {
          // 递归直到目录下没有文件
          deleteFolder(file);
        } else {
          // 删除
          file.delete();
        }
      }
    }
    // 删除
    folder.delete();
  }

  public long BkdrHash(String str) {
    int seed = 131;
    int hash = 0;
    for (char c : str.toCharArray()) {
      hash = hash * seed + c;
    }
    return hash & 0x7FFFFFFF;
  }

  public void pre(String sourcePath, String splitPath, String sourceFile) {
    verifyPath(sourcePath);
    verifyPath(splitPath);
    generateFile(sourcePath + "/" + sourceFile);
  }

  public void verifyPath(String path) {
    File file = new File(path);
    if (!file.exists()) {
      file.mkdirs();
    }
  }

  public void generateFile(String fileName) {
    boolean append = true;
    boolean autoFlush = true;
    String charset = "UTF-8";
    try {
      File file = new File(fileName);
      if (!file.exists()) {
        file.createNewFile();
      } else {
        file.delete();
      }
      FileOutputStream fos = new FileOutputStream(file, append);
      OutputStreamWriter osw = new OutputStreamWriter(fos, charset);
      BufferedWriter bw = new BufferedWriter(osw);
      PrintWriter pw = new PrintWriter(bw, autoFlush);
      generateTestData(pw, fileName);
      pw.close();
      bw.close();
      osw.close();
      fos.close();

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void generateTestData(PrintWriter printWriter, String fileName) {
    // \n occupy 1 byte
    int N = FILE_SIZE / (WORD_LENGTH + 1);
    String alpha = "abcdefghijklmnopqrstuvwxyz";
    Random random = new Random();
    for (int i = 0; i < N; i++) {
      int size = random.nextInt(WORD_LENGTH);
      String s = "";
      for (int j = 0, l = 1; j < size; j++) {
        s += alpha.charAt(random.nextInt(26));
        l = (s.charAt(s.length() - 1) == '-' || j >= size - 1) ? 1 : 0;
      }
      printWriter.write(s);
      printWriter.write("\n");
    }
  }

  private class WordFreq {
    String word;
    int freq;

    public WordFreq(String word, int freq) {
      this.freq = freq;
      this.word = word;
    }
  }
}
