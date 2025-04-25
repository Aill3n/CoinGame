public class CoinFactoy {

    public static Coin createCoin(int panelWidth, int panelHeight, int borderWidth, int borderHeight) {
        int rectWidth = panelWidth - 2 * borderWidth;
        int rectHeight = panelHeight - 2 * borderHeight;

        // Coin dimensions
        int coinSize = 25;

        int positionX = borderWidth + (int) (Math.random() * (rectWidth - coinSize));
        int positionY = borderHeight + (int) (Math.random() * (rectHeight - coinSize));

        // Create the Coin with the calculated position
        return new Coin(positionX, positionY);
    }
}
