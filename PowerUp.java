import java.awt.*;

public class PowerUp {
    private int x = 640;
    private int y = 0;
    private int width = 45;
    private int height = 40;
    private Image img;


    public PowerUp(Image img) {
        this.img = img;
//    public PowerUp(int x, int y, int width, int height, Image img) {
//        this.x = x;
//        this.y = y;
//        this.img = img;
//    }
    }
    public int getX() {
        return x;
    }

    public void setX(int x) {
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


}