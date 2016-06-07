package application;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import javafx.animation.*;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

/** The main class */
public class Main extends Application {
  public static boolean botflag = false;
  public static boolean replyflag = false;
  public static final int ONEHUNDRED = 100;
  public static final int XGHOST1 = 570;
  public static final int YGHOST1 = 570;
  public static final int XGHOST2 = 90;
  public static final int YGHOST2 = 570;
  public static final int SPEEDPLAYER = 3;
  public static final int UPANIMATION = 165;
  public static final int DOWNANIMATION = 0;
  public static final int RIGHTANIMATION = 110;
  public static final int LEFTANIMATION = 54;
  public static final int ICONSIZE = 50;
  public static final int XENTER = 130;
  public static final int YENTER = 600;
  public static final int BIGLETTERSIZE = 80;
  public static final int SMALLLETTERSSIZE = 40;
  public static final int RECTANGLESIZE1 = 600;
  public static final int RECTANGLESIZE2 = 80;
  public static final int SUBMENUX = 50;
  public static final int SUBMENUY = 100;
  public static final int XSCORE = 500;
  public static final int YSCORE = 0;
  public static final int XID = 150;
  public static final int YID = 0;
  public static final int CLASSICSPEED = 1;
  public static final int HARDSPEED = 2;
  public static int TotalSpeed = 1;
  public static int size = 700;
  static PlayMusic pm = new PlayMusic("music.mp3");
  private VBox taskbar;
  static Label scoreLabel;
  static Label idLabel;
  Label _Enter;
  public static Pane root1 = new Pane();
  private HashMap<KeyCode, Boolean> keys = new HashMap<>();
  public static ArrayList<Circle> bonuses = new ArrayList<>();
  Image image1 = new Image(getClass().getResourceAsStream("pac.png"));
  Image image2 = new Image(getClass().getResourceAsStream("ghost1.png"));
  Image image3 = new Image(getClass().getResourceAsStream("ghost1.png"));
  ImageView imageView1 = new ImageView(image1);
  ImageView imageView2 = new ImageView(image2);
  ImageView imageView3 = new ImageView(image3);
  Scala scala1 = new Scala();
  Character player = new Character(imageView1);
  Ghost ghost1 = new Ghost(imageView2, XGHOST1, YGHOST1);
  Ghost ghost2 = new Ghost(imageView3, XGHOST2, YGHOST2);
  Server _server;

