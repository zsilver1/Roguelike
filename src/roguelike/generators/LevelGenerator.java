package roguelike.generators;

import roguelike.Tile;

public abstract class LevelGenerator {

    Tile[][] tiles;
    final int width;
    final int height;
    int startingPlayerX;
    int startingPlayerY;

    LevelGenerator(int width, int height) {
        this.width = width;
        this.height = height;
        this.tiles = new Tile[this.width][this.height];
    }

    public abstract Tile[][] generate();

    public void fillMapWith(Tile.Type type) {
        for (int x = 0; x < this.width; x++) {
            for (int y = 0; y < this.height; y++) {
                this.tiles[x][y] = new Tile(x, y, type);
            }
        }
    }

    public void filledRect(int left, int top, int width, int height, Tile.Type type) {
        for (int x = left; x - left < width; x++) {
            for (int y = top; y - top < height; y++) {
                this.tiles[x][y].setType(type);
            }
        }
    }

    public void filledSquareAt(int centerX, int centerY, int side, Tile.Type type) {
        // side should always be odd, because square must have center
        for (int x = centerX - side / 2; x <= centerX + side / 2; x++) {
            for (int y = centerY - side / 2; y <= centerY + side / 2; y++) {
                this.tiles[x][y].setType(type);
            }
        }
    }

    public void verticalLine(int startX, int startY, int len, Tile.Type type) {
        for (int y = startY; y - startY < len; y++) {
            this.tiles[startX][y].setType(type);
        }
    }

    public void horizontalLine(int startX, int startY, int len, Tile.Type type) {
        for (int x = startX; x - startX < len; x++) {
            this.tiles[x][startY].setType(type);
        }
    }

    public void hollowRect(int left, int top, int width, int height, int thickness, Tile.Type type) {
        // first create rows
        for (int x = left; x - left < width; x++) {
            for (int y = top; y - top < thickness; y++) {
                this.tiles[x][y].setType(type);
            }
        }
        for (int x = left; x - left < width; x++) {
            for (int y = top + height - thickness; y - top - height + thickness < thickness; y++) {
                this.tiles[x][y].setType(type);
            }
        }
        // now columns
        for (int y = top; y - top < height; y++) {
            for (int x = left; x - left < thickness; x++) {
                this.tiles[x][y].setType(type);
            }
        }
        for (int y = top; y - top < height; y++) {
            for (int x = left + width - thickness; x - left - width + thickness < thickness; x++) {
                this.tiles[x][y].setType(type);
            }
        }
    }

    public int numNeighboring(int x, int y, int within, Tile.Type type) {
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
                if (this.tiles[xi][yi].getType() == type) {
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
