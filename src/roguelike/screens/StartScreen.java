package roguelike.screens;

import asciiPanel.AsciiPanel;
import java.awt.event.KeyEvent;

public class StartScreen implements Screen {
    private int width;
    private int height;

    public StartScreen(int width, int height) {
        this.width = width;
        this.height = height;
    }

    @Override
    public void displayOutput(AsciiPanel terminal) {
        terminal.writeCenter("-- press [enter] to start --", this.height / 2);
    }

    @Override
    public Screen respondToUserInput(KeyEvent key) {
        return key.getKeyCode() == KeyEvent.VK_ENTER ? new PlayScreen(this.width, this.height) : this;
    }
}
