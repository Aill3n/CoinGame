/*
BIT504 A3
Aillen Teixeira
Student ID: 2021712
*/

package game;

import java.awt.Color;

/**
 * The Enemy class will represent antagonists that hurt the player.
 * They can move around the screen and bounce off the screen edges.
 */

public class Enemy extends Sprite {

    private static final int DIMENSION = 20;
    private static final Color COLOUR = Color.BLUE;

    /**
     * Constructor of the Enemy
     * @param positionX horizontal position of the enemy
     * @param positionY vertical position of the enemy
     */
    public Enemy(int positionX, int positionY) {
        setWidth(DIMENSION);
        setHeight(DIMENSION);
        setColour(COLOUR);
        setInitialPosition(positionX, positionY);
        resetToInitialPosition();
    }

}
