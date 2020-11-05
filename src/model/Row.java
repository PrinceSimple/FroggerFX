package model;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Row {
    private GraphicsContext ctx;

    public Row(int x, int y, int w, int h, Color c, Canvas cv) {
        ctx = cv.getGraphicsContext2D();
        ctx.setFill(c);
        ctx.fillRect(x, y, w, h);
    }
}
