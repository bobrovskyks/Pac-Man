package application;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ModalWindow {
  final static String FILENAME = "steps.txt";
  public static final int BTNX = 70;
  public static final int BTNY = 200;
  public static final int LabelX = 40;
  public static final int LabelY = 40;
  public static final int SIZE = 300;
  static long IntUp;
  static long IntDown;
  static long IntRight;
  static long IntLeft;
  static long IntTotal;
  static long IntReadUP;
  static long IntReadDown;
  static long IntReadRight;
  static long IntReadLeft;
  static String readUP;
  static String readDown;
  static String readRight;
  static String readLeft;
  static String readTotal;
  static String UP;
  static String Down;
  static String Right;
  static String Left;
  static String Total;

  public static void setSteps(int _up, int _down, int _right, int _left, int _total) {
    IntUp = _up;
    IntDown = _down;
    IntRight = _right;
    IntLeft = _left;
    IntTotal = _total;
    try {
      OpenFile();
    } catch (IOException e) {
      e.printStackTrace();
    }
    readUP = Long.toString(IntReadUP);
    readDown = Long.toString(IntReadDown);
    readRight = Long.toString(IntReadRight);
    readLeft = Long.toString(IntReadLeft);
    readTotal = Long.toString(IntReadUP + IntReadDown + IntReadRight + IntReadLeft);
    UP = Long.toString(IntUp);
    Down = Long.toString(IntDown);
    Right = Long.toString(IntRight);
    Left = Long.toString(IntLeft);
    Total = Long.toString(IntTotal);
  }

  public static void OpenFile() throws IOException {
    File file = new File(FILENAME);
    try {
      BufferedReader in = new BufferedReader(new FileReader(file.getAbsoluteFile()));
      try {
        readUP = in.readLine();
        readDown = in.readLine();
        readRight = in.readLine();
        readLeft = in.readLine();
        total();
      } finally {
        in.close();
      }
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public static void total() {
    IntReadUP = (Long.parseLong(readUP)) + IntUp;
    IntReadDown = (Long.parseLong(readDown)) + IntDown;
    IntReadRight = (Long.parseLong(readRight)) + IntRight;
    IntReadLeft = (Long.parseLong(readLeft)) + IntLeft;
  }

  public static void saveSteps() throws FileNotFoundException {
    PrintStream printStream = new PrintStream(FILENAME);
    try {
      printStream.println(IntReadUP);
      printStream.println(IntReadDown);
      printStream.println(IntReadRight);
      printStream.println(IntReadLeft);
    } finally {
      printStream.close();
    }
  }

  public static void doModal() {
    Stage window = new Stage();
    window.initModality(Modality.APPLICATION_MODAL);
    Pane pane1 = new Pane();
    Label upLabel = new Label("Way Steps UP: " + UP + " / " + readUP);
    upLabel.setTranslateX(LabelX);
    upLabel.setTranslateY(LabelY);
    Label downLabel = new Label("Way Steps DOWN: " + Down + " / " + readDown);
    downLabel.setTranslateX(LabelX);
    downLabel.setTranslateY(LabelY + 20);
    Label rightLabel = new Label("Way Steps RIGHT: " + Right + " / " + readRight);
    rightLabel.setTranslateX(LabelX);
    rightLabel.setTranslateY(LabelY + 40);
    Label leftLabel = new Label("Way Steps LEFT: " + Left + " / " + readLeft);
    leftLabel.setTranslateX(LabelX);
    leftLabel.setTranslateY(LabelY + 60);
    Label totalLabel = new Label("Way Steps TOTAL: " + Total + " / " + readTotal);
    totalLabel.setTranslateX(LabelX);
    totalLabel.setTranslateY(LabelY + 80);

    Button btn = new Button("To continue...");
    btn.setTranslateX(BTNX+20);
    btn.setTranslateY(BTNY);
    btn.setOnAction(event6 -> {
      Ghost.lose = false;
      window.close();
    });
    try {
      saveSteps();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    pane1.getChildren().addAll(btn, upLabel, downLabel, rightLabel, leftLabel, totalLabel);
    Scene scene1 = new Scene(pane1, SIZE, SIZE-50);
    window.setScene(scene1);
    window.setTitle("Statistics");
    window.showAndWait();
  }
}
