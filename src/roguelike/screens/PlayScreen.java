package roguelike.screens;

import asciiPanel.AsciiPanel;
import roguelike.Level;
import roguelike.Player;

import java.awt.event.KeyEvent;

public class PlayScreen {
    private UtilityScreen utilityScreen;
    private int width;
    private int height;
    private int left;

    public PlayScreen(int w, int h) {
        this.width = w;
        this.height = h;
        this.left = this.width / 5;
        this.utilityScreen = new UtilityScreen(this.width / 5, this.height);
    }

    public void displayScreen(AsciiPanel terminal, Level level) {
        this.utilityScreen.displayScreen(terminal);
        this.displayLevel(terminal, level);
    }

    private void displayLevel(AsciiPanel terminal, Level level) {
        int levelX;
        for (int x = this.left; x < this.width; x++) {
            for (int y = 0; y < this.height; y++) {
                levelX = x - this.left;
                terminal.write(
                        level.getTile(levelX, y).getChar(),
                        x,
                        y,
                        level.getTile(levelX, y).getForeground(),
                        level.getTile(levelX, y).getBackground());
            }
        }
    }
}
