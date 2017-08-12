package roguelike;

import java.util.LinkedList;

public class Level {
    private Tile[][] tiles;
    private final int width;
    private final int height;
    private LinkedList<Creature> creatureList;
    private Player player;

    public Level(int width, int height) {
        this.width = width;
        this.height = height;
        this.creatureList = new LinkedList<>();

        //LevelGenerator gen = new BasicLevelGenerator(width, height);
        LevelGenerator gen = new RoomLevelGenerator(width, height);
        this.tiles = gen.generate();
        this.player = new Player(this, gen.getStartingPlayerX(), gen.getStartingPlayerY());
    }

    public Tile getTile(int x, int y) {
        if (x >= 0 && x < this.width && y >= 0 && y < this.height) {
            return this.tiles[x][y];
        } else {
            throw new IndexOutOfBoundsException("Invalid tile");
        }
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Player getPlayer() {
        return player;
    }
}
