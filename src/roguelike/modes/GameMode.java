package roguelike.modes;

import asciiPanel.AsciiPanel;
import java.awt.event.KeyEvent;

public abstract class GameMode {
    public abstract GameMode respondToUserInput(KeyEvent k);
    public abstract void displayScreen(AsciiPanel terminal);
}
