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

    /**
     * Used for various algorithms.
     */
    public boolean marked;

    // Used for actual char value of tile, in case of creature or object
    private char curChar;
    private Type type;
    private Color foreground;
    private Color background;
    private boolean walkable;
    private boolean transparent;
    private boolean explored;
    private float illumination;
    private Creature creature;
    private LightSource lightSource;

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
        this.transparent = this.type.getTransparent();
    }

    public char getChar() {
        return this.curChar;
    }

    public Creature getCreature() {
        return creature;
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
        return this.walkable;
    }

    public boolean isTransparent() {
        return this.transparent;
    }

    public boolean isExplored() {
        return explored;
    }

    public void explore() {
        this.explored = true;
    }

    public LightSource getLightSource() {
        return lightSource;
    }

    public void setLightSource(LightSource l) {
        this.lightSource = l;
        this.curChar = '*';
        this.foreground = Color.ORANGE;
        this.background = Color.BLACK;
    }

    public void removeLightSource(LightSource lightSource) {
        this.lightSource = null;
        this.curChar = this.type.getChar();
        this.foreground = this.type.getForeground();
        this.background = this.type.getBackground();
    }


    /**
     * An enum representing tile types.
     */
    public enum Type {
        FLOOR((char)249, Color.GRAY, Color.black, true, true),
        WALL((char)219, Color.YELLOW, Color.black, false, false),
        FLOOR_UNEXPLORED(' ', Color.black, Color.black, true, true),
        WALL_UNEXPLORED(' ', AsciiPanel.black, AsciiPanel.black, false, false),
        FLOOR_EXPLORED((char)249, Color.DARK_GRAY, AsciiPanel.black, true, true),
        WALL_EXPLORED((char)219, Color.DARK_GRAY, AsciiPanel.black, true, true);

        private char c;
        private Color foreground;
        private Color background;
        private Boolean walkable;
        private Boolean transparent;

        Type(char c, Color foreground, Color background, boolean walkable, boolean transparent) {
            this.c = c;
            this.foreground = foreground;
            this.background = background;
            this.walkable = walkable;
            this.transparent = transparent;
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

        public Boolean getTransparent() {
            return transparent;
        }
    }
}
