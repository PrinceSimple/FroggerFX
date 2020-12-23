package model;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class Row {
    private GraphicsContext ctx;
    public Obstacle[] obstacles;
    private int y;
    private static int H = 50;
    private boolean isWater = false;
    //private int leftToRight = 1;

    public Row(int id, Color bgColor, Canvas backgroundCanvas, int obstacleCount, int obstacleWidth, int spacing, int offset, double speed, boolean backwards, String obstacleImgPath) {
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
            this.obstacles[i] = new Obstacle(temp_x, id * H, obstacleWidth, speed, (id < 7) ? false : true, obstacleImgPath);
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

    public int getY() {
        return y;
    }

    public boolean isWater() {
        return isWater;
    }

    /*public static class RowBuilder {

        private String name;
        private String accountNumber;
        private String email;
        private boolean newsletter;

        public RowBuilder(String name, String accountNumber) {
            this.name = name;
            this.accountNumber = accountNumber;
        }

        public RowBuilder withEmail(String email) {
            this.email = email;
            return this;
        }

        public RowBuilder wantNewsletter(boolean newsletter) {
            this.newsletter = newsletter;
            return this;
        }

        public Row build() {
            return new Row(this);
        }
    }*/
}
