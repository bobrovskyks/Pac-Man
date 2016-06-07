package application;

import java.util.Random;

import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class Server implements Runnable {
  Client _thread;
  Thread serverThread;
  boolean mFinish = false;

  @Override
  public void run() {
    if (mFinish == true) {
      return;
    }
  }

  Server() {
    if (serverThread == null) {
      serverThread = new Thread(this, "Server_thread");
      serverThread.start();
    }
  }

  public void finish() {
    mFinish = true;
  }

  public void checkWin(Character _player) {
    System.out.println("server");
    Main.bonuses.forEach((circ) -> {
      if (_player.getBoundsInParent().intersects(circ.getBoundsInParent())) {
        Character.removeCirc = circ;
        Character.score++;
        /** When dialing 49 points the player wins */
        if (Character.score == Character.WINSCORE) {
          Character.win = true;
          Character.WIN = new Label("You WIN!");
          Character.WIN.setLayoutX(Character.LABELX);
          Character.WIN.setLayoutY(Character.LABELY);
          Character.WIN.setTextFill(Color.RED);
          Character.WIN.setFont(Font.font("Arial", FontWeight.BOLD, Character.SIZELETTERS));
          Main.root1.getChildren().add(Character.WIN);
          try {
            Reply.write();
          } catch (Exception e) {
            e.printStackTrace();
          }
          if (Main.replyflag == false) {
            try {
              Reply.SaveStatistics();
            } catch (Exception e) {
              e.printStackTrace();
            }
          }
          /*try {
            JavaSort.OpenFile();
          } catch (Exception e) {
            e.printStackTrace();
          }
          int buffer = JavaSort.count.get(0);
          for (int i = 1; i < JavaSort.count.size(); i++) {
            if (buffer > JavaSort.count.get(i)) {
              buffer = JavaSort.count.get(i);
            }
          }
          if (Reply.StepsWrite.size() < buffer) {
            try {
              Reply.writeBestRepley();
            } catch (Exception e) {
              e.printStackTrace();
            }
          }*/
        }
      }
    });
    _thread = new Client(this);
    _thread.deletePoints();
    finish();
  }

  public void moveGost(Ghost _ghost, int _x, int _y, Ghost _ghost2, int _speed, Character _player,
      Server _server) {
    int x = _x;
    int y = _y;
    Client _client;
    _client = new Client(_server);
    if (_ghost.getTranslateX() <= x) {
      _ghost.setTranslateX(_ghost.getTranslateX() + _speed);
      Walls.walls.forEach((rect) -> {
        if (_ghost.getBoundsInParent().intersects(rect.getBoundsInParent())) {
          _ghost.setTranslateX(_ghost.getTranslateX() - _speed);
          return;
        }
      });
      if (_ghost.getBoundsInParent().intersects(_ghost2.getBoundsInParent())) {
        _ghost.setTranslateX(_ghost.getTranslateX() - _speed);
      }
      _client.checkLose(_ghost, _player);
      if (_ghost.getTranslateY() < y) {
        _ghost.setTranslateY(_ghost.getTranslateY() + _speed);
        Walls.walls.forEach((rect) -> {
          if (_ghost.getBoundsInParent().intersects(rect.getBoundsInParent())) {
            _ghost.setTranslateY(_ghost.getTranslateY() - _speed);
            return;
          }
        });
        if (_ghost.getBoundsInParent().intersects(_ghost2.getBoundsInParent())) {
          _ghost.setTranslateY(_ghost.getTranslateY() - _speed);
        }
        _client.checkLose(_ghost, _player);
      } else {
        _ghost.setTranslateY(_ghost.getTranslateY() - _speed);
        Walls.walls.forEach((rect) -> {
          if (_ghost.getBoundsInParent().intersects(rect.getBoundsInParent())) {
            _ghost.setTranslateY(_ghost.getTranslateY() + _speed);
            return;
          }
        });
        if (_ghost.getBoundsInParent().intersects(_ghost2.getBoundsInParent())) {
          _ghost.setTranslateY(_ghost.getTranslateY() + _speed);
        }
        _client.checkLose(_ghost, _player);
      }
    } else {
      _ghost.setTranslateX(_ghost.getTranslateX() - _speed);
      Walls.walls.forEach((rect) -> {
        if (_ghost.getBoundsInParent().intersects(rect.getBoundsInParent())) {
          _ghost.setTranslateX(_ghost.getTranslateX() + _speed);
          return;
        }
      });
      if (_ghost.getBoundsInParent().intersects(_ghost2.getBoundsInParent())) {
        _ghost.setTranslateX(_ghost.getTranslateX() + _speed);
      }
      _client.checkLose(_ghost, _player);
      if (_ghost.getTranslateY() < y) {
        _ghost.setTranslateY(_ghost.getTranslateY() + _speed);
        Walls.walls.forEach((rect) -> {
          if (_ghost.getBoundsInParent().intersects(rect.getBoundsInParent())) {
            _ghost.setTranslateY(_ghost.getTranslateY() - _speed);
            return;
          }
        });
        if (_ghost.getBoundsInParent().intersects(_ghost2.getBoundsInParent())) {
          _ghost.setTranslateY(_ghost.getTranslateY() - _speed);
        }
        _client.checkLose(_ghost, _player);
      } else {
        _ghost.setTranslateY(_ghost.getTranslateY() - _speed);
        Walls.walls.forEach((rect) -> {
          if (_ghost.getBoundsInParent().intersects(rect.getBoundsInParent())) {
            _ghost.setTranslateY(_ghost.getTranslateY() + _speed);
            return;
          }
        });
        if (_ghost.getBoundsInParent().intersects(_ghost2.getBoundsInParent())) {
          _ghost.setTranslateY(_ghost.getTranslateY() + _speed);
        }
        _client.checkLose(_ghost, _player);
      }
    }
    finish();
  }

  public static int botrand() {
    int number;
    Random rand = new Random();
    try {
      number = rand.nextInt(Character.RANGE);
    } catch (ArithmeticException e) {
      System.out.println("Error");
      return -1;
    }
    return number;
  }

  /** Game Bot method */
  public void bot(Character _player, Ghost _ghost1, Ghost _ghost2, Server _server) {
    Client _client;
    _client = new Client(_server);
    _player.animation.play();
    if (Character.flagrand == false) {
      Character.way = botrand();
      Character.flagrand = true;
    }
    switch (Character.way) {
      case 0: {
        _player.animation.setOffsetY(Main.RIGHTANIMATION);
        _player.isBonuseEat();
        _player.setTranslateX(_player.getTranslateX() + Character.SPEEDB);
        Reply.StepsWrite.add(3);
        Walls.walls.forEach((rect) -> {
          if (_player.getBoundsInParent().intersects(rect.getBoundsInParent())) {
            _player.setTranslateX(_player.getTranslateX() - Character.SPEEDB);
            Character.flagrand = false;
            return;
          }
        });
        break;
      }
      case 1: {
        _player.animation.setOffsetY(Main.LEFTANIMATION);
        _player.isBonuseEat();
        _player.setTranslateX(_player.getTranslateX() - Character.SPEEDB);
        Reply.StepsWrite.add(4);
        Walls.walls.forEach((rect) -> {
          if (_player.getBoundsInParent().intersects(rect.getBoundsInParent())) {
            _player.setTranslateX(_player.getTranslateX() + Character.SPEEDB);
            Character.flagrand = false;
            return;
          }
        });
        break;
      }
      case 2: {
        _player.animation.setOffsetY(Main.DOWNANIMATION);
        _player.isBonuseEat();
        _player.setTranslateY(_player.getTranslateY() + Character.SPEEDB);
        Reply.StepsWrite.add(2);
        Walls.walls.forEach((rect) -> {
          if (_player.getBoundsInParent().intersects(rect.getBoundsInParent())) {
            _player.setTranslateY(_player.getTranslateY() - Character.SPEEDB);
            Character.flagrand = false;
            return;
          }
        });
        break;
      }
      case 3: {
        _player.animation.setOffsetY(Main.UPANIMATION);
        _player.isBonuseEat();
        _player.setTranslateY(_player.getTranslateY() - Character.SPEEDB);
        Reply.StepsWrite.add(1);
        Walls.walls.forEach((rect) -> {
          if (_player.getBoundsInParent().intersects(rect.getBoundsInParent())) {
            _player.setTranslateY(_player.getTranslateY() + Character.SPEEDB);
            Character.flagrand = false;
            return;
          }
        });
        break;
      }
    }
    _client.troughWalls(_player);
  }
}

