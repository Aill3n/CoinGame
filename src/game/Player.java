package game;

import java.awt.Color;

public class Player extends Sprite {

    private static final int DIMENSION = 25;
    private static final Color COLOUR = Color.WHITE;
    private static int health = 3;
    private boolean invencible = false;

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

    public boolean isInvencible() {
        return invencible;
    }

    public void setInvencible(boolean invencible) {
        this.invencible = invencible;
    }

}
