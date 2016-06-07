package application;

import java.util.ArrayList;
import application.Main;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

/** Class Walls */
public class Walls {
  public static final int size = 70;
  public static final int CircleS = 0;
  public static final int CircleRad = 10;
  public static final int masSize = 10;
  public static ArrayList<Rectangle> walls = new ArrayList<>();
  public static int[][] mas = {{1, 0, 1, 1, 1, 1, 1, 1, 1, 1}, {1, 0, 0, 0, 0, 0, 0, 0, 0, 1},
      {1, 0, 1, 1, 0, 1, 1, 0, 0, 1}, {1, 0, 0, 1, 0, 0, 1, 1, 0, 1},
      {1, 1, 0, 1, 1, 0, 0, 1, 0, 1}, {1, 0, 0, 0, 0, 0, 0, 0, 0, 1},
      {1, 0, 1, 1, 0, 1, 0, 1, 0, 1}, {1, 0, 1, 0, 0, 0, 0, 1, 0, 1},
      {0, 0, 0, 0, 1, 1, 0, 0, 0, 0}, {1, 0, 1, 1, 1, 1, 1, 1, 1, 1}};

  /** The method of producing the walls and balls */
  public static void createwallsandbonuses() {
    for (int i = 0; i < masSize; i++) {
      for (int j = 0; j < masSize; j++) {
        if (mas[i][j] == 1) {
          int place1, place2;
          try {
            place1 = j * size;
            place2 = i * size;
          } catch (ArithmeticException e) {
            System.out.println("Error");
            return;
          }
          /** Drawing a rectangle */
          Rectangle rect = new Rectangle(size, size, Color.DARKBLUE);
          rect.setX(place1);
          rect.setY(place2);
          walls.add(rect);
          Main.root1.getChildren().addAll(rect);
        } else {
          int place1, place2;
          Rectangle rect = new Rectangle(size, size, Color.BLACK);
          rect.setX(j * size);
          rect.setY(i * size);
          try {
            place1 = (j * size) + size / 2;
            place2 = (i * size) + size / 2;
          } catch (ArithmeticException e) {
            System.out.println("Error");
            return;
          }
          /** Drawing of the ball */
          Circle circ = new Circle(CircleS, CircleS, CircleRad, Color.CYAN);
          circ.setCenterX(place1);
          circ.setCenterY(place2);
          Main.bonuses.add(circ);
          Main.root1.getChildren().addAll(rect,circ);
        }
      }
    }
  }
}
