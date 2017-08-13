package roguelike.generators;

import roguelike.Tile;

public class BasicLevelGenerator extends LevelGenerator{

    BasicLevelGenerator(int width, int height) {
        super(width, height);
    }

    public Tile[][] generate() {
        this.fillMapWith(Tile.Type.FLOOR);
        this.hollowRect(0, 0, this.width, this.height, 2, Tile.Type.WALL);
        this.filledRect(10, 10, 3, 3, Tile.Type.WALL);
        this.filledRect(this.width - 10, 10, 3, 3, Tile.Type.WALL);
        this.filledRect(10, this.height - 10, 3, 3, Tile.Type.WALL);
        this.filledRect(this.width - 10, this.height - 10, 3, 3, Tile.Type.WALL);
        this.hollowRect(this.width / 2 - 4, this.height / 2 - 4, 4, 4, 1, Tile.Type.WALL);
        this.startingPlayerX = 50;
        this.startingPlayerY = 20;
        return this.tiles;
    }
}
