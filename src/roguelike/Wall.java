package roguelike;

import java.awt.Color;

public class Wall extends GameObject {
    public Wall(int x, int y) {
        super(x, y,
                false, false,
                (char)219, Color.LIGHT_GRAY, Color.BLACK);
    }
}
