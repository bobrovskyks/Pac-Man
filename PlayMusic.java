package application;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

/** Ñlass PlayMusic */
public class PlayMusic {
  private MediaPlayer mediaPlayer;

  public PlayMusic(String musicName) {
    Media media = new Media(getClass().getResource(musicName).toString());
    mediaPlayer = new MediaPlayer(media);
  }

  /** The method of incorporating songs */
  public void playMusic() {
    try {
      mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
      mediaPlayer.play();
    } catch (Exception e) {
      System.out.println("Error");
      return;
    }
  }

  /** Songs off method. */
  public void stopMusic() {
    try {
      mediaPlayer.stop();
    } catch (Exception e) {
      System.out.println("Error");
      return;
    }
  }
}
