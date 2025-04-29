/* 
BIT504 A3
Aillen Teixeira 
Student ID: 2021712
*/

package game;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;


//Manages the game panel, display components, movements, and handles logic
public class GamePanel extends JPanel implements ActionListener, KeyListener {
    private static final int INVINCIBILITY_DUR = 3000;
    private static final int PADDING = 10;
    private final static Color BACKGROUND_COLOR = Color.PINK;
    private final static Color PAINT_COLOR = Color.BLACK;
    private final static Color INVINCIBLE_COLOR = Color.MAGENTA;
    private final static int TIMER_DELAY = 5;
    private final static int MOVEMENT_SPEED = 2;
    private static final int NUM_ITEMS = 10;
    private static final int WALL_WIDTH = 40;
    private static final int WALL_HEIGHT = 50;
    private static final int POINTS_TO_WIN = 10;
    private static final int HEARTS = 3;
    private GameState gameState;
    private final List<Enemy> enemies = new ArrayList<>();
    private final List<Coin> coins = new ArrayList<>();

    private int playerScore = 0;

    Player player;

    /**
     * Constructor for the GamePanel.
     * Sets up the game panel, initializes the game state, starts the game timer, and configures key listeners.
     */
    public GamePanel() {
        setBackground(BACKGROUND_COLOR);
        gameState = GameState.INITIALISING;
        Timer timer = new Timer(TIMER_DELAY, this);
        timer.start();
        addKeyListener(this);
        setFocusable(true);
    }

    /**
     * Creates all game objects including player, coins, and enemies.
     */
    public void createObjects() {
        createPlayer();
        setUpCoins();
        setUpEnemies();
    }

    /**
     * Creates and initializes the player instance with the panel's dimensions.
     */
    private void createPlayer() {
        player = new Player(getWidth(), getHeight());
    }

    /**
     * Clears the enemy list and reinitialize enemy objects with their velocities.
     */
    private void setUpEnemies() {
        enemies.clear();
        createEnemies();
        setObjectVelocity(enemies);
    }

    /**
     * Clears the coin list and initializes new coin objects.
     */
    private void setUpCoins() {
        coins.clear();
        createCoins();
    }

    /**
     * Creates multiple enemies and adds them to the enemy list.
     */
    private void createEnemies() {
        for (int i = 0; i < NUM_ITEMS; i++) {
            enemies.add(EnemyFactory.createEnemy(getWidth(), getHeight(), WALL_WIDTH + PADDING, WALL_HEIGHT + PADDING));
        }
    }

    /**
     * Creates multiple coins and adds them to the coin list.
     */
    private void createCoins() {
        for (int i = 0; i < NUM_ITEMS; i++) {
            coins.add(CoinFactory.createCoin(getWidth(), getHeight(), WALL_WIDTH + PADDING, WALL_HEIGHT + PADDING));
        }
    }

