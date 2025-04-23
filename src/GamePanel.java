import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.*;

public class GamePanel extends JPanel implements ActionListener, KeyListener {
    private final static Color BACKGROUND_COLOR = Color.PINK;
    private final static Color PAINT_COLOR = Color.WHITE;
    private final static int TIMER_DELAY = 5;
    private final static int ENEMY_MOVEMENT_SPEED = 2;
    private GameState gameState;

    Player player;
    Enemy enemy;
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
        enemy = new Enemy(getWidth(), getHeight());
        coin = new Coin(getWidth(), getHeight());
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
                moveObject(enemy);
                checkWallColision(enemy);
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
        g.fillRect(sprite.getxPosition(), sprite.getyPosition(), sprite.getWidth(), sprite.getHeight());
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(PAINT_COLOR); // AILLEn

        switch (gameState) {
            case INITIALISING -> g.drawString("Press ENTER or SPACE to start the game.", 50, 50);
            case PLAYING -> g.drawString("Game is in progress", 50, 50);
            case GAME_OVER -> g.drawString("Game Over! Press ENTER or SPACE to restart.", 50, 50);
            case GAME_WON -> g.drawString("You won! Press ENTER or SPACE to play again.", 50, 50);
            default -> {
            }
        }

        g.fillRect(40, 90, 700, 400);
        if (gameState != GameState.INITIALISING) {
            paintSprite(g, player);
            paintSprite(g, enemy);
            paintSprite(g, coin);
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
                setEnemyVelocity();
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

    private void checkWallColision(Enemy enemy) {
        if (enemy.getxPosition() <= 0) {
            // Hit left side of screen
            enemy.setxVelocity(-enemy.getxVelocity());
            resetEnemy();
        } else if (enemy.getxPosition() >= getWidth() - enemy.getWidth()) {
            // Hit right side of screen
            enemy.setxVelocity(-enemy.getxVelocity());
            resetEnemy();
        }
        if (enemy.getyPosition() <= 0 || enemy.getyPosition() >= getHeight() - enemy.getHeight()) {
            // Hit top or bottom of screen
            enemy.setyVelocity(-enemy.getyVelocity());
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
        enemy.setxVelocity(ENEMY_MOVEMENT_SPEED);
        enemy.setyVelocity(ENEMY_MOVEMENT_SPEED);
    }

    private void resetEnemy() {
        enemy.resetToInitialPosition();
    }
}
