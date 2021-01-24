package model;

import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;

import java.util.Random;

public class RowBuilder {

    private final int RowId;
    private String imgPath = "";
    private int obstacleCount = 0;
    private int obstacleWidth = 0;
    private int spacing = 0;
    private int offset = 0;
    private double speed = 0;
    private final Color bgColor;
    private final Canvas bgCanvas;
    boolean backwards = fiftyFifty();

    private boolean fiftyFifty() {
        Random rdm = new Random();
        return rdm.nextBoolean();
    }

    public RowBuilder(int id, int level, Canvas backgroundCanvas) {
        this.RowId = id;
        this.bgCanvas = backgroundCanvas;
        String[] cars = {
                "assets/Car_1.png",
                "assets/Car_2.png",
                "assets/Car_3.png",
                "assets/Car_4.png"
        };
        Color[] bgColors = {
                Color.web("99FF99"),
                Color.web("99FFFF"),
                Color.web("D6FF99"),
                Color.web("1f261f")
        };
        switch(id) {
            case 1:
                bgColor = bgColors[0];
                obstacleCount = 5;
                obstacleWidth = 50;
                spacing = 140;
                offset = 75;
                imgPath = "assets/home.png";
                break;
            case 2: case 4:
            case 5:
                bgColor = bgColors[1];
                obstacleCount = randomIntInRange(3,4);
                obstacleWidth = 230;
                spacing = randomIntInRange(300, 400);
                offset = randomIntInRange(230, 400);
                speed = randomSpeedInRange(1, 2);
                imgPath = "assets/Hog_3.png";
                break;
            case 3: case 6:
                bgColor = bgColors[1];
                obstacleCount = randomIntInRange(2,4);
                obstacleWidth = 175;
                spacing = randomIntInRange(200, 400);
                offset = randomIntInRange(50, 150);
                speed = randomSpeedInRange(1, 2.3);
                imgPath = "assets/turtle_1.png";
                break;
            case 8: case 9:
            case 10: case 11:
            case 12: case 13:
                bgColor = bgColors[3];
                obstacleCount = randomIntInRange(4,8);
                obstacleWidth = 50;
                spacing = randomIntInRange(50, 200);
                offset = randomIntInRange(50, 400);
                speed = randomSpeedInRange(0.8, 3);
                imgPath = cars[randomIntInRange(0,3)];
                break;
            default:
                obstacleCount = 0;
                bgColor = bgColors[0];
                imgPath = "";
                break;

        }
    }

    private double randomSpeedInRange(double min, double max) {
        Random rdm = new Random();
        return (max - min) + min * rdm.nextDouble();
    }
    private int randomIntInRange(int min, int max) {
        Random rdm = new Random();
        return rdm.nextInt(max - min) + min;
    }

    public Row build() {
        return new Row(RowId, bgColor, bgCanvas, obstacleCount, obstacleWidth, spacing, offset, speed, backwards, imgPath);
    }
}