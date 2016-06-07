package application;

import javafx.geometry.Rectangle2D;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

/** Class ghost will stop playing. */
public class Ghost extends Pane {
  ImageView imageView;
  static boolean lose = false;
  static Label LOSE;
  static final int SIZELETTRS = 100;
  static final int LOSEX = 130;
  static final int LOSEY = 300;
  static final int OFFSETX = 0;
  static final int OFFSETY = 0;
  static final int WIDTH = 60;
  static final int HEIGHT = 60;

  /** Constructor */
  public Ghost(ImageView imageView, int xx, int yy) {
    this.imageView = imageView;
    this.imageView.setViewport(new Rectangle2D(OFFSETX, OFFSETY, WIDTH, HEIGHT));
    this.setTranslateX(xx);
    this.setTranslateY(yy);
    getChildren().addAll(imageView);
  }
}
