package controller;

import javafx.scene.media.AudioClip;

public class SoundController implements AutoCloseable{
    AudioClip bgMusic;
    public SoundController(String file) {
        bgMusic =new AudioClip(SoundController.class.getResource(file).toExternalForm());
        bgMusic.setCycleCount(AudioClip.INDEFINITE);
    }

    public void play() {
        bgMusic.play();
    }

    public void pause() {
        bgMusic.stop();
    }

    public void close()  {
        bgMusic.stop();
    }

    public void resume(){
        bgMusic.play();
    }

    public void suspend(){
        bgMusic.stop();
    }
}