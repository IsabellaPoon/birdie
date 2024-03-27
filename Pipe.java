import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;

public class Pipe {
   private int boardWidth = 640;
   private int boardHeight = 640;


    private int pipeX = boardWidth;
    private int pipeY= 0;
    private int pipeWidth = 64;
    private int pipeHeight = 512;
    private Image img;
    private boolean passed = false;

    public Pipe(Image img){
        this.img = img;
    }

    public int getX() {
        return pipeX;
    }

    public int getY(){
        return pipeY;
    }

    public int getWidth(){
        return pipeWidth;
    }

    public int getHeight(){
        return pipeHeight;
    }

    public void setX(int i){
        pipeX+=i;
    }

    public boolean getPassed(){
        return passed;
    }

    public void setPassed(){
        passed = true;
    }
    public Image getImg(){
        return this.img;
    }

    public void setY(int i){
        pipeY = i;
    }
}