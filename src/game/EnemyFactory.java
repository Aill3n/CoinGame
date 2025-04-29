/*
BIT504 A3
Aillen Teixeira
Student ID: 2021712
*/

package game;

/**
 * The EnemyFactory class is responsible for creating Enemy objects
 * with random positions within specified panel bounds.
 */
public class EnemyFactory extends Sprite {

    public static Enemy createEnemy(int innerWidth, int innerHeight, int xPosition, int yPosition) {
        int enemySize = 25;
        int positionX = xPosition + (int) (Math.random() * (innerWidth - enemySize));
        int positionY = yPosition + (int) (Math.random() * (innerHeight - enemySize));

        // Create the enemy with the calculated position
        return new Enemy(positionX, positionY);
    }
}