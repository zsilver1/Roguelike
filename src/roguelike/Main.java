package roguelike;

import javax.swing.JFrame;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import asciiPanel.AsciiPanel;
import roguelike.screens.Screen;
import roguelike.screens.StartScreen;

public class Main extends JFrame implements KeyListener {
    private AsciiPanel terminal;
    private Screen screen;

    // represent the terminal width and height, given to
    // every screen as initialization input
    private static final int WIDTH = 175;
    private static final int HEIGHT = 55;

    private Main() {
        super();
        terminal = new AsciiPanel(WIDTH, HEIGHT);
        add(terminal);
        pack();
        screen = new StartScreen(WIDTH, HEIGHT);
        addKeyListener(this);
        repaint();
    }

    public static void main(final String[] args) {
        Main app = new Main();
        app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        app.setVisible(true);
    }

    @Override
    public void repaint() {
        terminal.clear();
        screen.displayOutput(terminal);
        super.repaint();
    }

    @Override
    public void keyPressed(final KeyEvent e) {
        screen = screen.respondToUserInput(e);
        this.repaint();
    }

    @Override
    public void keyReleased(final KeyEvent e) { }

    @Override
    public void keyTyped(final KeyEvent e) { }
}
