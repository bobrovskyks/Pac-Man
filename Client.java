package application;

import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class Client implements Runnable {

  Thread clientThread;
  Server _thread;
  boolean mFinish = false;

  Client(Server serverThread) {
    _thread = serverThread;
    clientThread = new Thread(this, "Client_thread");
    clientThread.start();
  }

  @Override
  public void run() {
    if (mFinish == true) {
      return;
    }
  }

  public void finish() {
    mFinish = true;
  }

  public boolean getStatus() {
    if (clientThread.isAlive() == true)
      return true;
    else {
      return false;
    }
  }

  public void deletePoints() {
    Main.bonuses.remove(Character.removeCirc);
    Main.root1.getChildren().remove(Character.removeCirc);
    System.out.println("client");
    finish();
  }

  public void checkLose(Ghost _ghost, Character _player) {
    if (_ghost.getBoundsInParent().intersects(_player.getBoundsInParent())) {
      Ghost.LOSE = new Label("You LOSE!");
      Ghost.LOSE.setLayoutX(Ghost.LOSEX);
      Ghost.LOSE.setLayoutY(Ghost.LOSEY);
      Ghost.LOSE.setTextFill(Color.RED);
      Ghost.LOSE.setFont(Font.font("Arial", FontWeight.BOLD, Ghost.SIZELETTRS));
      Main.root1.getChildren().add(Ghost.LOSE);
      Ghost.lose = true;
      try {
        Reply.write();
      } catch (Exception e) {
        e.printStackTrace();
      }
      try {
        Reply.SaveStatistics();
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    finish();
  }

  public void troughWalls(Character _player) {
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
}


