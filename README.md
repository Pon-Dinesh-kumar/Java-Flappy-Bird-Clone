# Endgame - A Marvel-Themed Flappy Bird Clone 🚀

Welcome to **Endgame**! This project is a fun twist on the classic Flappy Bird game, featuring Marvel characters, stunning backgrounds, and exciting gameplay. Dive into this guide to learn how to clone, build, and play the game, as well as explore future upgrades and implementation ideas.

## Table of Contents
- [Technologies Used](#technologies-used)
- [Game Physics](#game-physics)
- [Assets](#assets)
- [How to Clone and Play](#how-to-clone-and-play)
- [Future Upgrades](#future-upgrades)
- [How to Rebuild the Project](#how-to-rebuild-the-project)
- [Gameplay Functions](#gameplay-functions)
- [Increasing Difficulty](#increasing-difficulty)
- [Screenshots](#screenshots)

## Technologies Used
- **Java**: Core programming language
- **Maven**: Build and dependency management
- **Swing**: GUI framework for building the game's interface

## Game Physics
- **Gravity**: Pulls the hero down
- **Velocity**: Controls the hero's upward and downward movement
- **Collision Detection**: Determines if the hero has hit an enemy

## Assets
- Marvel hero sprites (e.g., Thor, Iron Man)
- Enemy images
- Background images

**Note**: You can get the assets from the `assets` directory in the project repository.

## How to Clone and Play
1. **Clone the Repository**:
    ```sh
    git clone https://github.com/yourusername/Endgame.git
    cd Endgame
    ```

2. **Build the Project**:
    ```sh
    mvn clean install
    ```

3. **Run the Game**:
    ```sh
    java -jar target/Endgame-1.0-SNAPSHOT.jar
    ```

## Future Upgrades
- **Special Power-Ups**: Add abilities like temporary invincibility, speed boost, or double score.
- **Different Locations**: Implement various Marvel Universe locations as backgrounds.
- **Character Selection**: Allow players to choose their favorite Marvel hero.
- **Increased Difficulty**: Gradually increase the game's speed and the number of enemies over time.

### Ideas and Implementation
1. **Special Power-Ups**:
    - **Idea**: Introduce collectible power-ups.
    - **Implementation**:
        - Add new classes for power-ups.
        - Implement collision detection with the hero to activate the power-ups.

2. **Different Locations**:
    - **Idea**: Change backgrounds periodically or based on levels.
    - **Implementation**:
        - Use a timer to switch backgrounds.
        - Load different images and set them as the background.

3. **Character Selection**:
    - **Idea**: Let players select from a roster of heroes.
    - **Implementation**:
        - Create a selection screen.
        - Load the selected character's sprite.

## Technology Versions
- **Java**: 18
- **Maven**: 3.6.3
- **Swing**: Built-in with Java

## How to Rebuild the Project
1. **Set Up the Project Directory**:
    ```sh
    mkdir Endgame
    cd Endgame
    mkdir -p src/main/java/org/example
    mkdir -p src/main/resources
    ```

2. **Create the Maven Project File (`pom.xml`)**:
    ```xml
    <project xmlns="http://maven.apache.org/POM/4.0.0"
             xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
             xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
        <modelVersion>4.0.0</modelVersion>
        <groupId>org.example</groupId>
        <artifactId>Endgame</artifactId>
        <version>1.0-SNAPSHOT</version>
        <properties>
            <maven.compiler.source>18</maven.compiler.source>
            <maven.compiler.target>18</maven.compiler.target>
            <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        </properties>
    </project>
    ```

3. **Create the Main Application File (`App.java`)**:
    ```java
    package org.example;

    import javax.swing.*;

    public class App {
        public static void main(String[] args) {
            int boardWidth = 460;
            int boardHeight = 720;

            JFrame frame = new JFrame("Endgame");
            frame.setVisible(true);
            frame.setSize(boardWidth, boardHeight);
            frame.setLocationRelativeTo(null);
            frame.setResizable(false);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            Endgame endgame = new Endgame();
            frame.add(endgame);
            frame.pack();
            endgame.requestFocus();
            frame.setVisible(true);
        }
    }
    ```

4. **Create the `Hero` Class**:
    ```java
    package org.example;

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

        int getX() { return x; }
        int getY() { return y; }
        int getWidth() { return width; }
        int getHeight() { return height; }
        Image getImage() { return image; }
        void setY(int y) { this.y = y; }
        Image getClickImage() { return this.clickImage; }
    }
    ```

5. **Create the `Enemy` Class**:
    ```java
    package org.example;

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

        int getX() { return x; }
        int getY() { return y; }
        int getWidth() { return width; }
        int getHeight() { return height; }
        Image getImage() { return image; }
        boolean isPassed() { return passed; }
        void setPassed() { this.passed = true; }
        void setX(int i) { this.x = i; }
    }
    ```

6. **Create the `Endgame` Class**:
    ```java
    package org.example;

    import javax.swing.*;
    import java.awt.*;
    import java.awt.event.*;
    import java.util.ArrayList;
    import java.util.Objects;

    public class Endgame extends JPanel implements ActionListener, KeyListener {

        private static final int BOARD_WIDTH = 460;
        private static final int BOARD_HEIGHT = 720;

        private final Image background;
        private final Image upperEnemyImage;
        private final Image lowerEnemyImage;

        private final Hero hero;
        private final int heroStartY = BOARD_HEIGHT / 2;

        private final ArrayList<Enemy> enemies;

        private final Timer gameLoop;
        private final Timer placeEnemyTimer;
        private boolean gameOver = false;
        private double score = 0;
        private final Timer timeTracker;
        private int timePassedSeconds = 0;

        private int velocityY = 0;
        private boolean strt = false;

        public Endgame() {
            setPreferredSize(new Dimension(BOARD_WIDTH, BOARD_HEIGHT));
            setBackground(Color.BLACK);
            setFocusable(true);
            addKeyListener(this);

            background = new ImageIcon(Objects.requireNonNull(getClass().getResource("./l15.gif"))).getImage();
            Image heroImage = new ImageIcon(Objects.requireNonNull(getClass().getResource("/thor.gif"))).getImage();
            Image heroOnClick = new ImageIcon(Objects.requireNonNull(getClass().getResource("/thorClick.gif"))).getImage();
            upperEnemyImage = new ImageIcon(Objects.requireNonNull(getClass().getResource("/upperEnemy.png"))).getImage();
            lowerEnemyImage = new ImageIcon(Objects.requireNonNull(getClass().getResource("/lowerEnemy.png"))).getImage();

            int heroStartX = BOARD_WIDTH / 8;
            int heroWidth = 60;
            int heroHeight = 60;
            hero = new Hero(heroStartX, heroStartY, heroWidth, heroHeight, heroImage, heroOnClick);

            enemies = new ArrayList<>();

            placeEnemyTimer = new Timer(1500, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    placeEnemy();
                }
            });
            placeEnemyTimer.start();

            gameLoop = new Timer(1000 / 60, this);
            gameLoop.start();
            timeTracker = new Timer(1000, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    timePassedSeconds++;
                }
            });
            timeTracker.start();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            draw(g);
        }

        private void draw(Graphics g) {
            g.drawImage(background, -timePassedSeconds, 0, background.getWidth(null), background.getHeight(null), null);

            if (strt) {
                g.drawImage(hero.getClickImage(), hero.getX(), hero.getY(), hero.getWidth(), hero.getHeight(), null);
                strt = false;
            } else {
                g.drawImage(hero.getImage(), hero.getX(), hero.getY(), hero.getWidth(), hero.getHeight(), null);
            }

            for (Enemy enemy : enemies) {
                g.drawImage(enemy.getImage(), enemy.getX(), enemy.getY(), enemy.getWidth(), enemy.getHeight(), null);
            }

            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.PLAIN, 32));
            if (gameOver) {
                g.drawString("GAME OVER: " + (int) score, 10, 35);
            } else {
                g.drawString(String.valueOf((int) score), 10, 35);
            }
        }

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
                timePassedSeconds = 0;
                placeEnemyTimer.start();
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {
        }

        @Override
        public void keyTyped(KeyEvent e) {
        }

        private void move() {
            int gravity = 1;
            velocityY += gravity;
            hero.setY(hero.getY() + velocityY);
            hero.setY(Math.max(hero.getY(), 0));

            for (Enemy enemy : enemies) {
                int velocityX = -4;
                enemy.setX(enemy.getX() + velocityX);

                if (!enemy.isPassed() && hero.getX() > enemy.getX() + enemy.getWidth()) {
                    enemy.setPassed();
                    score += 0.5;
                }

                if (collision(hero, enemy)) {
                    gameOver = true;
                }
            }

            if (hero.getY() > BOARD_HEIGHT) {
                gameOver = true;
            }
        }

        private void placeEnemy() {
            int enemyStartY = 0;
            int enemyHeight = 512;
            int randomEnemyY = (int) (enemyStartY - enemyHeight / 4 - Math.random() * (enemyHeight / 2));
            int openingSpace = (BOARD_HEIGHT / 4);

            int enemyStartX = BOARD_WIDTH;
            int enemyWidth = 64;
            Enemy topEnemy = new Enemy(enemyStartX, randomEnemyY, enemyWidth, enemyHeight, upperEnemyImage);
            enemies.add(topEnemy);

            Enemy bottomEnemy = new Enemy(enemyStartX, topEnemy.getY() + enemyHeight + openingSpace, enemyWidth, enemyHeight, lowerEnemyImage);
            enemies.add(bottomEnemy);
        }

        private boolean collision(Hero h, Enemy e) {
            return h.getX() + 10 < e.getX() + e.getWidth() &&
                    h.getX() + h.getWidth() - 10 > e.getX() &&
                    h.getY() + 10 < e.getY() + e.getHeight() &&
                    h.getY() + h.getHeight() - 10 > e.getY();
        }
    }
    ```

## Gameplay Functions
- **Hero Movement**: Controlled by `velocityY`, affected by gravity and key presses.
- **Enemy Movement**: Enemies move from right to left, resetting once they go off-screen.
- **Collision Detection**: Uses bounding boxes to detect collisions between the hero and enemies.
- **Score Calculation**: Incremented when the hero successfully passes an enemy.

## Increasing Difficulty
- **Idea**: Gradually increase the game's speed and the number of enemies over time.
- **Implementation**:
    - Increment the speed of enemies based on the time passed.
    - Reduce the time interval for spawning new enemies.

## Screenshots
- **Main Menu**: ![Main Menu](#)
- **Gameplay**: ![Gameplay](#)
- **Game Over**: ![Game Over](#)

## Conclusion
Enjoy playing **Endgame**! Feel free to contribute by implementing new features or improving the existing ones. Your feedback and contributions are highly appreciated! 🎮🦸‍♂️
