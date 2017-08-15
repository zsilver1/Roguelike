package roguelike;

public class LightSource {
    private int x;
    private int y;
    private final Level level;

    public LightSource(int x, int y, Level level) {
        this.x = x;
        this.y = y;
        this.level = level;
        this.level.getTile(x, y).setLightSource(this);
    }
}
