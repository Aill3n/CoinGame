/*
BIT504 A3
Aillen Teixeira
Student ID: 2021712
*/

package game;

import java.awt.Color;

/**
 * The Player class represents the protagonist controlled by the user.
 */
public class Player extends Sprite {

    private static final int DIMENSION = 25;
    private static final Color COLOUR = Color.WHITE;
    private static int health = 3;
    private boolean invincible = false;

    public Player(int panelWidth, int panelHeight) {
        setWidth(DIMENSION);
        setHeight(DIMENSION);
        setColour(COLOUR);
        setInitialPosition(panelWidth / 2 - (getWidth() / 2), panelHeight / 2 - (getHeight() / 2));
        resetToInitialPosition();
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        Player.health = health;
    }

    public boolean isInvincible() {
        return invincible;
    }

    public void setInvincible(boolean invincible) {
        this.invincible = invincible;
    }

    /**
     * Check the position of the player against a coin or an emey
     */
    public boolean isPlayerTooClose(Sprite sprite) {
        int safeDistance = 100;
        return Math.abs(sprite.getxPosition() - this.getxPosition()) < safeDistance
                && Math.abs(sprite.getyPosition() - this.getyPosition()) < safeDistance;
    }
}
