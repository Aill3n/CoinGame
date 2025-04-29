package game;

/**
 * Creates Coin objects positioned randomly
 * within a defined playable area of a game panel.
 */
public class CoinFactory {

    public static Coin createCoin(int panelWidth, int panelHeight, int borderWidth, int borderHeight) {
        int rectWidth = panelWidth - 2 * borderWidth;
        int rectHeight = panelHeight - 2 * borderHeight;
        int coinSize = 25;

        int positionX = borderWidth + (int) (Math.random() * (rectWidth - coinSize));
        int positionY = borderHeight + (int) (Math.random() * (rectHeight - coinSize));

        return new Coin(positionX, positionY);
    }
}
