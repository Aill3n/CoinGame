import java.awt.Color;

// The Enemy class will represent antagonists that hurt the player.
// They can move around the screen and bounce off the screen edges.
public class Enemy extends Sprite {

    private static final int WIDTH = 25;
    private static final int HEIGHT = 25;
    private static final Color COLOUR = Color.RED;

    public Enemy(int panelWidth, int panelHeight) {
        setWidth(WIDTH);
        setHeight(HEIGHT);
        setColour(COLOUR);
        setInitialPosition(panelWidth / 2 - (getWidth() / 2), panelHeight / 2 - (getHeight() / 2));
        resetToInitialPosition();
    }

}
