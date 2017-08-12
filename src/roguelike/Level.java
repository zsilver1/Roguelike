package roguelike;

import java.util.LinkedList;

public class Level {
    private Tile[][] tiles;
    private int width;
    private int height;
    private LinkedList<Creature> creatureList;
    private Player player;

    public Level(int width, int height) {
        this.width = width;
        this.height = height;
        this.creatureList = new LinkedList<>();

        LevelGenerator gen = new LevelGenerator(width, height);
        this.tiles = gen.generate();
        this.player = new Player(this, gen.getStartingPlayerX(), gen.getStartingPlayerY());
    }

    public Tile getTile(int x, int y) {
        return this.tiles[x][y];
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
