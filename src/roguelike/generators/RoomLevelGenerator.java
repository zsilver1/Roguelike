package roguelike.generators;

import roguelike.Tile;

import java.util.Random;

public class RoomLevelGenerator extends LevelGenerator {
    private final Random random = new Random();

    public RoomLevelGenerator(int width, int height) {
        super(width, height);
    }

    @Override
    public Tile[][] generate() {

        // TODO

        this.startingPlayerX = 70;
        this.startingPlayerY = 20;
        return this.tiles;
    }
}
