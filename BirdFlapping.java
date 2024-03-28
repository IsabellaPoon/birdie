
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.*;
import javax.swing.Timer;


public class BirdFlapping extends JPanel implements ActionListener, KeyListener {
    private final long minShotInterval = 5000;
    private long lastShotTime = 0;
    private final int boardWidth = 640;
    private final int boardHeight = 640;
    private ArrayList<Projectile> projectiles;
    private ArrayList<PowerUp> powerUps;

    private Timer invincibilityTimer;

    private static final double luckySpawnProbability = 1;

    private int pipeCount = 0;
    private Image currentBirdImage;


    private enum PowerUpType {
        NONE, FIRE, STAR
    }

    private PowerUpType currentPowerUp = PowerUpType.NONE;


    private int velocityY = 0;
    private int velocitydecreaseX = -4;

    private int gravity = 1;
    private int randomPipeY;
    private int space;

    // IMAGES//
    private Image fireImg;
    private Image powerUpImg;
    private Image birdImg;
    private Image fireBirdImg;
    private Image topPipeImg;
    private Image bottomPipeImg;
    private Image backgroundImg;
    private Image starBirdImg;

    private Bird bird;
    private Pipe piper;
    private Pipe pipey;

    private PowerUp powerup;
    private ArrayList<Pipe> pipes;
    private Timer loop;
    private Timer pipeLoop;
    private Timer resetBirdTimer;
    private int countdownSeconds = 15;
    private boolean gameOver = false;
    private double score = 0;


