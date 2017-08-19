package roguelike.modes;

import asciiPanel.AsciiPanel;
import roguelike.Game;
import roguelike.Level;
import roguelike.Player;
import roguelike.screens.PlayScreen;
import java.awt.event.KeyEvent;

public class PlayMode extends GameMode {
    private Level level;
    private PlayScreen screen;
    //TODO ADD SCROLLING

    public PlayMode(Level level) {
        this.level = level;
        this.screen = new PlayScreen(Game.WIDTH, Game.HEIGHT);
    }

    @Override
    public GameMode respondToUserInput(KeyEvent k) {
        Player p = this.level.getPlayer();
        switch (k.getKeyCode()) {
            case KeyEvent.VK_LEFT: p.moveLeft(); break;
            case KeyEvent.VK_RIGHT: p.moveRight(); break;
            case KeyEvent.VK_DOWN: p.moveDown(); break;
            case KeyEvent.VK_UP: p.moveUp(); break;
            default: break;
        }
        return this;
    }

    @Override
    public void displayScreen(AsciiPanel terminal) {
        this.screen.displayScreen(terminal, this.level);
    }

    public void setLevel(Level level) {
        this.level = level;
    }
}
