package roguelike;

import asciiPanel.AsciiPanel;

import java.awt.*;

public class Tile {


    /**
     * X coordinate of tile.
     */
    public final int x;

    /**
     * Y coordinate of tile.
     */
    public final int y;

    // Used for actual char value of tile, in case of creature or object
    private char curChar;
    private Type type;
    private Color foreground;
    private Color background;
    private boolean walkable;
    private float illumination;
    private Creature creature;

    /**
     * Create a new tile.
     * @param x x location
     * @param y y location
     */
    public Tile(int x, int y, Tile.Type type) {
        this.x = x;
        this.y = y;
        this.type = type;
        this.curChar = this.type.getChar();
        this.foreground = this.type.getForeground();
        this.background = this.type.getBackground();
        this.walkable = this.type.getWalkable();
    }

    public char getChar() {
        return this.curChar;
    }

    public void setCreature(Creature creature) {
        this.creature = creature;
        this.curChar = creature.getCharacter();
        this.foreground = creature.getForeground();
        this.background = creature.getBackground();
    }

    public void removeCreature() {
        this.creature = null;
        this.curChar = this.type.getChar();
        this.foreground = this.type.getForeground();
        this.background = this.type.getBackground();
    }

    public Color getForeground() {
        return foreground;
    }

    public Color getBackground() {
        return background;
    }

    public Type getType() {
        return this.type;
    }

    public void setType(Tile.Type newType) {
        this.type = newType;
        this.curChar = this.type.getChar();
        this.foreground = this.type.getForeground();
        this.background = this.type.getBackground();
        this.walkable = this.type.getWalkable();
    }

    public boolean isWalkable() {
        return walkable;
    }


    /**
     * An enum representing tile types.
     */
    public enum Type {
        FLOOR('.', AsciiPanel.brightBlack, AsciiPanel.black, true),
        WALL('#', AsciiPanel.yellow, AsciiPanel.black, false);

        private char c;
        private Color foreground;
        private Color background;
        private Boolean walkable;

        Type(char c, Color foreground, Color background, boolean walkable) {
            this.c = c;
            this.foreground = foreground;
            this.background = background;
            this.walkable = walkable;
        }

        public char getChar() {
            return this.c;
        }

        public Color getBackground() {
            return background;
        }

        public Color getForeground() {
            return foreground;
        }

        public Boolean getWalkable() {
            return walkable;
        }
    }
}
