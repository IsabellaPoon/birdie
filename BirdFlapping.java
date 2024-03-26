import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;

public class BirdFlapping extends JPanel implements ActionListener, KeyListener {
    private final int boardWidth = 640;
    private final int boardHeight = 640;

    private ArrayList<Projectile> projectiles;
    private ArrayList<PowerUp> powerUps;

    private static final double luckySpawnProbability = 0.1  ;

    private int pipeCount = 0;

    private int velocityY = 0;
    private int velocityX = -4;
    private int gravity = 1;
    private int randomPipeY;
    private int space;

    //change image in subclass
    private Image fireImg;

    private Image powerUpImg;
    private Image birdImg;

    private Image fireBirdImg;
    private Image topPipeImg;
    private Image bottomPipeImg;
    private Image backgroundImg;


    private Bird bird;
    private Bird fireBird;
    private Pipe piper;
    private Pipe pipey;

    private PowerUp powerup;
    private ArrayList<Pipe> pipes;
    private Timer loop;
    private Timer pipeLoop;
    private boolean gameOver = false;
    private double score = 0;

    public BirdFlapping(){
        setPreferredSize(new Dimension(boardWidth, boardHeight));
//        setBackground(Color.blue);
        setFocusable(true);
        addKeyListener(this);
        backgroundImg = new ImageIcon(getClass().getResource("./flappybirdbg.png")).getImage();
        topPipeImg = new ImageIcon(getClass().getResource("./toppipe.png")).getImage();
        bottomPipeImg = new ImageIcon(getClass().getResource("./bottompipe.png")).getImage();
        fireImg = new ImageIcon(getClass().getResource("./fire.png")).getImage();
        powerUpImg = new ImageIcon(getClass().getResource("./powerup.png")).getImage();


        //make image in sublcass
        fireBirdImg = new ImageIcon(getClass().getResource("./smallbird.png")).getImage();
        birdImg = new ImageIcon(getClass().getResource("./flappybird.png")).getImage();

        //inheritance here
        bird = new Bird(birdImg);
        fireBird = new Bird(fireBirdImg);


        projectiles = new ArrayList<>();
        powerUps = new ArrayList<PowerUp>();
        pipes = new ArrayList<Pipe>();

        pipeLoop = new Timer(1500, new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){

                placePipes();
            }
        });

        pipeLoop.start();

        loop = new Timer(1000/60, this);
        loop.start();
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);

    }

    public void draw(Graphics g) {
        g.drawImage(backgroundImg, 0, 0, boardWidth, boardHeight, null);

//        g.drawImage(fireBird.getImg(), bird.getX(), bird.getY(), bird.getWidth(), bird.getHeight(), null);
        g.drawImage(bird.getImg(), bird.getX(), bird.getY(), 34, 24, null);

        for (Pipe pipe : pipes) {
            g.drawImage(pipe.getImg(), pipe.getX(), pipe.getY(), pipe.getWidth(), pipe.getHeight(), null);
        }

        for (Projectile projectile : projectiles) {
            g.drawImage(fireImg, projectile.getX(), projectile.getY(), projectile.getWidth(), projectile.getHeight(), null);
        }

        for (PowerUp powerUp : powerUps) {
            g.drawImage(powerUpImg, powerUp.getX(), powerUp.getY(), 35, 30, null);
        }


        g.setColor(Color.white);
        g.setFont(new Font("Arial", Font.PLAIN, 50));
        if (!gameOver) {
            g.drawString(String.valueOf((int) score), 10, 45);
        } else {
            drawGameOverScreen(g);
        }
    }


    public void move(){
        velocityY +=gravity;
        bird.setY(velocityY);

        for (Projectile projectile : projectiles) {
            projectile.move();
        }


        for (Pipe pipe : pipes) {
            pipe.setX(velocityX);

            if (!pipe.getPassed() && bird.getX() > pipe.getX() + pipe.getWidth()) {
                pipe.setPassed();
                score += 0.5;
            }
            if (collides(bird, pipe)) {
                gameOver = true;
            }
        }

        for (PowerUp powerup : powerUps) {
            powerup.setX(velocityX);
            if (collideswithpower(bird, powerup)) {
                System.out.println("yum");
            }
        }

        // ADD MOVE FOR POWERUPS
        //
        //

        if (bird.getY() > boardHeight){
            gameOver = true;
        }

    }

    public boolean collideswithpower(Bird a, PowerUp p){
        return a.getX()<p.getX() + p.getWidth() &&
                a.getX() + a.getWidth()> p.getX() &&
                a.getY()<p.getY() + p.getHeight() &&
                a.getY() + a.getHeight()> p.getY();
    }
    public boolean collides(Bird a, Pipe b){
        return a.getX()<b.getX() + b.getWidth() &&
                a.getX() + a.getWidth()> b.getX() &&
                a.getY()<b.getY() + b.getHeight() &&
                a.getY() + a.getHeight()> b.getY();
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        move();
        repaint();
        if (gameOver){
            pipeLoop.stop();
            loop.stop();
        }
    }


    public void placePipes() {
        // Increment the pipe count
        pipeCount++;

        randomPipeY = (int) (0 - 512 / 4 - Math.random() * (512 / 2));
        space = boardHeight / 4;

        // piper top; pipey bottom
        piper = new Pipe(topPipeImg);
        piper.setY(randomPipeY);
        pipes.add(piper);

        pipey = new Pipe(bottomPipeImg);
        pipey.setY(piper.getY() + piper.getHeight() + space);
        pipes.add(pipey);

        // Check if it's time to spawn a power-up based on the number of pipes spawned
        if (pipeCount % 20 == 0 || Math.random() < luckySpawnProbability) {
            // Calculate the middle Y position of the gap
            int middleY = (piper.getY() + pipey.getY()) / 2;

            // creates the range for powerUp Y posit
            int minY = Math.max(piper.getY() + piper.getHeight(), 0); // Ensure it's below the top pipe
            int maxY = Math.min(pipey.getY(), boardHeight - powerUpImg.getHeight(null)); // Ensure it's above the bottom pipe

            // creates a random Y position in range
            int powerUpY = (int) (minY + Math.random() * (maxY - minY));

            int powerUpX = piper.getX();


            System.out.println("Power-up spawned at (" + powerUpX + ", " + powerUpY + ")");
            PowerUp powerUp = new PowerUp(powerUpX, powerUpY, powerUpImg.getWidth(null), powerUpImg.getHeight(null), powerUpImg);
            powerUp.setX(powerUpX);
            powerUp.setY(powerUpY);
            powerUps.add(powerUp);

        }
    }



    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE){
            velocityY= -9;
            if(gameOver){
                bird.resetY();
                velocityY = 0;
                pipes.clear();
                score = 0;
                gameOver = false;
                loop.start();
                pipeLoop.start();

            }
        }
        if (e.getKeyCode() == KeyEvent.VK_E) {
            int width = fireImg.getWidth(null);
            int height = fireImg.getHeight(null);
            // Create a new projectile at bird's position, moving to the right
            int projectileVelocityX = 5;
            int projectileVelocityY = 0;
            Projectile newProjectile = new Projectile(bird.getX(), bird.getY(),
                    projectileVelocityX, projectileVelocityY,
                     width, height, fireImg);
            projectiles.add(newProjectile);
        }
    }

    public void drawGameOverScreen(Graphics g) {
        g.setColor(new Color(0, 0, 0, 175));
        g.fillRect(0, 0, boardWidth, boardHeight);
        Font font = new Font("Serif", Font.BOLD, 90);
        FontMetrics metrics = g.getFontMetrics(font);
        int x = (boardWidth - metrics.stringWidth("Game Over: " + String.valueOf((int) score))) / 2;
        int y = ((boardHeight - metrics.getHeight()) / 3) + metrics.getAscent();
        g.setFont(font);
        g.setColor(Color.black);
        g.drawString("Game Over: " + String.valueOf((int) score), x, y);
        g.setColor(Color.white);
        g.drawString("Game Over: " + String.valueOf((int) score), x - 4, y - 4);
    }

    @Override
    public void keyTyped(KeyEvent e) {    }
    @Override
    public void keyReleased(KeyEvent e) {}
}