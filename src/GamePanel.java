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
    private final static int TIMER_DELAY = 5;
    private final static int ENEMY_MOVEMENT_SPEED = 2;
    private static final int NUM_ITEMS = 10;
    private static final int WALL_WIDTH = 40;
    private static final int WALL_HEIGHT = 50;
    private GameState gameState;
    private final List<Enemy> enemies = new ArrayList<>();
    private final List<Coin> coins = new ArrayList<>();

    Player player;
    Coin coin;

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
            enemies.add(EnemyFactory.createEnemy(getWidth(), getHeight(), WALL_WIDTH, WALL_HEIGHT));
        }
    }

    private void createCoins() {
        for (int i = 0; i < NUM_ITEMS; i++) {
            coins.add(CoinFactoy.createCoin(getWidth(), getHeight(), WALL_WIDTH - 10, WALL_HEIGHT - 10));
        }
    }

    private void update() {

        switch (gameState) {
            case INITIALISING -> {
                setFocusable(true);
                requestFocusInWindow();
                break;
            }
            case PLAYING -> {
                moveObject(player);
                for (Enemy enemy : enemies) {
                    moveObject(enemy);
                    checkWallColision(enemy);
                }
                for (Coin currentCoin : coins) {
                    moveObject(currentCoin);
                    checkWallColision(currentCoin);
                }
                break;
            }
            case GAME_OVER -> {
                gameOver();
                break;
            }
            case GAME_WON -> {
                winGame();
                break;
            }
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

        switch (gameState) {
            case INITIALISING ->
                g.drawString("Press ENTER or SPACE to start the game.", borderWidth, borderHeight - 20);
            case PLAYING -> g.drawString("Game is in progress", borderWidth, borderHeight - 20);
            case GAME_OVER ->
                g.drawString("Game Over! Press ENTER or SPACE to restart.", borderWidth, borderHeight - 20);
            case GAME_WON ->
                g.drawString("You won! Press ENTER or SPACE to play again.", borderWidth, borderHeight - 20);
            default -> {
            }
        }

        g.fillRect(borderWidth, borderHeight, rectWidth, rectHeight);

        if (gameState != GameState.INITIALISING) {
            paintSprite(g, player);
            for (Coin coin : coins) {
                paintCoin(g, coin);
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

    private void checkWallColision(Sprite object) {

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

    private void winGame() {
        System.out.println("You won. Congratulations!");
        gameState = GameState.GAME_OVER;
    }

    private void gameOver() {
        System.out.println("Game over, try again.");
        gameState = GameState.GAME_OVER;
    }

    private void setEnemyVelocity() {
        for (Enemy enemy : enemies) {
            enemy.setxVelocity(ENEMY_MOVEMENT_SPEED);
            enemy.setyVelocity(ENEMY_MOVEMENT_SPEED);
        }
    }

    private void setCoinVelocity() {
        for (Coin coin : coins) {
            coin.setxVelocity(ENEMY_MOVEMENT_SPEED);
            coin.setyVelocity(ENEMY_MOVEMENT_SPEED);
        }
    }

    private void resetEnemy() {
        for (Enemy enemy : enemies) {
            enemy.resetToInitialPosition();
        }
    }
}
