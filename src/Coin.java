import java.awt.Color;

// The Coin will represent non-moving objects the player can collect.
public class Coin extends Sprite {

    private static final int WIDTH = 25;
    private static final int HEIGHT = 25;
    private static final Color COLOUR = Color.YELLOW;

    public Coin(int panelWidth, int panelHeight) {
        setWidth(WIDTH);
        setHeight(HEIGHT);
        setColour(COLOUR);
        setInitialPosition(panelWidth / 3 - (getWidth() / 3), panelHeight / 3 - (getHeight() / 3));
        resetToInitialPosition();
    }
}
