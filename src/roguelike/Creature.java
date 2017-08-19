package roguelike;

import java.awt.*;

public abstract class Creature {
    int x;
    int y;
    final Level level;
    private final char character;
    private final Color foreground;
    private final Color background;

    public Creature(Level level, int startingX, int startingY, char c, Color fg, Color bg) {
        this.level = level;
        this.x = startingX;
        this.y = startingY;
        this.character = c;
        this.foreground = fg;
        this.background = bg;
        this.level.getTile(x, y).setCreature(this);
    }

    public void moveTo(int newX, int newY) {
        Tile t;
        try {
            t = this.level.getTile(newX, newY);
        } catch (IndexOutOfBoundsException e) {
            System.err.println("Tile out of bounds");
            return;
        }
        if (t.isWalkable()) {
            this.level.getTile(this.x, this.y).removeCreature();
            this.x = newX;
            this.y = newY;
            t.setCreature(this);
        }
    }

    public void moveLeft() {
        this.moveTo(this.x - 1, this.y);
    }

    public void moveRight() {
        this.moveTo(this.x + 1, this.y);
    }

    public void moveUp() {
        this.moveTo(this.x, this.y - 1);
    }

    public void moveDown() {
        this.moveTo(this.x, this.y + 1);
    }

    public char getCharacter() {
        return character;
    }

    public Color getForeground() {
        return foreground;
    }

    public Color getBackground() {
        return background;
    }
}
