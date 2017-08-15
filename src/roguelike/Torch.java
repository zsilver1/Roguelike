package roguelike;

import java.awt.Color;

public class Torch extends GameObject {

    private static final int RADIUS = 10;
    private LightSource lightSource;
    private Level level;

    public Torch(int x, int y, Level level) {
        super(x, y,
                true, true,
                '*', Color.ORANGE, Color.BLACK);
        this.lightSource = new LightSource(this.x, this.y, RADIUS, level);
        this.level = level;
    }
}
