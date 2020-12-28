package model;

public class HomeSlot {
    private int id;
    private boolean occupied = false;
    public HomeSlot(int id, float x, float y) {
        this.id = id;
    }

    public boolean isOccupied() {
        return occupied;
    }

    public void setOccupied(boolean occupied) {
        this.occupied = occupied;
    }
}
