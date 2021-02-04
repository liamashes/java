package other.practice;

import other.GenerateData;

import java.io.*;
import java.util.*;

public class CommonUtils {
  public static void verifyPath(String path) {
    File file = new File(path);
    if (!file.exists()) {
      file.mkdirs();
    }
  }

  /**
   * @param fileName
   * @param generator
   * @param isCover 是否覆盖
   */
  public static void generateFile(
      String path, String fileName, GenerateData generator, Boolean isCover) {
    boolean append = true;
    boolean autoFlush = true;
    String charset = "UTF-8";
    try {
      File file = new File(path, fileName);
      if (file.exists()) {
        if (!isCover) {
          return;
        }
        file.delete();
      }
      FileOutputStream fos = new FileOutputStream(file, append);
      OutputStreamWriter osw = new OutputStreamWriter(fos, charset);
      BufferedWriter bw = new BufferedWriter(osw);
      PrintWriter pw = new PrintWriter(bw, autoFlush);
      generator.generateTestData(pw, fileName);
      pw.close();
      bw.close();
      osw.close();
      fos.close();

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static void deleteFolder(File folder) {
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

  public static void cleanFolder(String path) {
    File outPathCheck = new File(path);
    if (outPathCheck.exists()) {
      deleteFolder(outPathCheck);
    }
    outPathCheck.mkdirs();
  }

  public static void resetFile(String path, String fileName) throws IOException {
    File file = new File(path + "/" + fileName);
    if (file.exists()) {
      file.delete();
    }
    file.createNewFile();
  }

  public static void checkFile(String path, String fileName) throws FileNotFoundException {
    File file = new File(path + "/" + fileName);
    if (!file.exists()) {
      throw new FileNotFoundException("file " + fileName + " not found in " + path);
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

  public static class Unique {
    private int SPLIT_FILE_COUNT;
    private String suffix;
    private String splitPath;
    private String outPath;
    private String resultFile;

    public Unique(
        int SPLIT_FILE_COUNT, String suffix, String splitPath, String resultFile, String outPath) {
      this.splitPath = splitPath;
      this.resultFile = resultFile;
      this.SPLIT_FILE_COUNT = SPLIT_FILE_COUNT;
      this.suffix = suffix;
      this.outPath = outPath;
    }

    public void getUnique() {
      HashSet<String> resultSet = new HashSet<>();
      HashSet<String> repeatSet = new HashSet<>();
      for (int i = 0; i <= SPLIT_FILE_COUNT; i++) {
        String splitFileName = splitPath + "/" + i + suffix;
        File file = new File(splitFileName);
        if (!file.exists()) {
          continue;
        }
        getFileUniqueLine(splitFileName, resultSet, repeatSet);
      }

      generateFile(outPath, resultFile, resultSet);
    }

    public void getFileUniqueLine(
        String fileName, HashSet<String> resultSet, HashSet<String> repeatSet) {
      File file = new File(fileName);
      if (!file.exists()) {
        return;
      }
      BufferedReader reader = null;
      try {
        reader = new BufferedReader(new FileReader(file));
        String tmpLine;
        while ((tmpLine = reader.readLine()) != null) {
          if (resultSet.contains(tmpLine)) {
            repeatSet.add(tmpLine);
            resultSet.remove(tmpLine);
          }
          if (!repeatSet.contains(tmpLine)) {
            resultSet.add(tmpLine);
          }
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

    public void generateFile(String outPath, String fileName, HashSet<String> info) {
      boolean append = true;
      boolean autoFlush = true;
      String charset = "UTF-8";
      try {
        File file = new File(outPath, fileName);
        if (!file.exists()) {
          file.createNewFile();
        } else {
          file.delete();
        }
        FileOutputStream fos = new FileOutputStream(file, append);
        OutputStreamWriter osw = new OutputStreamWriter(fos, charset);
        BufferedWriter bw = new BufferedWriter(osw);
        PrintWriter pw = new PrintWriter(bw, autoFlush);
        for (String line : info) {
          pw.write(line + "\n");
        }
        pw.close();
        bw.close();
        osw.close();
        fos.close();

      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }

  public class TopKFrequency {
    private int TOP_K;
    private int SPLIT_FILE_COUNT;
    private String suffix;
    private String outPath;
    private String resultFile;

    public TopKFrequency(
        int TOP_K, int SPLIT_FILE_COUNT, String suffix, String outPath, String resultFile) {
      this.outPath = outPath;
      this.resultFile = resultFile;
      this.SPLIT_FILE_COUNT = SPLIT_FILE_COUNT;
      this.suffix = suffix;
      this.TOP_K = TOP_K;
    }

    public void getTopK() {
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

    public void generateFile(String fileName, Queue<WordFreq> info) {
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

    public class WordFreq {
      String word;
      int freq;

      public WordFreq(String word, int freq) {
        this.freq = freq;
        this.word = word;
      }
    }
  }
}