    /**
     * Updates the game state and handles game logic depending on the current state.
     */
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
                checkWallCollisionPlayer(player);
                checkCollision();
                checkWin();
            }
            case GAME_OVER, GAME_WON -> resetGame();
        }
    }

    /**
     * Updates the position of all coins and checks for wall collisions.
     */
    private void moveCoins() {
        for (Coin currentCoin : coins) {
            moveObject(currentCoin);
            checkWallBounce(currentCoin);
        }
    }

    /**
     * Updates the position of all enemies and checks for wall collisions.
     */
    private void moveEnemy() {
        for (Enemy enemy : enemies) {
            moveObject(enemy);
            checkWallBounce(enemy);
        }
    }

    /**
     * Draws the sprite on the game panel using the specified graphical context.
     *
     * @param g      the Graphics object used for rendering
     * @param sprite the sprite to be painted
     */
    private void paintSprite(Graphics g, Sprite sprite) {
        g.setColor(sprite.getColour());
        g.fill3DRect(sprite.getxPosition(), sprite.getyPosition(), sprite.getWidth(), sprite.getHeight(), true);
    }

    /**
     * Draws a coin on the game panel using the specified graphical context.
     *
     * @param g      the Graphics object used for rendering
     * @param sprite the coin to be painted
     */
    private void paintCoin(Graphics g, Sprite sprite) {
        g.setColor(sprite.getColour());
        g.fillOval(sprite.getxPosition(), sprite.getyPosition(), sprite.getWidth(), sprite.getHeight());
    }

    /**
     * Renders all game objects and game-related visual elements
     * on the panel based on the current game state.
     *
     * @param g Graphics object used for drawing on the panel.
     */
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

        g.setFont(new Font("Roboto", Font.PLAIN, 20));

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

    /**
     * Handles the action events triggered by a timer or other sources.
     * Updates the game state and repaints the game panel to reflect changes.
     *
     * @param event the ActionEvent that triggered this method
     */
    @Override
    public void actionPerformed(ActionEvent event) {
        update();
        repaint();
    }

    /**
     * Handles a key typed event. This method is triggered when a key is typed (a key press followed by a key release),
     * and processes the input to determine the appropriate action for the game.
     *
     * @param event the KeyEvent object containing details about the key typed event
     */
    @Override
    public void keyTyped(KeyEvent event) {

    }

    /**
     * The behavior is as follows:
     * - When the game is in the "PLAYING" state, arrow keys control the player's movement,
     *   and the ESC key pauses the game (switches to "INITIALISING" state).
     * - In the "INITIALISING," "GAME_OVER," or "GAME_WON" states:
     *   - The ENTER or SPACE key restarts the game by recreating objects and switching the
     *     state to "PLAYING."
     *   - The ESC key exits the application.
     *
     * @param event The KeyEvent representing the key press action, which is used to
     *              determine the action to take based on the current game state.
     */
    @Override
    public void keyPressed(KeyEvent event) {
        if (gameState == GameState.PLAYING) {
            switch (event.getKeyCode()) {
                case KeyEvent.VK_UP -> player.setyVelocity(-5); // Move up
                case KeyEvent.VK_DOWN -> player.setyVelocity(5); // Move down
                case KeyEvent.VK_LEFT -> player.setxVelocity(-5); // Move left
                case KeyEvent.VK_RIGHT -> player.setxVelocity(5); // Move right
                case KeyEvent.VK_ESCAPE -> gameState = GameState.INITIALISING;
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

    /**
     * Handles the event triggered when a key is released.
     * If the game is in the PLAYING state, it stops the player's movement
     * along the appropriate axis if the released key corresponds to a directional key.
     *
     * @param event the KeyEvent object that indicates which key was released
     */
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


    /**
     * Updates the position of a sprite object based on its current x and y velocities
     * and ensures the position remains within the panel's boundaries.
     *
     * @param obj the sprite whose position is being updated
     */
    private void moveObject(Sprite obj) {
        obj.setxPosition(obj.getxPosition() + obj.getxVelocity(), getWidth());
        obj.setyPosition(obj.getyPosition() + obj.getyVelocity(), getHeight());
    }

    /**
     * Checks whether a sprite object is colliding with the walls and reverses
     * its velocity to make it bounce off the walls.
     *
     * @param object the sprite being checked for wall collisions
     */
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
    
    /**
     * Checks collisions between the player and game objects.
     * Coins increase the score and are removed, while enemy collisions
     * reduce player health and can trigger game state changes.
     */
    private void checkCollision() {
        for (Coin currentCoin : coins) {
            if (isColliding(player, currentCoin)) {
                addScore();
                coins.remove(currentCoin);
                break;
            }
        }

        for (Enemy currentEnemy : enemies) {
            if (isColliding(player, currentEnemy) && !player.isInvincible()) {
                player.setHealth(player.getHealth() - 1);
                enemies.remove(currentEnemy);
                invincibleMode();
                repaint();

                if (player.getHealth() <= 0) {
                    gameState = GameState.GAME_OVER;
                }
                break;
            }
        }
    }

    /**
     * Activates invincibility for the player for a limited duration.
     * During this time, the player cannot take damage.
     */
    private void invincibleMode() {
        player.setInvincible(true);
        player.setColour(INVINCIBLE_COLOR);

        Timer invincibleTimer = new Timer(INVINCIBILITY_DUR, e -> {
            // Reset the changes after 3 seconds
            player.setColour(Color.WHITE);
            player.setInvincible(false);
        });
        invincibleTimer.start(); // Start the timer
        invincibleTimer.setRepeats(false);
    }

    /**
     * Determines whether two sprite objects are colliding by checking
     * for overlapping positions and dimensions.
     *
     * @param obj1 the first sprite object
     * @param obj2 the second sprite object
     * @return true if the objects are colliding, false otherwise
     */
    private boolean isColliding(Sprite obj1, Sprite obj2) {
        return obj1.getxPosition() < obj2.getxPosition() + obj2.getWidth() &&
                obj1.getxPosition() + obj1.getWidth() > obj2.getxPosition() &&
                obj1.getyPosition() < obj2.getyPosition() + obj2.getHeight() &&
                obj1.getyPosition() + obj1.getHeight() > obj2.getyPosition();
    }

    
    /**
     * Displays the player's health using a string of hearts
     * based on their current health value.
     *
     * @return a string representation of the hearts corresponding to player health.
     */
    private String displayHearts() {
        return "♥".repeat(Math.max(0, player.getHealth()));
    }

    /**
     * Prevents the player from moving outside the boundaries of the panel.
     * Adjusts the player's position if it collides with the wall.
     *
     * @param object the sprite object to check against the panel's boundaries.
     */
    private void checkWallCollisionPlayer(Sprite object) {
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

    /**
     * Sets the initial velocity for a list of sprites.
     * This method ensures all sprites (e.g., enemies) have uniform movement at the start.
     *
     * @param objects a list of sprites whose velocities are being initialized.
     */
    private void setObjectVelocity(List<? extends Sprite> objects) {
        for (Sprite obj : objects) {
            obj.setxVelocity(MOVEMENT_SPEED);
            obj.setyVelocity(MOVEMENT_SPEED);
        }
    }

    /**
     * Resets all enemies and coins to their initial positions.
     * This method prepares the game objects for a new round or restart.
     */
    private void resetObjects() {
        for (Enemy enemy : enemies) {
            enemy.resetToInitialPosition();
        }
        for (Coin coin : coins) {
            coin.resetToInitialPosition();
        }
    }

    /**
     * Resets the game to its initial configuration.
     * Restores the player's health, resets the score, and repositions all objects.
     */
    private void resetGame() {
        resetObjects();
        player.resetToInitialPosition();
        player.setHealth(HEARTS);
        playerScore = 0;
    }

    /**
     * Checks if the player has achieved the required score to win the game.
     * Updates the game state to 'GAME_WON' if the victory condition is met.
     */
    private void checkWin() {
        if (playerScore >= POINTS_TO_WIN) {
            gameState = GameState.GAME_WON;
        }
    }

    /**
     * Increases the player's score by 1.
     */
    private void addScore() {
        ++playerScore;
    }

}
