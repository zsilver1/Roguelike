package roguelike.generators;

import roguelike.Tile;

import java.util.ArrayList;

public class BasicLevelGenerator extends LevelGenerator{

    public BasicLevelGenerator(int width, int height) {
        super(width, height);
    }

    public ArrayList<Tile> generate() {
        this.initMap();
        this.hollowRect(0, 0, this.width, this.height, 2);
        this.hollowRect(this.width / 2 - 4, this.height / 2 - 4, 4, 4, 1);
        this.startingPlayerX = 50;
        this.startingPlayerY = 20;
        return this.flatten(this.tiles);
    }
}
