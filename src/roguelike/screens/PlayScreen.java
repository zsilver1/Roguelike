package roguelike.screens;

import asciiPanel.AsciiPanel;
import roguelike.Level;
import roguelike.Player;

import java.awt.event.KeyEvent;

public class PlayScreen implements Screen {
    private Level level;
    private int width;
    private int height;
    private int left;
    private UtilityScreen utilityScreen;

    PlayScreen(int terminalWidth, int terminalHeight) {
        // playable screen width is right 4/5 of terminal
        this.width = terminalWidth;
        this.left = terminalWidth / 5;
        this.height = terminalHeight;
        this.level = new Level(this.width - this.left, this.height);
        this.utilityScreen = new UtilityScreen(this.width, this.height);
    }

    @Override
    public void displayOutput(AsciiPanel terminal) {
        this.displayLevel(terminal);
        this.utilityScreen.displayOutput(terminal);
    }

    @Override
    public Screen respondToUserInput(KeyEvent key) {
        Player p = this.level.getPlayer();
        switch (key.getKeyCode()) {
            case KeyEvent.VK_LEFT: p.moveLeft(); break;
            case KeyEvent.VK_RIGHT: p.moveRight(); break;
            case KeyEvent.VK_DOWN: p.moveDown(); break;
            case KeyEvent.VK_UP: p.moveUp(); break;
            default: break;
        }
        return this;
    }

    private void displayLevel(AsciiPanel terminal) {
        int levelX;
        for (int x = this.left; x < this.width; x++) {
            for (int y = 0; y < this.height; y++) {
                levelX = x - this.left;
                terminal.write(
                        this.level.getTile(levelX, y).getChar(),
                        x,
                        y,
                        this.level.getTile(levelX, y).getForeground(),
                        this.level.getTile(levelX, y).getBackground());
            }
        }
    }
}
