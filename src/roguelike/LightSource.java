package roguelike;

public class LightSource {
    private int x;
    private int y;
    private int radius;
    private final Level level;

    public LightSource(int x, int y, int radius, Level level) {
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.level = level;
    }

    public void update() {

    }
}