    public BirdFlapping() {
        setPreferredSize(new Dimension(boardWidth, boardHeight));
        setFocusable(true);
        addKeyListener(this);

        // created images
        backgroundImg = new ImageIcon(getClass().getResource("./flappybirdbg.png")).getImage();
        topPipeImg = new ImageIcon(getClass().getResource("./toppipe.png")).getImage();
        bottomPipeImg = new ImageIcon(getClass().getResource("./bottompipe.png")).getImage();
        fireImg = new ImageIcon(getClass().getResource("./fire.png")).getImage();
        powerUpImg = new ImageIcon(getClass().getResource("./powerup.png")).getImage();
        fireBirdImg = new ImageIcon(getClass().getResource("./firebird.png")).getImage();
        birdImg = new ImageIcon(getClass().getResource("./bird.png")).getImage();
        starBirdImg = new ImageIcon(getClass().getResource("./starbird.png")).getImage();
        currentBirdImage = birdImg; // Set initial image

        // inheritance here
        bird = new Bird(birdImg);
        projectiles = new ArrayList<>();
        powerUps = new ArrayList<>();
        pipes = new ArrayList<>();


        resetBirdTimer = new Timer(1000, new ActionListener() { // Timer ticks every second
            @Override
            public void actionPerformed(ActionEvent e) {
                countdownSeconds--; // Decrease countdown seconds
                if (countdownSeconds <= 0) {
                    currentPowerUp = PowerUpType.NONE;
                    updateBirdImage();
                    repaint();
                    resetBirdTimer.stop(); // Stop the timer after resetting the bird image
                }else {
                    repaint();
                }
            }
        });
        pipeLoop = new Timer(1500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                placePipes();
            }
        });


        pipeLoop.start();

        loop = new Timer(1000 / 60, this);
        loop.start();
    }
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
        if (currentPowerUp != PowerUpType.NONE) {
            drawCountdownTimer(g);
        }
    }

    public void moveProjectiles() {
        Iterator<Projectile> iterator = projectiles.iterator();
        while (iterator.hasNext()) {
            Projectile projectile = iterator.next();
            projectile.move(3); // Move the projectile

            // Check collision with pipes
            Iterator<Pipe> pipeIterator = pipes.iterator();
            while (pipeIterator.hasNext()) {
                Pipe pipe = pipeIterator.next();
                if (collideswfire(projectile, pipe)) {
                    // Remove the projectile and pipe if collision detected
                    iterator.remove();
                    pipeIterator.remove();
                    break;
                }
            }
        }
    }
    public void draw(Graphics g) {
        g.drawImage(backgroundImg, 0, 0, boardWidth, boardHeight, null);

        g.drawImage(currentBirdImage, bird.getX(), bird.getY(), bird.getWidth(), bird.getHeight(), null);

        for (Pipe pipe : pipes) {
            g.drawImage(pipe.getImg(), pipe.getX(), pipe.getY(), pipe.getWidth(), pipe.getHeight(), null);
        }

        for (PowerUp powerUp : powerUps) {
            g.drawImage(powerUp.getImage(), powerUp.getX(), powerUp.getY(), powerUp.getWidth(), powerUp.getHeight(),
                    null);
        }

        for (Projectile projectile : projectiles) {
            g.drawImage(projectile.getImage(), projectile.getX(), projectile.getY(),
                    projectile.getWidth(), projectile.getHeight(), null);
        }

        g.setColor(Color.white);
        g.setFont(new Font("Arial", Font.PLAIN, 50));
        if (!gameOver) {
            g.drawString(String.valueOf((int) score), 10, 45);
        } else {
            drawGameOverScreen(g);
        }
    }


    public void move() {
        velocityY += gravity;
        bird.setY(velocityY);
        for (Pipe pipe : pipes) {
            pipe.incrementX(velocitydecreaseX);
            if (!pipe.getPassed() && bird.getX() > pipe.getX() + pipe.getWidth()) {
                pipe.setPassed();
                score += 0.5;
            }
            if (collides(bird, pipe)) {
                gameOver = true;
            }
        }
        for (PowerUp powerup : powerUps) {
            powerup.incrementX(velocitydecreaseX);
            if (collideswithpower(bird, powerup)) {
                randomPower();
                powerup.setCollided(true);
            }
        }
        for(Projectile projectile : projectiles){
            projectile.incrementX(2);
        }

        if (bird.getY() > boardHeight) {
            gameOver = true;
        }
    }
    public boolean collideswithpower(Bird a, PowerUp p) {
        return !p.hasCollided() &&
                a.getX() < p.getX() + p.getWidth() &&
                a.getX() + a.getWidth() > p.getX() &&
                a.getY() < p.getY() + p.getHeight() &&
                a.getY() + a.getHeight() > p.getY();
    }
    public boolean collideswfire(Projectile p, Pipe b) {
        return p.getX() < b.getX() + b.getWidth() &&
                p.getX() + p.getWidth() > b.getX() &&
                p.getY() < b.getY() + b.getHeight() &&
                p.getY() + p.getHeight() > b.getY();
    }

    public boolean collides(Bird a, Pipe b) {
        return a.getX() < b.getX() + b.getWidth() &&
                a.getX() + a.getWidth() > b.getX() &&
                a.getY() < b.getY() + b.getHeight() &&
                a.getY() + a.getHeight() > b.getY();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        move();
        moveProjectiles();
        repaint();
        if (currentPowerUp != PowerUpType.NONE) {
            countdownSeconds--; // Decrease countdown seconds
            if (countdownSeconds <= 0) {
                currentPowerUp = PowerUpType.NONE; // Reset the power-up type
                updateBirdImage(); // Update the bird image accordingly
                resetBirdTimer.stop(); // Stop the countdown timer
            }
        }

        if (gameOver) {
            pipeLoop.stop(); // Stop the pipe loop timer
            loop.stop(); // Stop the main game loop timer
            resetBirdTimer.stop(); // Stop the countdown timer
        }
    }
    private void updateBirdImage() {
        switch (currentPowerUp) {
            case FIRE:
                currentBirdImage = fireBirdImg;
                break;
            case STAR:
                currentBirdImage = starBirdImg;
                break;
            default:
                currentBirdImage = birdImg;
                break;
        }
    }

    public void randomPower() {
        countdownSeconds = 150; // Reset the countdown timer to its initial value
        resetBirdTimer.start(); // Start the countdown timer
        double randomValue = Math.random();
        if (randomValue < 0.5) {
            bird.setImage(fireBirdImg);
            currentPowerUp = PowerUpType.FIRE;
        } else {
            bird.setImage(starBirdImg);
            currentPowerUp = PowerUpType.STAR;
        }
        updateBirdImage();
        repaint();
    }

    public void placePipes() {
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
        if ((pipeCount % 20 == 0 || Math.random() < luckySpawnProbability) && currentPowerUp == PowerUpType.NONE) {

            powerup = new PowerUp(powerUpImg);
            // creates the range for powerUp Y posit
            int minY = Math.max(piper.getY() + piper.getHeight(), 0); // Ensure it's below the
                                                                      // top pipe
            int maxY = Math.min(pipey.getY() - powerup.getHeight(), boardHeight); // Ensure
            assert minY < maxY;
            // creates a random Y position in range
            int powerUpY = (int) (minY + Math.random() * (maxY - minY));
            int powerUpX = piper.getX();
            // PowerUp powerUp = new PowerUp(powerUpX, powerUpY, 35, 30, powerUpImg);
            powerup.setY(powerUpY);
            powerup.incrementX(piper.getWidth() / 2 - powerup.getWidth() / 2);
            powerUps.add(powerup);

        }

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            velocityY = -9;
            if (currentPowerUp != PowerUpType.NONE) {
                resetBirdTimer.restart(); // Restart the timer
            }
            if (gameOver) {
                currentPowerUp = PowerUpType.NONE;
                updateBirdImage();
                repaint();
                resetBirdTimer.stop();
                bird.resetY();
                velocityY = 0;
                pipes.clear();
                score = 0;
                gameOver = false;
                loop.start();
                pipeLoop.start();
                powerUps.clear();

            }
        }

        else if (e.getKeyCode() == KeyEvent.VK_E) {
            long currentTime = System.currentTimeMillis();
            if (currentPowerUp == PowerUpType.FIRE && currentTime - lastShotTime >= minShotInterval) {
                // Create a new projectile
                Projectile projectile = new Projectile(fireImg);
                projectile.setX(bird.getX());
                projectile.setY(bird.getY());
                projectiles.add(projectile);

                // Update last shot time
                lastShotTime = currentTime;
            }
        }
    }

    private void drawCountdownTimer(Graphics g) {
        g.setColor(Color.white);
        g.setFont(new Font("Arial", Font.BOLD, 50));
        String countdownString = "Timer: " + countdownSeconds;
        FontMetrics metrics = g.getFontMetrics();
        int x = (getWidth() - metrics.stringWidth(countdownString)) / 2;
        int y = 40;
        g.drawString(countdownString, x, y);
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
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

}
