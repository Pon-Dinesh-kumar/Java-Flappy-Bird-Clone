import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Objects;

public class Endgame extends JPanel implements ActionListener, KeyListener {

    // Game board dimensions
    private static final int BOARD_WIDTH = 460;
    private static final int BOARD_HEIGHT = 720;

    // Images for game elements
    private final Image background;
    private final Image upperEnemyImage;
    private final Image lowerEnemyImage;

    // Hero properties
    private final Hero hero;
    private final int heroStartY = BOARD_HEIGHT / 2;

    // Enemy properties
    private final ArrayList<Enemy> enemies;

    // Game variables
    private final Timer gameLoop;
    private final Timer placeEnemyTimer;
    private boolean gameOver = false;
    private double score = 0;
    private final Timer timeTracker;
    private int timePassedSeconds = 0;

    // Physics variables
    private int velocityY = 0;
    private boolean strt = false;

    // Constructor
    public Endgame() {
        setPreferredSize(new Dimension(BOARD_WIDTH, BOARD_HEIGHT));
        setBackground(Color.BLACK);
        setFocusable(true);
        addKeyListener(this);

        // Load images
        background = new ImageIcon(Objects.requireNonNull(getClass().getResource("./l15.gif"))).getImage();
        Image heroImage = new ImageIcon(Objects.requireNonNull(getClass().getResource("/thor.gif"))).getImage();
        Image heroOnClick = new ImageIcon(Objects.requireNonNull(getClass().getResource("/thorClick.gif"))).getImage();
        upperEnemyImage = new ImageIcon(Objects.requireNonNull(getClass().getResource("/upperEnemy.png"))).getImage();
        lowerEnemyImage = new ImageIcon(Objects.requireNonNull(getClass().getResource("/lowerEnemy.png"))).getImage();

        // Initialize hero
        int heroStartX = BOARD_WIDTH / 8;
        int heroWidth = 60;
        int heroHeight = 60;
        hero = new Hero(heroStartX, heroStartY, heroWidth, heroHeight, heroImage, heroOnClick);

        // Initialize enemies
        enemies = new ArrayList<>();

        // Timer for placing enemies
        placeEnemyTimer = new Timer(1500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                placeEnemy();
            }
        });
        placeEnemyTimer.start();

        // Game loop timer
        gameLoop = new Timer(1000 / 60, this);
        gameLoop.start();
        // Timer for tracking time passed
        timeTracker = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                timePassedSeconds++;
            }
        });
        timeTracker.start();
    }

    // Paint method for drawing graphics
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    // Draw method to render game elements
    private void draw(Graphics g) {
        // Draw background

        g.drawImage(background, -timePassedSeconds, 0, background.getWidth(null), background.getHeight(null), null);

        if(strt){
            g.drawImage(hero.getClickImage(), hero.getX(), hero.getY(), hero.getWidth(), hero.getHeight(), null);
            strt = false;
        }
        else {
            g.drawImage(hero.getImage(), hero.getX(), hero.getY(), hero.getWidth(), hero.getHeight(), null);
        }
        // Draw hero

        // Draw enemies
        for (Enemy enemy : enemies) {
            g.drawImage(enemy.getImage(), enemy.getX(), enemy.getY(), enemy.getWidth(), enemy.getHeight(), null);
        }

        // Draw score
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.PLAIN, 32));
        if (gameOver) {
            g.drawString("GAME OVER: " + (int) score, 10, 35);
        } else {
            g.drawString(String.valueOf((int) score), 10, 35);
        }
    }

    // ActionListener method for game loop
    @Override
    public void actionPerformed(ActionEvent e) {
        move();
        repaint();
        if (gameOver) {
            timeTracker.stop();
            placeEnemyTimer.stop();
            gameLoop.stop();
        }
    }

    // KeyListener methods
    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            velocityY = -9;
            strt = true;
        }
        if (gameOver && e.getKeyCode() == KeyEvent.VK_ENTER) {
            hero.setY(heroStartY);
            velocityY = 0;
            enemies.clear();
            score = 0;
            gameOver = false;
            gameLoop.start();
            timeTracker.start();
            timePassedSeconds=0;
            placeEnemyTimer.start();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    // Move method to update game elements' positions
    private void move() {
        int gravity = 1;
        velocityY += gravity;
        hero.setY(hero.getY() + velocityY);
        hero.setY(Math.max(hero.getY(), 0));

        // Move enemies and check collisions
        for (Enemy enemy : enemies) {
            int velocityX = -4;
            enemy.setX(enemy.getX() + velocityX);

            // Check if hero has passed an enemy
            if (!enemy.isPassed() && hero.getX() > enemy.getX() + enemy.getWidth()) {
                enemy.setPassed();
                score += 0.5;
            }

            // Check collision with hero
            if (collision(hero, enemy)) {
                gameOver = true;
            }
        }

        // Check if hero falls out of the board
        if (hero.getY() > BOARD_HEIGHT) {
            gameOver = true;
        }
    }

    // Method to place new enemy pairs
    private void placeEnemy() {
        int enemyStartY = 0;
        int enemyHeight = 512;
        int randomEnemyY = (int) (enemyStartY - enemyHeight / 4 - Math.random() * (enemyHeight / 2));
        int openingSpace = (BOARD_HEIGHT / 4);

        // Upper enemy
        int enemyStartX = BOARD_WIDTH;
        int enemyWidth = 64;
        Enemy topEnemy = new Enemy(enemyStartX, randomEnemyY, enemyWidth, enemyHeight, upperEnemyImage);
        enemies.add(topEnemy);

        // Lower enemy
        Enemy bottomEnemy = new Enemy(enemyStartX, topEnemy.getY() + enemyHeight + openingSpace, enemyWidth, enemyHeight, lowerEnemyImage);
        enemies.add(bottomEnemy);
    }

    // Method to check collision between hero and enemy
    private boolean collision(Hero h, Enemy e) {
        return h.getX() + 10 < e.getX() + e.getWidth() &&
                h.getX() + h.getWidth() -10 > e.getX() &&
                h.getY() +10 < e.getY() + e.getHeight() &&
                h.getY() + h.getHeight() - 10> e.getY();
    }
}
