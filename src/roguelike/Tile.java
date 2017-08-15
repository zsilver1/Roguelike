package roguelike;

import asciiPanel.AsciiPanel;

import java.awt.*;

public class Tile {


    private final int x;
    private final int y;
    public boolean marked;

    private static final char DEFAULT_CHAR = '.';
    private static final Color DEFAULT_EXPLORED_FOREGROUND = Color.GRAY;
    private static final Color DEFAULT_UNEXPLORED_FORGROUND = Color.BLACK;
    private static final Color DEFAULT_BACKGROUND = Color.BLACK;
    private static final boolean DEFAULT_WALKABLE = true;
    private static final boolean DEFAULT_TRANSPARENT = true;

    private char curChar;
    private Color curForeground;
    private Color curBackground;

    private Creature creature;
    private GameObject gameObject;

    private boolean walkable;
    private boolean transparent;
    private boolean explored;
    private float illumination;


    public Tile(int x, int y) {
        this.x = x;
        this.y = y;
        this.curChar = DEFAULT_CHAR;
        this.curForeground = DEFAULT_UNEXPLORED_FORGROUND;
        this.curBackground = DEFAULT_BACKGROUND;
    }

    public Tile(int x, int y, GameObject g) {
        this(x, y);
        this.gameObject = g;
    }

    public void updateGraphics() {
        // TODO
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
        this.curForeground = creature.getForeground();
        this.curBackground = creature.getBackground();
    }

    public boolean hasCreature() {
        return (this.creature != null);
    }

    public void removeCreature() {
        this.creature = null;
        if (this.hasGameObject()) {
            this.curChar = this.gameObject.getCharacter();
            this.curForeground = this.gameObject.getForeground();
            this.curBackground = this.gameObject.getBackground();
        } else {
            this.curChar = DEFAULT_CHAR;
            this.curForeground = (this.explored ? DEFAULT_EXPLORED_FOREGROUND : DEFAULT_UNEXPLORED_FORGROUND);
            this.curBackground = DEFAULT_BACKGROUND;
        }
    }

    public GameObject getGameObject() {
        return this.gameObject;
    }

    public void setGameObject(GameObject gameObject) {
        this.gameObject = gameObject;
        if (!this.hasCreature()) {
            this.curChar = gameObject.getCharacter();
            this.curForeground = gameObject.getForeground();
            this.curBackground = gameObject.getBackground();
        }
        this.walkable = gameObject.isWalkable();
        this.transparent = gameObject.isTransparent();

    }

    public boolean hasGameObject() {
        return (this.gameObject != null);
    }

    public void removeGameObject() {
        this.gameObject = null;
        if (!this.hasCreature()) {
            this.curChar = DEFAULT_CHAR;
            this.curForeground = (this.explored ? DEFAULT_EXPLORED_FOREGROUND : DEFAULT_UNEXPLORED_FORGROUND);
            this.curBackground = DEFAULT_BACKGROUND;
        }
        this.walkable = DEFAULT_WALKABLE;
        this.transparent = DEFAULT_TRANSPARENT;
    }

    public Color getForeground() {
        return this.curForeground;
    }

    public Color getBackground() {
        return this.curBackground;
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

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public float getIllumination() {
        return illumination;
    }

    public void setIllumination(float illumination) {
        this.illumination = illumination;
    }
}
