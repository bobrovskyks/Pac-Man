package application;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;

public class JavaSort {
  public static ArrayList<Integer> id = new ArrayList<>();
  public static ArrayList<Integer> count = new ArrayList<>();
  public static int[] id1;
  public static int[] count1;
  static int idLast;

  public static void OpenFile() throws IOException {
    id.clear();
    count.clear();
    File file = new File(Reply.FILENAME2);
    try {
      BufferedReader in = new BufferedReader(new FileReader(file.getAbsoluteFile()));
      try {
        String s;
        int flag = 0;
        s = in.readLine();
        idLast = (Integer.parseInt(s)) + 1;
        while ((s = in.readLine()) != null) {
          if (flag == 0) {
            id.add(Integer.parseInt(s));
            flag = 1;
          } else {
            count.add(Integer.parseInt(s));
            flag = 0;
          }
        }
      } finally {
        in.close();
      }
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public static void print() {
    for (int i = 0; i < count1.length; i++) {
      System.out.println(id1[i] + "|" + count1[i]);
    }
  }


  public static void quickSort(int[] array1, int[] array2, int left, int right) {
    if (right <= left) {
      return;
    }
    int pivot1 = array1[right];
    int pivot2 = array2[right];
    int p = left;
    int i = left;
    while (i < right) {
      if (array1[i] < pivot1) {
        if (p != i) {
          int tmp = array1[p];
          array1[p] = array1[i];
          array1[i] = tmp;
          tmp = array2[p];
          array2[p] = array2[i];
          array2[i] = tmp;
        }
        p += 1;
      }
      i += 1;
    }
    array1[right] = array1[p];
    array1[p] = pivot1;
    array2[right] = array2[p];
    array2[p] = pivot2;
    quickSort(array1, array2, left, p - 1);
    quickSort(array1, array2, p + 1, right);
  }

  public static void SaveSort() throws FileNotFoundException {

    PrintStream printStream = new PrintStream(Reply.FILENAME2);
    try {
      printStream.println(idLast);
      for (int i = 0; i < count1.length - 1; i++) {
        printStream.println(id1[i]);
        printStream.println(count1[i]);
      }
    } finally {
      printStream.close();
    }
  }

}
