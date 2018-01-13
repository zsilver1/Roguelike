package roguelike;

import java.awt.*;

public class Tile {


    private final int x;
    private final int y;
    public boolean marked;

    private static final char DEFAULT_CHAR = ' ';
    //private static final Color DEFAULT_EXPLORED_BACKGROUND = Color.DARK_GRAY;
    //private static final Color DEFAULT_EXPLORED_FOREGROUND = Color.LIGHT_GRAY;
    private static final Color DEFAULT_EXPLORED_BACKGROUND = Color.BLACK;
    private static final Color DEFAULT_EXPLORED_FOREGROUND = Color.BLACK;
    private static final Color DEFAULT_VISIBLE_FOREGROUND = Color.LIGHT_GRAY.brighter();
    private static final Color DEFAULT_VISIBLE_BACKGROUND = Color.BLUE.darker();
        private static final Color DEFAULT_UNEXPLORED_FOREGROUND = Color.BLACK;
    private static final Color DEFAULT_UNEXPLORED_BACKGROUND = Color.BLACK;
    private static final boolean DEFAULT_WALKABLE = true;
    private static final boolean DEFAULT_TRANSPARENT = true;

    private char curChar;
    private Color curForeground;
    // the foreground color of whatever object or actor is present
    private Color curBackground;

    private Actor actor;
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
        this.curBackground = DEFAULT_UNEXPLORED_BACKGROUND;
    }

    public Tile(int x, int y, GameObject g) {
        this(x, y);
        this.gameObject = g;
    }

    public char getChar() {
        return this.curChar;
    }

    public Actor getActor() {
        return actor;
    }

    public void setActor(Actor actor) {
        this.actor = actor;
        this.updateGraphic();
    }

    public boolean hasActor() {
        return (this.actor != null);
    }

    public void removeActor() {
        this.actor = null;
        this.updateGraphic();
    }

    public GameObject getGameObject() {
        return this.gameObject;
    }

    public void setGameObject(GameObject gameObject) {
        this.gameObject = gameObject;
        this.updateGraphic();
        this.walkable = this.gameObject.isWalkable();
        this.transparent = this.gameObject.isTransparent();

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

    public void setToNotExplored() {
        this.explored = false;
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
        if (this.hasActor()) {
            this.curChar = this.actor.getCharacter();
        } else if (this.hasGameObject()) {
            this.curChar = this.gameObject.getCharacter();
        } else {
            this.curChar = DEFAULT_CHAR;
        }

        this.updateColorGraphic();
    }

    private void updateColorGraphic() {
        if (this.explored) {
            if (this.visible) {
                this.curForeground = DEFAULT_VISIBLE_FOREGROUND;
                this.curBackground = DEFAULT_VISIBLE_BACKGROUND;
            } else {
                this.curForeground = DEFAULT_EXPLORED_FOREGROUND;
                this.curBackground = DEFAULT_EXPLORED_BACKGROUND;
            }
        } else {
            this.curForeground = DEFAULT_UNEXPLORED_FOREGROUND;
            this.curBackground = DEFAULT_UNEXPLORED_BACKGROUND;
        }
    }

//    private void updateGraphic() {
//        // FIXME fix colors and stuff
//        if (!this.explored && !this.visible) {
//            this.curForeground = DEFAULT_UNEXPLORED_FOREGROUND;
//            this.curBackground = DEFAULT_UNEXPLORED_BACKGROUND;
//            this.curChar = DEFAULT_CHAR;
//        } else if (!this.explored) {
//            this.curForeground = DEFAULT_UNEXPLORED_FOREGROUND;
//            this.curBackground = DEFAULT_UNEXPLORED_BACKGROUND;
//            this.curChar = DEFAULT_CHAR;
//        } else if (this.hasCreature()) {
//            this.curChar = this.actor.getCharacter();
//            if (this.visible) {
//                this.curForeground = this.actor.getForeground();
//                this.curBackground = DEFAULT_VISIBLE_BACKGROUND;
//            } else {
//                this.curForeground = this.actor.getForeground().darker();
//                this.curBackground = DEFAULT_EXPLORED_BACKGROUND;
//            }
//        } else if (this.hasGameObject()) {
//            this.curChar = this.gameObject.getCharacter();
//            if (this.visible) {
//                this.curForeground = this.gameObject.getForeground();
//                this.curBackground = DEFAULT_VISIBLE_BACKGROUND;
//            } else {
//                this.curForeground = this.gameObject.getForeground().darker();
//                this.curBackground = DEFAULT_EXPLORED_BACKGROUND;
//            }
//        } else if (this.visible){
//            this.curForeground = DEFAULT_VISIBLE_FOREGROUND;
//            this.curBackground = DEFAULT_VISIBLE_BACKGROUND;
//            this.curChar = DEFAULT_CHAR;
//        } else {
//            // explored but not visible
//            this.curForeground = DEFAULT_EXPLORED_FOREGROUND;
//            this.curBackground = DEFAULT_EXPLORED_BACKGROUND;
//            this.curChar = DEFAULT_CHAR;
//        }
//    }
}
