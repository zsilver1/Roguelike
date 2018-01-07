package roguelike.generators;

import roguelike.Level;
import roguelike.Tile;
import roguelike.Wall;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public abstract class LevelGenerator {

    Tile[][] tiles;
    final int width;
    final int height;
    Level level;
    int startingPlayerX;
    int startingPlayerY;

    LevelGenerator(int width, int height) {
        this.width = width;
        this.height = height;
        this.tiles = new Tile[this.width][this.height];
    }

    LevelGenerator(int width, int height, Level level) {
        this(width, height);
        this.level = level;
    }

    public abstract ArrayList<Tile> generate();

    public void fillMapWithWalls() {
        for (int x = 0; x < this.width; x++) {
            for (int y = 0; y < this.height; y++) {
                this.tiles[x][y].setGameObject(new Wall(x, y));
            }
        }
    }

    public ArrayList<Tile> flatten(Tile[][] t) {
        ArrayList<Tile> list = new ArrayList<>();
        for (Tile[] l : t) {
            Collections.addAll(list, l);
        }
        return list;
    }

    public void initMap() {
        for (int x = 0; x < this.width; x++) {
            for (int y = 0; y < this.height; y++) {
                this.tiles[x][y] = new Tile(x, y);
            }
        }
    }

    public void hollowRect(int left, int top, int width, int height, int thickness) {
        // first create rows
        for (int x = left; x - left < width; x++) {
            for (int y = top; y - top < thickness; y++) {
                this.tiles[x][y].setGameObject(new Wall(x, y));
            }
        }
        for (int x = left; x - left < width; x++) {
            for (int y = top + height - thickness; y - top - height + thickness < thickness; y++) {
                this.tiles[x][y].setGameObject(new Wall(x, y));
            }
        }
        // now columns
        for (int y = top; y - top < height; y++) {
            for (int x = left; x - left < thickness; x++) {
                this.tiles[x][y].setGameObject(new Wall(x, y));
            }
        }
        for (int y = top; y - top < height; y++) {
            for (int x = left + width - thickness; x - left - width + thickness < thickness; x++) {
                this.tiles[x][y].setGameObject(new Wall(x, y));
            }
        }
    }

    public int numNeighboringWalls(int x, int y, int within) {
        int number = 0;
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
                if (this.tiles[xi][yi].getGameObject() instanceof Wall) {
                    number++;
                }
            }
        }
        return number;
    }

    public int getStartingPlayerX() {
        return startingPlayerX;
    }

    public int getStartingPlayerY() {
        return startingPlayerY;
    }
}
