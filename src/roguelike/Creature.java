package roguelike;

public abstract class Creature {
    private int x;
    private int y;
    private final Level level;
    private final char character;

    public Creature(Level level, int startingX, int startingY, char c) {
        this.level = level;
        this.x = startingX;
        this.y = startingY;
        this.character = c;
        this.level.getTile(x, y).setCreature(this);
    }

    public void moveTo(int newX, int newY) {
        this.level.getTile(this.x, this.y).removeCreature(this);
        this.x = newX;
        this.y = newY;
        this.level.getTile(this.x, this.y).setCreature(this);
    }

    public char getCharacter() {
        return character;
    }
}
