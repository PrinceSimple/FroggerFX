package model;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Row {
    private final GraphicsContext ctx;
    public Obstacle[] obstacles;
    public HomeSlot[] homes;
    private final int id;
    private final int y;
    private static int H = 50;
    private boolean isWater = false;

    public Row(int id, Color bgColor, Canvas backgroundCanvas, int obstacleCount, int obstacleWidth, int spacing, int offset, double speed, boolean backwards, String obstacleImgPath) {
        this.id = id;
        this.y = id * H;
        ctx = backgroundCanvas.getGraphicsContext2D();
        ctx.setFill(bgColor);
        ctx.fillRect(0, id*H, 750, H);
        this.obstacles = new Obstacle[obstacleCount];
        this.isWater = id < 7 ? true : false;
        if (backwards) {
            speed=-speed;
        }
        for(int i = 0; i < obstacleCount; i++) {
            int temp_x = i * spacing + offset;
            this.obstacles[i] = new Obstacle(temp_x, id * H, obstacleWidth, speed, id > 7, obstacleImgPath);
        }
    }

    public Row(int id, Color bgColor, Canvas backgroundCanvas) {
        this.id = id;
        this.y = id * H;
        ctx = backgroundCanvas.getGraphicsContext2D();
        ctx.setFill(bgColor);
        ctx.fillRect(0, id*H, 750, H);
        this.homes = new HomeSlot[5];
        this.isWater = id < 7 ? true : false;
        for(int i = 0; i < 5; i++) {
            int temp_x = i * 140 + 75;
            this.homes[i] = new HomeSlot(i, temp_x, 50);
        }
    }

    public boolean intersects(Player p) {
        return this.y == (int) p.getY();
    }

    public void update(){
        for (Obstacle o: this.obstacles) {
            o.update();
        }
    }

    public int getId() {
        return id;
    }

    public boolean isWater() {
        return isWater;
    }

}
