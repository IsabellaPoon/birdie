import java.awt.*;

public class Projectile {
    public int setX;
    private int x;
    private int y;
    private int velocityX;
    private int velocityY;
    private int width = 35;
    private int height = 25;
    private Image image;

    private boolean isPowerUp;

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

    public int getVelocityX() {
        return velocityX;
    }

    public void setVelocityX(int velocityX) {
        this.velocityX = velocityX;
    }

    public int getVelocityY() {
        return velocityY;
    }

    public void setVelocityY(int velocityY) {
        this.velocityY = velocityY;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public boolean isPowerUp() {
        return isPowerUp;
    }

    public void incrementX(int x) {
        this.x = this.x + x;
    }

    public void move(int x) {
        this.x = this.x + x;
    }
}
