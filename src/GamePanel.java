import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.*;

public class GamePanel extends JPanel implements ActionListener, KeyListener {
    private final static Color BACKGROUND_COLOR = Color.PINK;
    private final static Color PAINT_COLOR = Color.WHITE;
    private final static int TIMER_DELAY = 5;

    public GamePanel() {
        setBackground(BACKGROUND_COLOR);
        Timer timer = new Timer(TIMER_DELAY, this);
        timer.start();
    }

    public void update() {
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(PAINT_COLOR);
        g.fillRect(20, 20, 100, 100);
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        update();
        repaint();
    }

    @Override
    public void keyTyped(KeyEvent event) {

    }

    @Override
    public void keyPressed(KeyEvent event) {

    }

    @Override
    public void keyReleased(KeyEvent event) {

    }
}
