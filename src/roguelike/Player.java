package roguelike;

public class Player extends Creature {
    public Player(Level level, int startingX, int startingY) {
        super(level, startingX, startingY, '@');
    }
}
