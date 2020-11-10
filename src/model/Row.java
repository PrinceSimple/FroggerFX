package model;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class Row {
    private GraphicsContext ctx;
    public Obstacle[] obstacles;
    //private int leftToRight = 1;

    public Row(int id, Color bgColor, Canvas backgroundCanvas, int obstacleCount, int obstacleWidth, int spacing, int offset, double speed, boolean inverted) {
        ctx = backgroundCanvas.getGraphicsContext2D();
        ctx.setFill(bgColor);
        ctx.fillRect(0, id*50, 750, 50);
       // this.leftToRight = leftToRight;
        this.obstacles = new Obstacle[obstacleCount];
        if (inverted) {
            speed=-speed;
        }
        for(int i = 0; i < obstacleCount; i++) {
            int temp_x = i * spacing + offset;
            this.obstacles[i] = new Obstacle(temp_x, id * 50, obstacleWidth, speed);
        }
    }
}
