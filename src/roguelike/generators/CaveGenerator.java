package roguelike.generators;

import roguelike.Tile;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

public class CaveGenerator extends LevelGenerator {

    private Random random = new Random();

    public CaveGenerator(int width, int height) {
        super(width, height);
    }

    @Override
    public Tile[][] generate() {
        this.fillWithWallsAtProb(48);
        // create strips to ensure path
        this.filledRect(0, this.height/2 - 2, this.width, 2, Tile.Type.FLOOR);
        this.filledRect(0, 1, this.width, 2, Tile.Type.FLOOR);
        this.filledRect(0, this.height - 5, this.width, 2, Tile.Type.FLOOR);

        // surround with walls
        this.hollowRect(0, 0, this.width, this.height, 1, Tile.Type.WALL);

        for (int i = 0; i < 4; i++) {
            this.firstStep();
        }
        for (int i = 0; i < 3; i++) {
            this.secondStep();
        }
        this.clearSingleWalls();

        this.placePlayer();
        return this.tiles;
    }

    private void placePlayer() {
        int startX = this.random.nextInt(this.width / 4);
        int startY = (3 * this.height / 4) + this.random.nextInt(this.height / 4);
        while (this.tiles[startX][startY].getType() == Tile.Type.WALL) {
            startX = this.random.nextInt(this.width / 4);
            startY = (3 * this.height / 4) + this.random.nextInt(this.height / 4);
        }
        this.startingPlayerX = startX;
        this.startingPlayerY = startY;
    }

    private void fillWithWallsAtProb(int prob) {
        if (prob >= 100) {
            prob = 99;
        } else if (prob < 0) {
            prob = 0;
        }
        int num;
        for (int x = 0; x < this.width; x++) {
            for (int y = 0; y < this.height; y++) {
                num = this.random.nextInt(100);
                if (num > prob) {
                    this.tiles[x][y] = new Tile(x, y, Tile.Type.FLOOR);
                } else {
                    this.tiles[x][y] = new Tile(x, y, Tile.Type.WALL);
                }
            }
        }
    }

    private void secondStep() {
        int neighbors;
        Tile[][] newTiles = new Tile[this.width][this.height];
        for (int x = 0; x < this.width; x++) {
            for (int y = 0; y < this.height; y++) {
                newTiles[x][y] = new Tile(x, y, this.tiles[x][y].getType());
            }
        }
        for (int x = 1; x < this.width - 1; x++) {
            for (int y = 1; y < this.height - 1; y++) {
                neighbors = this.numNeighboringWalls(x, y, 1);
                if (neighbors >= 5){
                    newTiles[x][y].setType(Tile.Type.WALL);
                } else {
                    newTiles[x][y].setType(Tile.Type.FLOOR);
                }
            }
        }
        this.tiles = newTiles;
    }

    private void firstStep() {
        int neighbors;
        int neighbors2;
        Tile[][] newTiles = new Tile[this.width][this.height];
        for (int x = 0; x < this.width; x++) {
            for (int y = 0; y < this.height; y++) {
                newTiles[x][y] = new Tile(x, y, this.tiles[x][y].getType());
            }
        }
        for (int x = 1; x < this.width - 1; x++) {
            for (int y = 1; y < this.height - 1; y++) {
                neighbors = this.numNeighboringWalls(x, y, 1);
                neighbors2 = this.numNeighboringWalls(x, y, 2);
                if (neighbors >= 5 || neighbors2 <= 3){
                    newTiles[x][y].setType(Tile.Type.WALL);
                } else {
                    newTiles[x][y].setType(Tile.Type.FLOOR);
                }
            }
        }
        this.tiles = newTiles;
    }

    private void clearSingleWalls() {
        for (int x = 0; x < this.width; x++) {
            for (int y = 0; y < this.height; y++) {
                if (this.numNeighboringWalls(x, y, 1) == 1) {
                    this.tiles[x][y].setType(Tile.Type.FLOOR);
                }
            }
        }
    }

    private int numNeighboringWalls(int x, int y, int within) {
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
                if (this.tiles[xi][yi].getType() == Tile.Type.WALL) {
                    number++;
                }
            }
        }
        return number;
    }
}
