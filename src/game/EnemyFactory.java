package game;
public class EnemyFactory {

    public static Enemy createEnemy(int panelWidth, int panelHeight, int borderWidth, int borderHeight) {
        int rectWidth = panelWidth - 2 * borderWidth;
        int rectHeight = panelHeight - 2 * borderHeight;

        // Enemy dimensions
        int enemySize = 25;

        int positionX = borderWidth + (int) (Math.random() * (rectWidth - enemySize));
        int positionY = borderHeight + (int) (Math.random() * (rectHeight - enemySize));

        // Create the enemy with the calculated position
        return new Enemy(positionX, positionY);
    }
}