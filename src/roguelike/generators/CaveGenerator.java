package roguelike.generators;

import roguelike.Level;
import roguelike.Tile;
import roguelike.Torch;
import roguelike.Wall;

import java.util.*;

public class CaveGenerator extends LevelGenerator {

    private Random random = new Random();

    public CaveGenerator(int width, int height, Level l) {
        super(width, height, l);
    }

    @Override
    public ArrayList<Tile> generate() {
        this.fillWithWallsAtProb(45);

        // surround with walls
        this.hollowRect(0, 0, this.width, this.height, 1);

        for (int i = 0; i < 4; i++) {
            this.firstStep();
        }
        for (int i = 0; i < 3; i++) {
            this.secondStep();
        }

        if (this.floodFill()) {
            return this.generate();
        }
        this.clearSingleWalls();
        this.placeTorches(30);

        this.placePlayer();
        return this.flatten(this.tiles);
    }

    private Tile get(int x, int y) {
        if (x >= 0 && x < this.width && y >= 0 && y < this.height) {
            return this.tiles[x][y];
        }
        return null;
    }

    private boolean tile_is_walkable(int x, int y) {
        Tile t = this.get(x, y);
        return t != null && t.isWalkable();
    }

    private void placePlayer() {
        int startX = this.random.nextInt(this.width / 4);
        int startY = (3 * this.height / 4) + this.random.nextInt(this.height / 4);
        while (this.tiles[startX][startY].getGameObject() instanceof Wall) {
            startX = this.random.nextInt(this.width / 4);
            startY = (3 * this.height / 4) + this.random.nextInt(this.height / 4);
        }
        this.startingPlayerX = startX;
        this.startingPlayerY = startY;
    }

    private void placeTorches(int num) {
        for (int i = 0; i < num; i++) {
            int x = this.random.nextInt(this.width);
            int y = this.random.nextInt(this.height);
            int walls = this.numNeighboringWalls(x, y, 1);
            while (walls < 2 || walls > 5) {
                x = this.random.nextInt(this.width);
                y = this.random.nextInt(this.height);
                walls = this.numNeighboringWalls(x, y, 1);
            }
            this.tiles[x][y].setGameObject(new Torch(x, y, this.level));
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
                    this.tiles[x][y] = new Tile(x, y);
                } else {
                    this.tiles[x][y] = new Tile(x, y, new Wall(x, y));
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
                newTiles[x][y] = new Tile(x, y, this.tiles[x][y].getGameObject());
            }
        }
        for (int x = 1; x < this.width - 1; x++) {
            for (int y = 1; y < this.height - 1; y++) {
                neighbors = this.numNeighboringWalls(x, y, 1);
                neighbors2 = this.numNeighboringWalls(x, y, 2);
                if (neighbors >= 5 || neighbors2 <= 3){
                    newTiles[x][y].setGameObject(new Wall(x, y));
                } else {
                    newTiles[x][y].removeGameObject();
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
                newTiles[x][y] = new Tile(x, y, this.tiles[x][y].getGameObject());
            }
        }
        for (int x = 1; x < this.width - 1; x++) {
            for (int y = 1; y < this.height - 1; y++) {
                neighbors = this.numNeighboringWalls(x, y, 1);
                if (neighbors >= 5){
                    newTiles[x][y].setGameObject(new Wall(x, y));
                } else {
                    newTiles[x][y].removeGameObject();
                }
            }
        }
        this.tiles = newTiles;
    }

    private void clearSingleWalls() {
        for (int x = 0; x < this.width; x++) {
            for (int y = 0; y < this.height; y++) {
                if (this.numNeighboringWalls(x, y, 1) == 1) {
                    this.tiles[x][y].removeGameObject();
                }
            }
        }
    }

    // returns true if world should be regenerated
    private boolean floodFill() {
        int startX = this.random.nextInt(this.width);
        int startY = this.random.nextInt(this.height);
        while (this.tiles[startX][startY].getGameObject() instanceof  Wall) {
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
                    this.tiles[x][y].setGameObject(new Wall(x, y));
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
            if (this.tile_is_walkable(t.getX() - 1, t.getY())) {
                s.push(this.tiles[t.getX()-1][t.getY()]);
            }
            if (this.tile_is_walkable(t.getX() + 1, t.getY())) {
                s.push(this.tiles[t.getX()+1][t.getY()]);
            }
            if (this.tile_is_walkable(t.getX(), t.getY() - 1)) {
                s.push(this.tiles[t.getX()][t.getY()-1]);
            }
            if (this.tile_is_walkable(t.getX(), t.getY() + 1)) {
                s.push(this.tiles[t.getX()][t.getY()+1]);
            }
        }
        return numVisited;
    }
}
