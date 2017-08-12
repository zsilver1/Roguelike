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
    private float illumination;
    private Creature creature;

    /**
     * Create a new tile.
     * @param x x location
     * @param y y location
     */
    Tile(int x, int y, Tile.Type type) {
        this.x = x;
        this.y = y;
        this.type = type;
        this.curChar = this.type.getChar();
    }

    public char getChar() {
        return this.curChar;
    }

    public void setCreature(Creature creature) {
        this.creature = creature;
        this.curChar = creature.getCharacter();
    }

    public void removeCreature(Creature creature) {
        this.creature = null;
        this.curChar = this.type.getChar();
    }



    /**
     * An enum representing tile types.
     */
    public enum Type {
        FLOOR('.'),
        WALL('#');

        private char c;
        private Color foreground;
        private Color background;

        Type(char c, Color foreground, Color background) {
            this.c = c;
            this.foreground = foreground;
            this.background = background;
        }

        public char getChar() {
            return this.c;
        }
    }
}
