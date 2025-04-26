package game;
import java.awt.Color;

// The Enemy class will represent antagonists that hurt the player.
// They can move around the screen and bounce off the screen edges.
public class Enemy extends Sprite {

    private static final int DIMENSION = 20;
    private static final Color COLOUR = Color.RED;

    public Enemy(int positionX, int positionY) {
        setWidth(DIMENSION);
        setHeight(DIMENSION);
        setColour(COLOUR);
        setInitialPosition(positionX, positionY);
        resetToInitialPosition();
    }

}
