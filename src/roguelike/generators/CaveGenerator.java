package roguelike.generators;

import roguelike.LightSource;
import roguelike.Tile;

import java.util.*;

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

        if (this.floodFill()) {
            this.fillMapWith(Tile.Type.FLOOR);
            return this.generate();
        }
        this.clearSingleWalls();

        this.placeLights(25);
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

    private void placeLights(int numLights) {
        for (int i = 0; i < numLights; i++) {
            int x = this.random.nextInt(this.width);
            int y = this.random.nextInt(this.height);
            int neighbors = this.numNeighboring(x, y, 1, Tile.Type.WALL);
            while (this.tiles[x][y].getType() != Tile.Type.FLOOR ||
                    neighbors < 2 || neighbors > 5) {
                x = this.random.nextInt(this.width);
                y = this.random.nextInt(this.height);
                neighbors = this.numNeighboring(x, y, 1, Tile.Type.WALL);
            }
            this.tiles[x][y].setLightSource(new LightSource());
        }
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
                neighbors = this.numNeighboring(x, y, 1, Tile.Type.WALL);
                neighbors2 = this.numNeighboring(x, y, 2, Tile.Type.WALL);
                if (neighbors >= 5 || neighbors2 <= 3){
                    newTiles[x][y].setType(Tile.Type.WALL);
                } else {
                    newTiles[x][y].setType(Tile.Type.FLOOR);
                }
            }
        }
        this.tiles = newTiles;
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
                neighbors = this.numNeighboring(x, y, 1, Tile.Type.WALL);
                if (neighbors >= 5){
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
                if (this.numNeighboring(x, y, 1, Tile.Type.WALL) == 1) {
                    this.tiles[x][y].setType(Tile.Type.FLOOR);
                }
            }
        }
    }

    // returns true if world should be regenerated
    private boolean floodFill() {
        int startX = this.random.nextInt(this.width);
        int startY = this.random.nextInt(this.height);
        while (this.tiles[startX][startY].getType() == Tile.Type.WALL) {
            startX = this.random.nextInt(this.width);
            startY = this.random.nextInt(this.height);
        }
        float area = this.width * this.height;
        int numVisited = this.visit(startX, startY);
        float frac = numVisited / area;
        if (frac < 0.3 || frac > 0.6) {
            return true;
        }
        for (int x = 0; x < this.width; x++) {
            for (int y = 0; y < this.height; y++) {
                if (!this.tiles[x][y].marked) {
                    this.tiles[x][y].setType(Tile.Type.WALL);
                }
            }
        }
        return false;
    }

    private int visit(int x, int y) {
        Stack<Tile> s = new Stack<>();
        s.push(this.tiles[x][y]);
        Tile t;
        int numVisited = 0;
        while (!s.empty()) {
            t = s.pop();
            if (t.marked) {
                continue;
            }
            t.marked = true;
            numVisited++;
            if (this.tiles[t.x-1][t.y].getType() != Tile.Type.WALL) {
                s.push(this.tiles[t.x-1][t.y]);
            }
            if (this.tiles[t.x+1][t.y].getType() != Tile.Type.WALL) {
                s.push(this.tiles[t.x+1][t.y]);
            }
            if (this.tiles[t.x][t.y-1].getType() != Tile.Type.WALL) {
                s.push(this.tiles[t.x][t.y-1]);
            }
            if (this.tiles[t.x][t.y+1].getType() != Tile.Type.WALL) {
                s.push(this.tiles[t.x][t.y+1]);
            }
        }
        return numVisited;
    }
}
