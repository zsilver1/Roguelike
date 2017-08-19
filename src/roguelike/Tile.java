package roguelike;

import java.awt.*;

public class Tile {


    private final int x;
    private final int y;
    public boolean marked;

    private static final char DEFAULT_CHAR = '.';
    private static final Color DEFAULT_EXPLORED_FOREGROUND = Color.DARK_GRAY;
    private static final Color DEFAULT_UNEXPLORED_FOREGROUND = Color.BLACK;
    private static final Color DEFAULT_BACKGROUND = Color.BLACK;
    private static final boolean DEFAULT_WALKABLE = true;
    private static final boolean DEFAULT_TRANSPARENT = true;

    private char curChar;
    private Color curForeground;
    // the foreground color of whatever object or creature is present
    private Color curBackground;

    private Creature creature;
    private GameObject gameObject;

    private boolean walkable = true;
    private boolean transparent = true;
    private boolean explored = false;
    private boolean visible = false;
    private float illumination;


    public Tile(int x, int y) {
        this.x = x;
        this.y = y;
        this.curChar = DEFAULT_CHAR;
        this.curForeground = DEFAULT_UNEXPLORED_FOREGROUND;
        this.curBackground = DEFAULT_BACKGROUND;
    }

    public Tile(int x, int y, GameObject g) {
        this(x, y);
        this.gameObject = g;
    }

    public char getChar() {
        return this.curChar;
    }

    public Creature getCreature() {
        return creature;
    }

    public void setCreature(Creature creature) {
        this.creature = creature;
        this.updateGraphic();
    }

    public boolean hasCreature() {
        return (this.creature != null);
    }

    public void removeCreature() {
        this.creature = null;
        this.updateGraphic();
    }

    public GameObject getGameObject() {
        return this.gameObject;
    }

    public void setGameObject(GameObject gameObject) {
        this.gameObject = gameObject;
        this.updateGraphic();
        this.walkable = this.gameObject.isWalkable();

    }

    public boolean hasGameObject() {
        return (this.gameObject != null);
    }

    public void removeGameObject() {
        this.gameObject = null;
        this.updateGraphic();
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
        this.updateGraphic();
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
        this.updateGraphic();
    }

    public boolean isVisible() {
        return visible;
    }

    public void setToVisible() {
        this.visible = true;
        this.updateGraphic();
    }

    public void setToNotVisible() {
        this.visible = false;
        this.updateGraphic();
    }

    private void updateGraphic() {
        if (!this.explored) {
            this.curForeground = DEFAULT_UNEXPLORED_FOREGROUND;
            this.curChar = DEFAULT_CHAR;
        } else if (this.hasCreature() && this.visible) {
            this.curForeground = this.creature.getForeground();
            this.curChar = this.creature.getCharacter();
        } else if (this.hasGameObject()) {
            if (this.visible) {
                this.curForeground = this.gameObject.getForeground();
            }
            else {
                this.curForeground = DEFAULT_EXPLORED_FOREGROUND;
            }
            this.curChar = this.gameObject.getCharacter();
        } else {
            this.curForeground = DEFAULT_EXPLORED_FOREGROUND;
            this.curChar = DEFAULT_CHAR;
        }
    }
}
