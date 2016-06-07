package application;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;

/** Ñlass Reply. This class is designed to replay the game */
public class Reply {
  final static String FILENAME1 = "save.txt";
  final static String FILENAME2 = "statistics.txt";
  final static String FILENAME3 = "best.txt";
  final static double SpeedMod = 3;
  public static int up = 0;
  public static int down = 0;
  public static int right = 0;
  public static int left = 0;
  public static int stay = 0;
  public static int LastStep;
  public static int ID = 0;
  public static ArrayList<Integer> StepsWrite = new ArrayList<Integer>();
  public static ArrayList<Integer> StepsRead = new ArrayList<Integer>();

  /** The method, which writes to a file replay */
  public static void write() throws FileNotFoundException {
    PrintStream printStream = new PrintStream(FILENAME1);
    try {
      for (Integer item : StepsWrite) {
        printStream.println(item);
      }
    } finally {
      printStream.close();
    }
  }

  public static void writeBestRepley() throws FileNotFoundException {
    PrintStream printStream = new PrintStream(FILENAME3);
    try {
      for (Integer item : StepsWrite) {
        printStream.println(item);
      }
    } finally {
      printStream.close();
    }
  }

  /** The method, which reads the replay file */
  public static void read(String filename) throws IOException {
    File file = new File(filename);
    try {
      BufferedReader in = new BufferedReader(new FileReader(file.getAbsoluteFile()));
      try {
        String s;
        while ((s = in.readLine()) != null) {
          StepsRead.add(Integer.parseInt(s));
        }
      } finally {
        in.close();
      }
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  /** The method, which is responsible for the movement in the replay mode. */
  public static void replymod(Character _player) {
    if (StepsRead.isEmpty() == true) {
      Main.replyflag = false;
      switch (LastStep) {
        case 1: {
          _player.isBonuseEat();
          _player.setTranslateY(_player.getTranslateY() - SpeedMod);
        }
        case 2: {
          _player.isBonuseEat();
          _player.setTranslateY(_player.getTranslateY() + SpeedMod);
        }
        case 3: {
          _player.isBonuseEat();
          _player.setTranslateX(_player.getTranslateX() + SpeedMod);
        }
        case 4: {
          _player.isBonuseEat();
          _player.setTranslateX(_player.getTranslateX() + SpeedMod);
        }
      }
      return;
    }
    _player.animation.play();
    switch (StepsRead.get(0)) {
      case 1: {
        _player.animation.setOffsetY(Main.UPANIMATION);
        _player.isBonuseEat();
        _player.setTranslateY(_player.getTranslateY() - SpeedMod);
        Walls.walls.forEach((rect) -> {
          if (_player.getBoundsInParent().intersects(rect.getBoundsInParent())) {
            _player.setTranslateY(_player.getTranslateY() + SpeedMod);
            return;
          }
        });
        LastStep = 1;
        StepsRead.remove(0);
        break;
      }
      case 2: {
        _player.animation.setOffsetY(Main.DOWNANIMATION);
        _player.isBonuseEat();
        _player.setTranslateY(_player.getTranslateY() + SpeedMod);
        Walls.walls.forEach((rect) -> {
          if (_player.getBoundsInParent().intersects(rect.getBoundsInParent())) {
            _player.setTranslateY(_player.getTranslateY() - SpeedMod);
            return;
          }
        });
        LastStep = 2;
        StepsRead.remove(0);
        break;
      }
      case 3: {
        _player.animation.setOffsetY(Main.RIGHTANIMATION);
        _player.isBonuseEat();
        _player.setTranslateX(_player.getTranslateX() + SpeedMod);
        Walls.walls.forEach((rect) -> {
          if (_player.getBoundsInParent().intersects(rect.getBoundsInParent())) {
            _player.setTranslateX(_player.getTranslateX() - SpeedMod);
            return;
          }
        });
        LastStep = 3;
        StepsRead.remove(0);
        break;
      }
      case 4: {
        _player.animation.setOffsetY(Main.LEFTANIMATION);
        _player.isBonuseEat();
        _player.setTranslateX(_player.getTranslateX() - SpeedMod);
        Walls.walls.forEach((rect) -> {
          if (_player.getBoundsInParent().intersects(rect.getBoundsInParent())) {
            _player.setTranslateX(_player.getTranslateX() + SpeedMod);
            return;
          }
        });
        LastStep = 4;
        StepsRead.remove(0);
        break;
      }
      case 0: {
        StepsRead.remove(0);
        break;
      }
    }
    if (_player.getTranslateY() > Character.DOWNBORDER) {
      _player.setTranslateY(Character.UPBORDER);
    }
    if (_player.getTranslateY() < Character.UPBORDER) {
      _player.setTranslateY(Character.DOWNBORDER);
    }

    if (_player.getTranslateX() > Character.RIGHTBORDER) {
      _player.setTranslateX(Character.LEFTBORDER);
    }

    if (_player.getTranslateX() < Character.LEFTBORDER) {
      _player.setTranslateX(Character.RIGHTBORDER);
    }
  }

  /** The method of reading the next ID from a file */
  public static int ReadID() throws IOException {
    File file = new File(FILENAME2);
    String id;
    try {
      BufferedReader in = new BufferedReader(new FileReader(file.getAbsoluteFile()));
      try {
        id = in.readLine();
      } finally {
        in.close();
      }
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    return Integer.parseInt(id);
  }

  /** The method that maintains statistics successful games played */
  public static void SaveStatistics() throws IOException {
    ID = ReadID();
    String s = StepsWrite.toString();
    String text = ID + "\n" + StepsWrite.size() + "\n" + s + "\n";
    ID += 1;
    try {
      Files.write(Paths.get("statistics1.txt"), text.getBytes(), StandardOpenOption.APPEND);
    } catch (IOException e) {
      System.out.println(e);
    }
    text = ID + "\n" + StepsWrite.size() + "\n";
    ID += 1;
    try {
      Files.write(Paths.get(FILENAME2), text.getBytes(), StandardOpenOption.APPEND);
    } catch (IOException ex) {
      System.out.println(ex);
    }

    @SuppressWarnings("resource")
    RandomAccessFile file = new RandomAccessFile(new File(FILENAME2), "rw");
    file.seek(0);
    text = ID + "";
    file.writeBytes(text);
  }


  public static String temp1;
  public static String temp2;
  public static String temp3;
  public static String temp4;
  public static String temp5;
  public static int flag = 0;

  public static void readP() throws IOException {
    File file = new File("statistics1.txt");
    BufferedReader in = new BufferedReader(new FileReader(file.getAbsoluteFile()));
    try {
      String s;
      s = in.readLine();
      temp1 = s;
      while ((s = in.readLine()) != null) {
        if (flag == 0) {
          temp2 = s;
          flag++;
          continue;
        }
        if (flag == 1) {
          temp3 = s;
          flag++;
          continue;
        }
        if (flag == 2) {
          temp4 = s;
          flag++;
          writeP();
          flag = 0;
          continue;
        }
      }
    } finally {
      in.close();
    }
  }

  public static void writeP() throws IOException {
    Scala scala1 = new Scala();
    try {
      File f = new File("psevdo.txt");
      RandomAccessFile out = new RandomAccessFile(f, "rw");
      out.seek(f.length());
      out.write(("Game ID: " + temp2 + "\n").getBytes("Cp1251"));
      out.write(("Count Steps: " + temp3 + "\n").getBytes("Cp1251"));
      out.write(("Steps: " + "\n").getBytes("Cp1251"));
      char[] charArray = temp4.toCharArray();
      for (int i = 0; i < temp4.length() - 1; i++) {
        out.write((scala1.makePsevdo(charArray[i])).getBytes("Cp1251"));
      }
      out.write(("\n").getBytes("Cp1251"));
      out.close();

    } finally {
    }
  }

  public static void doMas() {
    JavaSort.id1 = new int[JavaSort.count.size()];
    JavaSort.count1 = new int[JavaSort.count.size()];
    for (int i = 0; i < JavaSort.id.size(); i++) {
      JavaSort.count1[i] = JavaSort.count.get(i);
      JavaSort.id1[i] = JavaSort.id.get(i);
    }
  }

  public static void clean() throws FileNotFoundException {
    try {
      FileWriter fstream1;
      try {
        fstream1 = new FileWriter("psevdo.txt");
        BufferedWriter out1 = new BufferedWriter(fstream1);
        out1.write("");
        out1.close(); //
      } catch (IOException e) {
        e.printStackTrace();
      }
  }
    finally {
    }
}
}


/*
 * def ff(n:Int, x:(Int, Int)=>Int):Int={ x(n,5) } //> ff: (n: Int, x: (Int, Int) => Int)Int ff(7,
 * (x, y)=>x*y) //> res0: Int = 35
 * 
 * }
 */
