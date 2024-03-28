import java.awt.*;

public class PowerUp {

    private int x = 640;
    private int y = 0;
    private int width = 45;
    private int height = 40;
    private Image img;

    private boolean collided = false;

    public boolean hasCollided() {
        return collided;
    }
    public void setCollided(boolean collided) {
        this.collided = collided;
    }
    public PowerUp(Image img) { this.img = img; }

    public int getX() {
        return x;
    }

    public void incrementX(int x) {
        this.x = this.x + x;
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
        return img;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        PowerUp powerUp = (PowerUp) obj;
        return this.toString().equals(powerUp.toString());
    }

    @Override
    public String toString() {
        return "PowerUp position: (" + x + ", " + y + ")";
    }
}