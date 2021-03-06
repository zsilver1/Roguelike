package roguelike;

import roguelike.generators.BasicLevelGenerator;
import roguelike.generators.CaveGenerator;
import roguelike.generators.LevelGenerator;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.ListIterator;

public class Level {
    private ArrayList<Tile> tiles;
    private final int width;
    private final int height;
    private LinkedList<Actor> actors;
    private LinkedList<LightSource> lightSources;
    private Player player;

    public Level(int width, int height) {
        this.width = width;
        this.height = height;
        this.actors = new LinkedList<>();
        this.lightSources = new LinkedList<>();

        //LevelGenerator gen = new BasicLevelGenerator(width, height);
        LevelGenerator gen = new CaveGenerator(width, height, this);
        this.tiles = gen.generate();
        this.lightSources = gen.getLightSources();
        this.recomputeLight();
        this.player = new Player(this, gen.getStartingPlayerX(), gen.getStartingPlayerY());
    }

    public Tile getTile(int x, int y) {
        if (this.isValid(x, y)) {
            return this.getTileHelper(x, y);
        } else if (this.isValidX(x)) {
            if (y < 0) {
                return this.getTileHelper(x, 0);
            } else {
                return this.getTileHelper(x, this.height - 1);
            }
        } else if (this.isValidY(y)) {
            if (x < 0) {
                return this.getTileHelper(0, y);
            } else {
                return this.getTileHelper(this.width - 1, y);
            }
        } else {
            if (x < 0 && y < 0) {
                return this.getTileHelper(0, 0);
            } else if (x > this.width-1 && y < 0) {
                return this.getTileHelper(this.width - 1, 0);
            } else if (x > this.width-1 && y > this.height-1) {
                return this.getTileHelper(this.width - 1, this.height - 1);
            } else if (x < 0 && y > this.height-1) {
                return this.getTileHelper(0, this.height - 1);
            }
        }
        throw new IndexOutOfBoundsException("Invalid Tile");
    }

    private Tile getTileHelper(int x, int y) {
        return this.tiles.get(x * this.height + y);
    }

    public boolean isValid(int x, int y) {
        return (x >= 0 && x < this.width && y >= 0 && y < this.height);
    }

    private boolean isValidX(int x) {
        return (x >= 0 && x < this.width);
    }

    private boolean isValidY(int y) {
        return (y >= 0 && y < this.height);
    }

    public ArrayList<Tile> getTiles(int x, int y, int within) {
        return this.getTiles(x, y, within, false);
    }

    public ArrayList<Tile> getTiles(int x, int y, int within, boolean excludingCenter) {
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
                a.add(this.getTile(xi, yi));
            }
        }
        return a;
    }

    public void setTilesToNotExplored() {
        for (int x = 0; x < this.width; x++) {
            for (int y = 0; y < this.height; y++) {
                this.getTile(x, y).setToNotExplored();
            }
        }
    }

    public void recomputeLight() {
        for (LightSource l : this.lightSources) {
            l.update();
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
