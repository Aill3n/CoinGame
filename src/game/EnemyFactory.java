package game;

/**
 * The EnemyFactory class is responsible for creating Enemy objects
 * with random positions within specified panel bounds.
 */
public class EnemyFactory extends Sprite {

    // TODO: Coins shouldn't overlap.
    // TODO: Enemies shouldn't overlap.

    public static Enemy createEnemy(int panelWidth, int panelHeight, int borderWidth, int borderHeight) {
        int rectWidth = panelWidth - 5 * borderWidth;
        int rectHeight = panelHeight - 5 * borderHeight;

        int enemySize = 25;

        int positionX = borderWidth + (int) (Math.random() * (rectWidth - enemySize));
        int positionY = borderHeight + (int) (Math.random() * (rectHeight - enemySize));

        // Ensure the enemy is not too close to the player
        // Assuming player is at the center of the panel
        int playerX = panelWidth / 2 - (enemySize / 2);
        int playerY = panelHeight / 2 - (enemySize / 2);
        while (Math.abs(positionX - playerX) < enemySize && Math.abs(positionY - playerY) < enemySize) {
            positionX = borderWidth + (int) (Math.random() * (rectWidth - enemySize));
            positionY = borderHeight + (int) (Math.random() * (rectHeight - enemySize));
        }

        // Create the enemy with the calculated position
        return new Enemy(positionX, positionY);
    }
}