import java.awt.Color;

public class Player extends Sprite {

    // The Player class will represent the user character and perform actions as the
    // user specifies.

    private static final int WIDTH = 25;
    private static final int HEIGHT = 25;
    private static final Color COLOUR = Color.BLACK;

    public Player(int panelWidth, int panelHeight) {
        setWidth(WIDTH);
        setHeight(HEIGHT);
        setColour(COLOUR);
        setInitialPosition(panelWidth / 2 - (getWidth() / 2), panelHeight / 2 - (getHeight() / 2));
        resetToInitialPosition();
    }

}
