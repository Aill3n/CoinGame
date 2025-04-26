package game;

import java.awt.Color;

// The Coin will represent non-moving objects the player can collect.
public class Coin extends Sprite {

    private static final int DIMENSION = 20;
    private static final Color COLOUR = Color.YELLOW;

    public Coin(int panelWidth, int panelHeight) {
        setWidth(DIMENSION);
        setHeight(DIMENSION);
        setColour(COLOUR);
        setInitialPosition(panelWidth, panelHeight);
        resetToInitialPosition();
    }
}
