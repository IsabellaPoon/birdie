import java.awt.*;

public class Projectile {
    private int x;
    private int y;

    private int width = 35;
    private int height = 25;
    private Image image;

    public Projectile(Image img) { this.image = img; }

    // Getters and Setters for position, velocity, width, height, and image

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }


    public Image getImage() {
        return image;
    }

    public void incrementX(int x) {
        this.x = this.x + x;
    }

    public void move(int x) {
        this.x = this.x + x;
    }
    @Override
    public boolean equals(Object obj) {
        return obj.toString().equals(this.toString());
    }
    @Override
    public String toString() {
        return "the width of the projectile is: "+ width;
    }
    }

