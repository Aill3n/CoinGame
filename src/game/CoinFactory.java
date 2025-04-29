package game;

/**
 * Creates Coin objects positioned randomly
 * within a defined playable area of a game panel.
 */
public class CoinFactory {

    public static Coin createCoin(int innerWidth, int innerHeight, int xPosition, int yPosition) {
            int enemySize = 25;
            int positionX = xPosition + (int) (Math.random() * (innerWidth - enemySize));
            int positionY = yPosition + (int) (Math.random() * (innerHeight - enemySize));
            int playerX = xPosition + (innerHeight / 2) - (enemySize / 2);
            int playerY = yPosition + (innerWidth / 2) - (enemySize / 2);

            // Ensure the coin is not too close to the player
            // Assuming player is at the center of the panel
            while (Math.abs(positionX - playerX) < enemySize && Math.abs(positionY - playerY) < enemySize) {
                positionX = xPosition + (int) (Math.random() * (innerWidth - enemySize));
                positionY = yPosition + (int) (Math.random() * (innerHeight - enemySize));
            }

            // Create the enemy with the calculated position
            return new Coin(positionX, positionY);
    }
}
