package game;
import java.awt.*;

public class Sprite {

    private Color color;
    private int xPosition, yPosition;
    private int xVelocity, yVelocity;
    private int width, height;
    private int initialXPosition, initialYPosition;

    public Color getColour() {
        return color;
    }

    public void setColour(Color colour) {
        this.color = colour;
    }

    public Rectangle getRectangle() {
        return new Rectangle(getxPosition(), getyPosition(), getWidth(), getHeight());
    }

    public int getxPosition() {
        return xPosition;
    }

    public void setxPosition(int newX) {
        xPosition = newX;
    }

    public void setyPosition(int newY) {
        yPosition = newY;
    }

    public void setxPosition(int newX, int panelWidth) {
        xPosition = newX;
        if (xPosition < 0) {
            xPosition = 0;
        } else if (xPosition + width > panelWidth) {
            xPosition = panelWidth - width;
        }
    }

    public void setyPosition(int newY, int panelHeight) {
        yPosition = newY;
        if (yPosition < 0) {
            yPosition = 0;
        } else if (yPosition + height > panelHeight) {
            yPosition = panelHeight - height;
        }
    }

    public int getyPosition() {
        return yPosition;
    }

    public int getxVelocity() {
        return xVelocity;
    }

    public void setxVelocity(int xVelocity) {
        this.xVelocity = xVelocity;
    }

    public int getyVelocity() {
        return yVelocity;
    }

    public void setyVelocity(int yVelocity) {
        this.yVelocity = yVelocity;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getInitialXPosition() {
        return initialXPosition;
    }

    public void setInitialXPosition(int initialXPosition) {
        this.initialXPosition = initialXPosition;
    }

    public int getInitialYPosition() {
        return initialYPosition;
    }

    public void setInitialYPosition(int initialYPosition) {
        this.initialYPosition = initialYPosition;
    }

    public void setInitialPosition(int initialX, int initialY) {
        initialXPosition = initialX;
        initialYPosition = initialY;
    }

    public void resetToInitialPosition() {
        setxPosition(initialXPosition);
        setyPosition(initialYPosition);
    }
}