  /** Update method that monitors keystrokes */
  public void update() {
    if (isPressed(KeyCode.UP)) {
      player.animation.play();
      player.animation.setOffsetY(UPANIMATION);
      player.moveY(-SPEEDPLAYER);
      Reply.StepsWrite.add(1);
      scala1.add(1);
      Reply.up++;
    } else {
      if (isPressed(KeyCode.DOWN)) {
        player.animation.play();
        player.animation.setOffsetY(DOWNANIMATION);
        player.moveY(SPEEDPLAYER);
        Reply.StepsWrite.add(2);
        scala1.add(2);
        Reply.down++;
      } else {
        if (isPressed(KeyCode.RIGHT)) {
          player.animation.play();
          player.animation.setOffsetY(RIGHTANIMATION);
          player.moveX(SPEEDPLAYER);
          Reply.StepsWrite.add(3);
          scala1.add(3);
          Reply.right++;
        } else {
          if (isPressed(KeyCode.LEFT)) {
            player.animation.play();
            player.animation.setOffsetY(LEFTANIMATION);
            player.moveX(-SPEEDPLAYER);
            Reply.StepsWrite.add(4);
            scala1.add(4);
            Reply.left++;
          } else {
            player.animation.stop();
            if (botflag == false) {
              Reply.StepsWrite.add(0);
              Reply.stay++;
            }
          }
        }
      }
    }
    scoreLabel.setText("Score: " + Character.score);
    if (replyflag == true) {
      idLabel.setText("Reply game...");
    } else {
      try {
        idLabel.setText("ID: " + Reply.ReadID());
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  public boolean isPressed(KeyCode key) {
    return keys.getOrDefault(key, false);
  }

  /** The method of adding a button with an icon */
  private Button addButton(String _icon) {
    Button b = new Button();
    Image image = new Image(getClass().getResourceAsStream(_icon));
    ImageView img = new ImageView(image);
    img.setFitHeight(ICONSIZE);
    img.setFitWidth(ICONSIZE);
    b.setGraphic(img);
    return b;
  }

  @Override
  /** Start */
  public void start(Stage primaryStage) {
    Pane root = new Pane();
    Image image = new Image(getClass().getResourceAsStream("fon.jpg"));
    ImageView img = new ImageView(image);
    img.setFitHeight(size);
    img.setFitWidth(size);
    root.getChildren().add(img);
    _Enter = new Label("Press Enter");
    _Enter.setLayoutX(XENTER);
    _Enter.setLayoutY(YENTER);
    _Enter.setTextFill(Color.WHITE);
    _Enter.setFont(Font.font("Arial", FontWeight.BOLD, BIGLETTERSIZE));
    root.getChildren().add(_Enter);
    MenuItem newGame = new MenuItem("NEW GAME");
    MenuItem options = new MenuItem("SETTINGS");
    MenuItem exitGame = new MenuItem("EXIT");
    SubMenu mainMenu = new SubMenu(newGame, options, exitGame);
    MenuItem soundOn = new MenuItem("SOUND ON");
    MenuItem soundOff = new MenuItem("SOUND OFF");
    MenuItem ClassicMode = new MenuItem("CLASSIC MODE");
    MenuItem HardMode = new MenuItem("HARD MODE");
    MenuItem optionsBack = new MenuItem("BACK");
    SubMenu optionsMenu = new SubMenu(soundOn, soundOff, ClassicMode, HardMode, optionsBack);
    MenuBox menuBox = new MenuBox(mainMenu);
    newGame.setOnMouseClicked(event -> {
      taskbar = new VBox(10);
      Button restart = addButton("icon-1.png");
      Button sort = addButton("icon-2.png");
      Button restore = addButton("icon-3.png");
      Button bot = addButton("icon-4.png");
      Button starRecord = addButton("icon-5.png");
      Button statistics = addButton("icon-6.png");
      Button psevdo = addButton("icon-7.png");
      taskbar.getChildren().addAll(restart, sort, restore, bot, starRecord, statistics, psevdo);
      scoreLabel = new Label("Score: " + Character.score);
      try {
        idLabel = new Label("ID: " + Reply.ReadID());
      } catch (Exception e2) {
        e2.printStackTrace();
      }
      scoreLabel.setLayoutX(XSCORE);
      scoreLabel.setLayoutY(YSCORE);
      scoreLabel.setTextFill(Color.WHITE);
      scoreLabel.setFont(Font.font("Arial", FontWeight.BOLD, SMALLLETTERSSIZE));
      idLabel.setLayoutX(XID);
      idLabel.setLayoutY(YID);
      idLabel.setTextFill(Color.WHITE);
      idLabel.setFont(Font.font("Arial", FontWeight.BOLD, SMALLLETTERSSIZE));
      root1.setPrefSize(size, size);
      root1.getChildren().addAll(player, ghost1, ghost2, scoreLabel, taskbar, idLabel);
      Scene scene = new Scene(root1, size, size);
      scene.setOnKeyPressed(event1 -> keys.put(event1.getCode(), true));
      scene.setOnKeyReleased(event1 -> {
        keys.put(event1.getCode(), false);
      });
      AnimationTimer timer = new AnimationTimer() {
        @Override
        public void handle(long now) {
          if ((Ghost.lose == false) && (Character.win == false)) {
            update();
            _server = new Server();
            if (botflag == true) {
              _server.bot(player, ghost1, ghost2, _server);
            }
            if (replyflag == true) {
              Reply.replymod(player);
            }
            _server = new Server();
            _server.moveGost(ghost1, player.getX(), player.getY(), ghost2, TotalSpeed, player,
                _server);
            _server.moveGost(ghost2, player.getX(), player.getY(), ghost1, TotalSpeed, player,
                _server);
          }
        }
      };
      restart.setOnMouseClicked(
          event0 -> restart(player, ghost1, ghost2, scoreLabel, taskbar, idLabel));
      sort.setOnMouseClicked(event1 -> {
        Scala scala = new Scala();
        long lDelta = 0;
        try {
          JavaSort.OpenFile();
          Reply.doMas();
          long lBegin = scala.getStartEndTime();
          scala.sortArray(JavaSort.count1, JavaSort.id1, 0, JavaSort.count.size() - 1);
          long lEnd = scala.getStartEndTime();
          lDelta = lEnd - lBegin;
        } catch (Exception e) {
          e.printStackTrace();
        }
        long lBegin1 = System.currentTimeMillis();
        JavaSort.quickSort(JavaSort.count1, JavaSort.id1, 0, JavaSort.count.size() - 1);
        long lEnd1 = System.currentTimeMillis();
        long lDelta1 = lEnd1 - lBegin1;
        JavaSort.print();
        System.out.println("JavaSort Success. Time: " + lDelta1);
        System.out.println("ScalaSort Success. Time: " + lDelta);
      });
      restore.setOnMouseClicked(event2 -> {
        restart(player, ghost1, ghost2, scoreLabel, taskbar, idLabel);
        try {
          Reply.read(Reply.FILENAME1);
        } catch (Exception e) {
          e.printStackTrace();
        }
        replyflag = true;
      });
      /** The inclusion of bots */
      bot.setOnMouseClicked(event3 -> {
        if (botflag == true) {
          botflag = false;
        } else {
          botflag = true;
        }
      });
      starRecord.setOnMouseClicked(event4 -> {
        restart(player, ghost1, ghost2, scoreLabel, taskbar, idLabel);
        try {
          JavaSort.OpenFile();
        } catch (Exception e1) {
          e1.printStackTrace();
        }
        try {
          Reply.read(Reply.FILENAME3);
        } catch (Exception e) {
          e.printStackTrace();
        }
        replyflag = true;
      });

      statistics.setOnMouseClicked(event5 -> {
        Ghost.lose = true;
        ModalWindow.setSteps(scala1.countUp(), scala1.countDown(), scala1.countRight(),
            scala1.countLeft(), scala1.totalCount());
        ModalWindow.doModal();
      });
      psevdo.setOnMouseClicked(event6 -> {
        try {
          Reply.clean();
          Reply.readP();
        } catch (Exception e) {
          e.printStackTrace();
        }
      });
      timer.start();
      primaryStage.setScene(scene);
      primaryStage.show();
    });
    options.setOnMouseClicked(event -> menuBox.setSubMenu(optionsMenu));
    exitGame.setOnMouseClicked(event -> System.exit(0));
    optionsBack.setOnMouseClicked(event -> menuBox.setSubMenu(mainMenu));
    soundOn.setOnMouseClicked(event -> pm.playMusic());
    soundOff.setOnMouseClicked(event -> pm.stopMusic());
    /** The choice of game difficulty modes */
    ClassicMode.setOnMouseClicked(event -> {
      TotalSpeed = CLASSICSPEED;
      menuBox.setSubMenu(mainMenu);
    });
    HardMode.setOnMouseClicked(event -> {
      TotalSpeed = HARDSPEED;
      menuBox.setSubMenu(mainMenu);
    });
    root.getChildren().addAll(menuBox);
    Scene scene = new Scene(root, size, size);
    scene.setOnKeyPressed(event -> {
      if (event.getCode() == KeyCode.ENTER) {
        FadeTransition ft = new FadeTransition(Duration.seconds(1), menuBox);
        pm.playMusic();
        if (!menuBox.isVisible()) {
          ft.setFromValue(0);
          ft.setToValue(1);
          ft.play();
          menuBox.setVisible(true);
        } else {
          ft.setFromValue(1);
          ft.setToValue(0);
          ft.setOnFinished(evt -> menuBox.setVisible(false));
          ft.play();
        }
      }
    });
    /** Display scene */
    primaryStage.setTitle("Pac-man");
    primaryStage.setScene(scene);
    primaryStage.show();
  }

  public static void main(String[] args) {
    Walls.createwallsandbonuses();
    launch(args);
  }

  /** The method of restarting play */
  public static void restart(Character _player, Ghost _ghost1, Ghost _ghost2, Label _scoreLabel,
      VBox _taskbar, Label _idLabel) {
    bonuses.clear();
    Reply.right = 0;
    Reply.left = 0;
    Reply.up = 0;
    Reply.down = 0;
    Reply.stay = 0;
    Walls.walls.clear();
    root1.getChildren().clear();
    Walls.createwallsandbonuses();
    Reply.StepsWrite.clear();
    Character.score = 0;
    Character.flagrand = false;
    replyflag = false;
    botflag = false;
    _player.setTranslateX(Character.STAR_XY);
    _player.setTranslateY(Character.STAR_XY);
    _ghost1.setTranslateX(XGHOST1);
    _ghost1.setTranslateY(YGHOST1);
    _ghost2.setTranslateX(XGHOST2);
    _ghost2.setTranslateY(YGHOST2);
    root1.getChildren().addAll(_player, _ghost1, _ghost2, _scoreLabel, _taskbar, _idLabel);
    Ghost.lose = false;
    Character.win = false;
  }

  /** Class MenuItem */
  private static class MenuItem extends StackPane {
    public MenuItem(String name) {
      /** Draw a rectangle */
      Rectangle bg = new Rectangle(RECTANGLESIZE1, RECTANGLESIZE2, Color.BLUE);
      bg.setOpacity(0.7);
      Text text = new Text(name);
      text.setFill(Color.WHITE);
      text.setFont(Font.font("Arial", FontWeight.BOLD, SMALLLETTERSSIZE));
      setAlignment(Pos.CENTER);
      getChildren().addAll(bg, text);
      FillTransition st = new FillTransition(Duration.seconds(0.5), bg);
      /** Animation when hovering the mouse */
      setOnMouseEntered(event -> {
        st.setFromValue(Color.YELLOW);
        st.setToValue(Color.DARKBLUE);
        st.setCycleCount(Animation.INDEFINITE);
        st.setAutoReverse(true);
        st.play();
      });
      /** Exit */
      setOnMouseExited(event -> {
        st.stop();
        bg.setFill(Color.BLUE);
      });
    }
  }
  /** Class MenuBox */
  private static class MenuBox extends Pane {
    static SubMenu subMenu;

    public MenuBox(SubMenu subMenu) {
      MenuBox.subMenu = subMenu;
      setVisible(false);
      /** Draw a rectangle */
      Rectangle bg = new Rectangle(size, size, Color.LIGHTBLUE);
      bg.setOpacity(0.5);
      getChildren().addAll(bg, subMenu);
    }

    /** Set SubMenu */
    public void setSubMenu(SubMenu subMenu) {
      getChildren().remove(MenuBox.subMenu);
      MenuBox.subMenu = subMenu;
      getChildren().add(MenuBox.subMenu);
    }
  }
  /** Class SubMenu */
  private static class SubMenu extends VBox {
    public SubMenu(MenuItem... items) {
      setSpacing(15);
      setTranslateY(SUBMENUY);
      setTranslateX(SUBMENUX);
      for (MenuItem item : items) {
        getChildren().addAll(item);
      }
    }
  }
}
