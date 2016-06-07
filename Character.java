package application;

import javafx.geometry.Rectangle2D;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

/** Сlass Character */
public class Character extends Pane {
  static boolean flagrand = true;
  static int way;
  static final int LABELX = 130;
  static final int LABELY = 300;
  static final int STAR_XY = 75;
  static final int WINSCORE = 49;
  static final int SIZELETTERS = 100;
  static final int RANGE = 4;
  static final int MOVES = 1;
  static final int SPEEDB = 3;
  static final int LEFTBORDER = 0;
  static final int RIGHTBORDER = 700;
  static final int UPBORDER = 0;
  static final int DOWNBORDER = 700;
  static final int MOVINGX = 590;
  static final int TIME = 200;
  static final int COUNT = 2;
  static final int COLUMNS = 2;
  static final int OFFSETX = 0;
  static final int OFFSETY = 0;
  static final int WIDTH = 54;
  static final int HEIGHT = 54;
  static int flag = 1;
  static int[][] mass = Walls.mas;
  static boolean win = false;
  static Label WIN;
  static int score = 0;
  ImageView imageView;
  static Circle removeCirc = null;
  SpriteAnimation animation;
  Server _server;

  /** Constructor */
  public Character(ImageView imageView) {
    this.imageView = imageView;
    this.imageView.setViewport(new Rectangle2D(OFFSETX, OFFSETY, WIDTH, HEIGHT));
    animation = new SpriteAnimation(imageView, Duration.millis(TIME), COUNT, COLUMNS, OFFSETX,
        OFFSETY, WIDTH, HEIGHT);
    this.setTranslateX(STAR_XY);
    this.setTranslateY(STAR_XY);
    getChildren().addAll(imageView);
  }

  /** Method for motion axis X */
  public void moveX(int x) {
    boolean right = x > 0 ? true : false;
    if (right) {
      this.setTranslateX(this.getTranslateX() + Math.abs(x));
    } else {
      this.setTranslateX(this.getTranslateX() - Math.abs(x));
    }
    isBonuseEat();
    if (getTranslateX() < LEFTBORDER) {
      setTranslateX(RIGHTBORDER);
    }
    if ((getTranslateX() < LEFTBORDER) && (getTranslateY() < RIGHTBORDER)) {
      if (right) {
        setTranslateX(getTranslateX() - Math.abs(x));
        return;
      } else {
        setTranslateX(getTranslateX() + Math.abs(x));
        return;
      }
    }
    if (getTranslateX() > RIGHTBORDER) {
      setTranslateX(LEFTBORDER);
    }
    Walls.walls.forEach((rect) -> {
      if (this.getBoundsInParent().intersects(rect.getBoundsInParent())) {
        if (right) {
          setTranslateX(getTranslateX() - Math.abs(x));
          return;
        } else {
          setTranslateX(getTranslateX() + Math.abs(x));
          return;
        }
      }
    });
  }

  /** Method for motion axis Y */
  public void moveY(int y) {
    boolean down = y > 0 ? true : false;
    if (down) {
      this.setTranslateY(this.getTranslateY() + Math.abs(y));
    } else {
      this.setTranslateY(this.getTranslateY() - Math.abs(y));
    }
    isBonuseEat();
    if (getTranslateY() < UPBORDER) {
      setTranslateY(DOWNBORDER);
    }
    if ((getTranslateY() < LEFTBORDER) && (getTranslateX() < RIGHTBORDER)) {
      if (down) {
        setTranslateY(getTranslateY() - Math.abs(y));
        return;
      } else {
        setTranslateY(getTranslateY() + Math.abs(y));
        return;
      }
    }
    if (getTranslateY() > DOWNBORDER) {
      setTranslateY(UPBORDER);
    }
    Walls.walls.forEach((rect) -> {
      if (this.getBoundsInParent().intersects(rect.getBoundsInParent())) {
        if (down) {
          setTranslateY(getTranslateY() - Math.abs(y));
          return;
        } else {
          setTranslateY(getTranslateY() + Math.abs(y));
          return;
        }
      }
    });
  }

  /** Method return the X coordinate */
  public int getX() {
    return ((int) getTranslateX());
  }

  /** Method return the Y coordinate */
  public int getY() {
    return ((int) getTranslateY());
  }

  /** The method set points, eaten balls */
  public void isBonuseEat() {
    Server _server;
    _server = new Server();
    _server.checkWin(this);
  }
}


