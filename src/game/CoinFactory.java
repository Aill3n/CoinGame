/*
BIT504 A3
Aillen Teixeira
Student ID: 2021712
*/

package game;

/**
 * Creates Coin objects positioned randomly
 * within a defined playable area of a game panel.
 */
public class CoinFactory {

    public static Coin createCoin(int innerWidth, int innerHeight, int xPosition, int yPosition) {
            int coinSize = 25;
            int positionX = xPosition + (int) (Math.random() * (innerWidth - coinSize));
            int positionY = yPosition + (int) (Math.random() * (innerHeight - coinSize));

            // Create the coin with the calculated position
            return new Coin(positionX, positionY);
    }
}
