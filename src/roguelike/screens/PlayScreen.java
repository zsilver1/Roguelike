package roguelike.screens;

import asciiPanel.AsciiPanel;
import roguelike.Level;
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
        this.level = new Level(this.width, this.height);
        this.utilityScreen = new UtilityScreen(this.width, this.height);
    }

    @Override
    public void displayOutput(AsciiPanel terminal) {
        this.displayLevel(terminal);
        this.utilityScreen.addLineBreaks(4);
        this.utilityScreen.addLine("HEALTH: 34");
        this.utilityScreen.addLine("SNEAKING");
        this.utilityScreen.addLineBreaks(4);
        this.utilityScreen.addLine("DOWN");
        this.utilityScreen.displayOutput(terminal);
    }

    @Override
    public Screen respondToUserInput(KeyEvent key) {
        return this;
    }

    private void displayLevel(AsciiPanel terminal) {
        for (int x = this.left; x < this.width; x++) {
            for (int y = 0; y < this.height; y++) {
                terminal.write(this.level.getTile(x, y).getChar(), x, y);
            }
        }
    }
}
