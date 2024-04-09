import java.awt.*;

public class Piece {
    private static int nextNumber = 1; // static field to keep track of the next number to assign
    private int x, y, number;
    private Color color;
    private boolean isVisible;

    public Piece(int x, int y, Color color) {
        this.x = x;
        this.y = y;
        this.color = color;
        this.isVisible = true;
        this.number = nextNumber++; // assign the next number and increment it
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Color getColor() {
        return color;
    }

    public boolean isVisible() {
        return isVisible;
    }

    public void setVisible(boolean visible) {
        isVisible = visible;
    }

    public void move(int newX, int newY) {
        this.x = newX;
        this.y = newY;
    }

    public int getNumber() {
        return number;
    }
}