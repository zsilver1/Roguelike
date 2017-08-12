package roguelike;

public class LevelGenerator {
    private Tile[][] tiles;
    private int width;
    private int height;
    private int startingPlayerX;
    private int startingPlayerY;

    LevelGenerator(int width, int height) {
        this.width = width;
        this.height = height;
        this.tiles = new Tile[this.width][this.height];
    }

    public Tile[][] generate() {
        for (int x = 0; x < this.width; x++) {
            for (int y = 0; y < this.height; y++) {
                if (x == 0 || x == this.width - 1 || y == 0 || y == this.height - 1) {
                    this.tiles[x][y] = new Tile(x, y, Tile.Type.WALL);
                } else {
                    this.tiles[x][y] = new Tile(x, y, Tile.Type.FLOOR);
                }
            }
        }
        this.startingPlayerX = 50;
        this.startingPlayerY = 20;
        return this.tiles;
    }

    public int getStartingPlayerX() {
        return startingPlayerX;
    }

    public int getStartingPlayerY() {
        return startingPlayerY;
    }
}
