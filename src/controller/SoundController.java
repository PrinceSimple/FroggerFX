package controller;

import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.net.URL;
import java.util.Random;

public class SoundController {
    Media bgMedia;
    MediaPlayer bgMusic;
    AudioClip killSnd;
    AudioClip levelupSnd;
    AudioClip homeSnd;
    AudioClip gameOverSnd;
    AudioClip jumpSnd;
    URL[] bgMusicChoice = {
            getClass().getResource("../assets/01.mp3"),
            getClass().getResource("../assets/02.mp3"),
            getClass().getResource("../assets/03.mp3"),
            getClass().getResource("../assets/04.mp3"),
            getClass().getResource("../assets/05.mp3")
    };
    public SoundController() {
        bgMedia = new Media(bgMusicChoice[randomIntInRange(0,4)].toString());
        bgMusic = new MediaPlayer(bgMedia);
        bgMusic.setCycleCount(AudioClip.INDEFINITE);
        killSnd =new AudioClip("file:src/assets/kill.wav");
        levelupSnd =new AudioClip("file:src/assets/levelup.wav");
        homeSnd =new AudioClip("file:src/assets/home.wav");
        gameOverSnd =new AudioClip("file:src/assets/game_over.wav");
        jumpSnd =new AudioClip("file:src/assets/jump.wav");
        jumpSnd.setVolume(0.7);
    }

    public void bgMusicPlay() {
        bgMusic.play();
    }

    public void bgMusicPause() {
        bgMusic.pause();
    }

    public void playKillSound(){
        killSnd.play();
    }

    public void playLevelUpSound(){
        levelupSnd.play();
    }

    public void playHomeSound(){
        homeSnd.play();
    }

    public void playGameOverSound(){
        gameOverSnd.play();
    }

    public void playJumpSound(){
        jumpSnd.play();
    }

    public void anotherRandomTrack(){
        bgMusic.stop();
        bgMusic.dispose();
        bgMedia = new Media(bgMusicChoice[randomIntInRange(0,4)].toString());
        bgMusic = new MediaPlayer(bgMedia);
        bgMusic.setCycleCount(AudioClip.INDEFINITE);
        bgMusic.play();
    }

    private int randomIntInRange(int min, int max) {
        Random rdm = new Random();
        return rdm.nextInt(max - min) + min;
    }
}