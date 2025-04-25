import java.awt.Color;

public class Player extends Sprite {

    // The Player class will represent the user character and perform actions as the
    // user specifies.

    private static final int DIMENSION = 25;
    private static final Color COLOUR = Color.WHITE;

    public Player(int panelWidth, int panelHeight) {
        setWidth(DIMENSION);
        setHeight(DIMENSION);
        setColour(COLOUR);
        setInitialPosition(panelWidth / 2 - (getWidth() / 2), panelHeight / 2 - (getHeight() / 2));
        resetToInitialPosition();
    }

}
