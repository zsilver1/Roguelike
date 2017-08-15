package roguelike;

import roguelike.generators.CaveGenerator;
import roguelike.generators.LevelGenerator;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.ListIterator;

public class Level {
    private Tile[][] tiles;
    private final int width;
    private final int height;
    private LinkedList<Creature> creatures;
    private LinkedList<LightSource> lightSources;
    private Player player;

    public Level(int width, int height) {
        this.width = width;
        this.height = height;
        this.creatures = new LinkedList<>();
        this.lightSources = new LinkedList<>();

        //LevelGenerator gen = new BasicLevelGenerator(width, height);
        LevelGenerator gen = new CaveGenerator(width, height, this);
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

    public ListIterator<Tile> getTiles(int x, int y, int within) {
        return this.getTiles(x, y, within, false);
    }

    public ListIterator<Tile> getTiles(int x, int y, int within, boolean excludingCenter) {
        ArrayList<Tile> a = new ArrayList<>();
        int startX = 0;
        int startY = 0;
        int endX = this.width;
        int endY = this.height;
        if (x - within >= 0) {
            startX = x - within;
        }
        if (y - within >= 0) {
            startY = y - within;
        }
        if (x + within < this.width) {
            endX = x + within + 1;
        }
        if (y + within < this.height) {
            endY = y + within + 1;
        }
        for (int xi = startX; xi < endX; xi++) {
            for (int yi = startY; yi < endY; yi++) {
                if (excludingCenter && xi == x && yi == y) {
                    continue;
                }
                a.add(this.tiles[xi][yi]);
            }
        }
        return a.listIterator();
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
