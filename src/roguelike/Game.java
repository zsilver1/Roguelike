package roguelike;

import javax.swing.JFrame;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import asciiPanel.AsciiPanel;
import roguelike.modes.GameMode;
import roguelike.modes.PlayMode;

public class Game extends JFrame implements KeyListener {
    private AsciiPanel terminal;

    //TODO change game modes to singleton pattern?
    private GameMode gameMode;
    private Level curLevel;

    // represent the terminal width and height, given to
    // every screen as initialization input
    public static final int WIDTH = 175;
    public static final int HEIGHT = 55;

    private Game() {
        super();
        this.terminal = new AsciiPanel(WIDTH, HEIGHT);
        this.curLevel = new Level(WIDTH * 4 / 5, HEIGHT);
        this.gameMode = new PlayMode(this.curLevel);
        add(terminal);
        pack();
        addKeyListener(this);
        this.repaint();
    }

    public static void main(final String[] args) {
        Game app = new Game();
        app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        app.setVisible(true);
    }

    @Override
    public void repaint() {
        terminal.clear();
        this.gameMode.displayScreen(this.terminal);
        super.repaint();
    }

    @Override
    public void keyPressed(final KeyEvent e) {
        this.gameMode = this.gameMode.respondToUserInput(e);
        this.repaint();
    }

    @Override
    public void keyReleased(final KeyEvent e) { }

    @Override
    public void keyTyped(final KeyEvent e) { }
}
