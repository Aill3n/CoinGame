package game;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

public class GamePanel extends JPanel implements ActionListener, KeyListener {
    private final static Color BACKGROUND_COLOR = Color.PINK;
    private final static Color PAINT_COLOR = Color.BLACK;
    private final static Color INVINCIBLE_COLOR = Color.MAGENTA;
    private final static int TIMER_DELAY = 5;
    private final static int MOVEMENT_SPEED = 2;
    private static final int NUM_ITEMS = 10;
    private static final int WALL_WIDTH = 40;
    private static final int WALL_HEIGHT = 50;
    private static final int POINTS_TO_WIN = 10;
    private GameState gameState;
    private final List<Enemy> enemies = new ArrayList<>();
    private final List<Coin> coins = new ArrayList<>();

    private int playerScore = 0;

    Player player;

    public GamePanel() {
        setBackground(BACKGROUND_COLOR);
        gameState = GameState.INITIALISING;
        Timer timer = new Timer(TIMER_DELAY, this);
        timer.start();
        addKeyListener(this);
        setFocusable(true);
    }

    public void createObjects() {
        player = new Player(getWidth(), getHeight());

        coins.clear();
        enemies.clear();

        createCoins();
        setCoinVelocity();

        createEnemies();
        setEnemyVelocity();

    }

    private void createEnemies() {
        for (int i = 0; i < NUM_ITEMS; i++) {
            enemies.add(EnemyFactory.createEnemy(getWidth(), getHeight(), WALL_WIDTH + 10, WALL_HEIGHT + 10));
        }
    }

    private void createCoins() {
        for (int i = 0; i < NUM_ITEMS; i++) {
            coins.add(CoinFactoy.createCoin(getWidth(), getHeight(), WALL_WIDTH + 10, WALL_HEIGHT + 10));
        }
    }

    private void update() {

        switch (gameState) {
            case INITIALISING -> {
                setFocusable(true);
                requestFocusInWindow();
            }
            case PLAYING -> {
                moveObject(player);
                moveEnemy();
                moveCoins();
                checkWallcollisionPlayer(player);
                checkCollision();
                checkWin();
            }
            case GAME_OVER -> {
                resetGame();
                gameState = GameState.GAME_OVER;
            }
            case GAME_WON -> {
                resetGame();
                gameState = GameState.GAME_WON;
            }
        }
    }

    private void moveCoins() {
        for (Coin currentCoin : coins) {
            moveObject(currentCoin);
            checkWallBounce(currentCoin);
        }
    }

    private void moveEnemy() {
        for (Enemy enemy : enemies) {
            moveObject(enemy);
            checkWallBounce(enemy);
        }
    }

    private void paintSprite(Graphics g, Sprite sprite) {
        g.setColor(sprite.getColour());
        g.fill3DRect(sprite.getxPosition(), sprite.getyPosition(), sprite.getWidth(), sprite.getHeight(), true);
    }

