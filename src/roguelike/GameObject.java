package roguelike;

import java.awt.Color;

public class GameObject {
    int x;
    int y;
    private boolean transparent;
    private boolean walkable;
    private char character;
    private Color foreground;
    private Color background;

    public GameObject(int x, int y,
                      boolean transparent, boolean walkable,
                      char character, Color foreground, Color background) {
        this.x = x;
        this.y = y;
        this.transparent = transparent;
        this.walkable = walkable;
        this.character = character;
        this.foreground = foreground;
        this.background = background;
    }


    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isTransparent() {
        return transparent;
    }

    public boolean isWalkable() {
        return walkable;
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
