import java.awt.*;

class Hero {
    private final int x;
    private int y;
    private final int width;
    private final int height;
    private final Image image;
    private final Image clickImage;

    Hero(int x, int y, int width, int height, Image image, Image clickImage) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.image = image;
        this.clickImage = clickImage;
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

    void setY(int y) {
        this.y = y;
    }

    public Image getClickImage() {
        return this.clickImage;
    }
}