    private void paintCoin(Graphics g, Sprite sprite) {
        g.setColor(sprite.getColour());
        g.fillOval(sprite.getxPosition(), sprite.getyPosition(), sprite.getWidth(), sprite.getHeight());
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(PAINT_COLOR);

        int borderWidth = 40;
        int borderHeight = 50;
        int panelWidth = getWidth();
        int panelHeight = getHeight();
        int rectWidth = panelWidth - 2 * borderWidth;
        int rectHeight = panelHeight - 2 * borderHeight;

        g.setFont(new Font("Roboto", Font.PLAIN, 20)); // Set font to Consolas with size 20

        switch (gameState) {
            case INITIALISING ->
                g.drawString("Press ENTER or SPACE to start the game.", borderWidth, borderHeight - 20);
            case PLAYING -> {
                g.drawString("Health:" + displayHearts(), borderWidth, borderHeight - 20);
                g.drawString("\t Points:" + playerScore, borderWidth + 100, borderHeight - 20);
            }
            case GAME_OVER ->
                g.drawString("Game Over! Press ENTER or SPACE to restart.", borderWidth, borderHeight - 20);
            case GAME_WON ->
                g.drawString("You won! Press ENTER or SPACE to play again.", borderWidth, borderHeight - 20);
            default -> {
            }
        }

        g.fillRect(borderWidth, borderHeight, rectWidth, rectHeight);

        if (gameState == GameState.PLAYING) {
            paintSprite(g, player);
            for (Coin currentCoin : coins) {
                paintCoin(g, currentCoin);
            }
            for (Enemy enemy : enemies) {
                paintSprite(g, enemy);
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        update();
        repaint();
    }

    @Override
    public void keyTyped(KeyEvent event) {

    }

    @Override
    public void keyPressed(KeyEvent event) {
        if (gameState == GameState.PLAYING) {
            switch (event.getKeyCode()) {
                case KeyEvent.VK_UP -> player.setyVelocity(-5); // Move up
                case KeyEvent.VK_DOWN -> player.setyVelocity(5); // Move down
                case KeyEvent.VK_LEFT -> player.setxVelocity(-5); // Move left
                case KeyEvent.VK_RIGHT -> player.setxVelocity(5); // Move right
                case KeyEvent.VK_ESCAPE -> {
                    gameState = GameState.INITIALISING;
                }
            }
        } else if (gameState == GameState.INITIALISING || gameState == GameState.GAME_OVER
                || gameState == GameState.GAME_WON) {
            if (event.getKeyCode() == KeyEvent.VK_ENTER || event.getKeyCode() == KeyEvent.VK_SPACE) {
                createObjects();
                gameState = GameState.PLAYING;
            } else if (event.getKeyCode() == KeyEvent.VK_ESCAPE) {
                System.exit(0);
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent event) {
        if (gameState == GameState.PLAYING) {
            if (event.getKeyCode() == KeyEvent.VK_UP || event.getKeyCode() == KeyEvent.VK_DOWN) {
                player.setyVelocity(0);
            } else if (event.getKeyCode() == KeyEvent.VK_LEFT || event.getKeyCode() == KeyEvent.VK_RIGHT) {
                player.setxVelocity(0);
            }
        }
    }

    private void moveObject(Sprite obj) {
        obj.setxPosition(obj.getxPosition() + obj.getxVelocity(), getWidth());
        obj.setyPosition(obj.getyPosition() + obj.getyVelocity(), getHeight());
    }

    private void checkWallBounce(Sprite object) {

        // Hit left side of screen or right side of screen
        if (object.getxPosition() <= WALL_WIDTH
                || object.getxPosition() >= getWidth() - object.getWidth() - WALL_WIDTH) {
            object.setxVelocity(-object.getxVelocity());
        }
        // Hit top or bottom of screen
        if (object.getyPosition() <= WALL_HEIGHT
                || object.getyPosition() >= getHeight() - object.getHeight() - WALL_HEIGHT) {
            object.setyVelocity(-object.getyVelocity());
        }
    }

    private void checkCollision() {
        for (Coin currentCoin : coins) {
            if (isColliding(player, currentCoin)) {
                playerScore = addScore();
                coins.remove(currentCoin);
                break;
            }
        }

        for (Enemy currentEnemy : enemies) {
            if (isColliding(player, currentEnemy) && player.isInvencible() == false) {
                player.setHealth(player.getHealth() - 1);
                enemies.remove(currentEnemy);
                invencibleMode();
                repaint();

                if (player.getHealth() <= 0) {
                    gameState = GameState.GAME_OVER;
                }
                break;
            }
        }
    }

    private void invencibleMode() {
        player.setInvencible(true);
        player.setColour(INVINCIBLE_COLOR);

        Timer invincibleTimer = new Timer(3000, e -> {
            // Reset the changes after 3 seconds
            player.setColour(Color.WHITE);
            player.setInvencible(false);
        });
        invincibleTimer.start(); // Start the timer
        invincibleTimer.setRepeats(false);
    }

    private boolean isColliding(Sprite obj1, Sprite obj2) {
        return obj1.getxPosition() < obj2.getxPosition() + obj2.getWidth() &&
                obj1.getxPosition() + obj1.getWidth() > obj2.getxPosition() &&
                obj1.getyPosition() < obj2.getyPosition() + obj2.getHeight() &&
                obj1.getyPosition() + obj1.getHeight() > obj2.getyPosition();
    }

    private String displayHearts() {
        StringBuilder hearts = new StringBuilder();
        for (int i = 0; i < player.getHealth(); i++) {
            hearts.append("\u2665");
        }
        return hearts.toString();
    }

    private void checkWallcollisionPlayer(Sprite object) {
        if (object.getxPosition() <= WALL_WIDTH) {
            object.setxPosition(WALL_WIDTH);
        } else if (object.getxPosition() >= getWidth() - object.getWidth() - WALL_WIDTH) {
            object.setxPosition(getWidth() - object.getWidth() - WALL_WIDTH);
        }
        if (object.getyPosition() <= WALL_HEIGHT) {
            object.setyPosition(WALL_HEIGHT);
        } else if (object.getyPosition() >= getHeight() - object.getHeight() - WALL_HEIGHT) {
            object.setyPosition(getHeight() - object.getHeight() - WALL_HEIGHT);
        }
    }

    private void setEnemyVelocity() {
        for (Enemy enemy : enemies) {
            enemy.setxVelocity(MOVEMENT_SPEED);
            enemy.setyVelocity(MOVEMENT_SPEED);
        }
    }

    private void setCoinVelocity() {
        for (Coin coin : coins) {
            coin.setxVelocity(MOVEMENT_SPEED);
            coin.setyVelocity(MOVEMENT_SPEED);
        }
    }

    private void resetObjects() {
        for (Enemy enemy : enemies) {
            enemy.resetToInitialPosition();
        }
        for (Coin coin : coins) {
            coin.resetToInitialPosition();
        }
    }

    private void resetGame() {
        resetObjects();
        player.resetToInitialPosition();
        player.setHealth(3);
        playerScore = 0;
    }

    private void checkWin() {
        if (playerScore >= POINTS_TO_WIN) {
            gameState = GameState.GAME_WON;
        }
    }

    private int addScore() {
        return ++playerScore;
    }

}
