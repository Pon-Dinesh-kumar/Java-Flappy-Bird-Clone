import java.awt.*;

class Enemy {
    private int x;
    private final int y;
    private final int width;
    private final int height;
    private final Image image;
    private boolean passed;

    Enemy(int x, int y, int width, int height, Image image) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.image = image;
        this.passed = false;
    }

    int getX() {
        return x;
    }

    int getY() {
        return y;
    }

    int getWidth() {
        return width;
    }

    int getHeight() {
        return height;
    }

    Image getImage() {
        return image;
    }

    boolean isPassed() {
        return passed;
    }

    void setPassed() {
        this.passed = true;
    }

    public void setX(int i) {
        this.x = i;
    }
}