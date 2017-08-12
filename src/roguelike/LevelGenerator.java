package roguelike;

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

    public void fillWithFloors() {
        for (int x = 0; x < this.width; x++) {
            for (int y = 0; y < this.height; y++) {
                this.tiles[x][y] = new Tile(x, y, Tile.Type.FLOOR);
            }
        }
    }

    public void filledRect(int left, int top, int width, int height) {
        for (int x = left; x - left < width; x++) {
            for (int y = top; y - top < height; y++) {
                this.tiles[x][y].setType(Tile.Type.WALL);
            }
        }
    }

    public void filledSquareAt(int centerX, int centerY, int side) {
        // side should always be odd, because square must have center
        for (int x = centerX - side / 2; x <= centerX + side / 2; x++) {
            for (int y = centerY - side / 2; y <= centerY + side / 2; y++) {
                this.tiles[x][y].setType(Tile.Type.WALL);
            }
        }
    }

    public void verticalLine(int startX, int startY, int len) {
        for (int y = startY; y - startY < len; y++) {
            this.tiles[startX][y].setType(Tile.Type.WALL);
        }
    }

    public void horizontalLine(int startX, int startY, int len) {
        for (int x = startX; x - startX < len; x++) {
            this.tiles[x][startY].setType(Tile.Type.WALL);
        }
    }

    public void hollowRect(int left, int top, int width, int height, int thickness) {
        // first create rows
        for (int x = left; x - left < width; x++) {
            for (int y = top; y - top < thickness; y++) {
                this.tiles[x][y].setType(Tile.Type.WALL);
            }
        }
        for (int x = left; x - left < width; x++) {
            for (int y = top + height - thickness; y - top - height + thickness < thickness; y++) {
                this.tiles[x][y].setType(Tile.Type.WALL);
            }
        }
        // now columns
        for (int y = top; y - top < height; y++) {
            for (int x = left; x - left < thickness; x++) {
                this.tiles[x][y].setType(Tile.Type.WALL);
            }
        }
        for (int y = top; y - top < height; y++) {
            for (int x = left + width - thickness; x - left - width + thickness < thickness; x++) {
                this.tiles[x][y].setType(Tile.Type.WALL);
            }
        }
    }

    public int getStartingPlayerX() {
        return startingPlayerX;
    }

    public int getStartingPlayerY() {
        return startingPlayerY;
    }
}
