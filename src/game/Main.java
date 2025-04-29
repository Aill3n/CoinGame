package game;
import javax.swing.*;

public class Main extends JFrame {

    private final static String WINDOW_TITLE = "Coin Game";

    private final static int WINDOW_WIDTH = 800;

    private final static int WINDOW_HEIGHT = 600;

    public static void main(String[] args) {

        javax.swing.SwingUtilities.invokeLater(Main::new);
    }

    public Main() {
        setTitle(WINDOW_TITLE);
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setResizable(false);
        add(new GamePanel());
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
}