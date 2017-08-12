package roguelike;

import java.util.Random;

public class RoomLevelGenerator extends LevelGenerator {
    private final Random random = new Random();

    RoomLevelGenerator(int width, int height) {
        super(width, height);
    }

    @Override
    public Tile[][] generate() {
        this.fillWithFloors();
        this.hollowRect(0, 0, this.width, this.height, 1);

        int centerRoomSideLen = 5 + random.nextInt(this.height - 20);
        this.filledSquareAt(this.width / 2, this.height / 2, centerRoomSideLen);
        this.verticalLine(this.width/2, 5, this.height - 10);
        this.horizontalLine(8, this.height/2, this.width - 16);

        this.startingPlayerX = 70;
        this.startingPlayerY = 20;
        return this.tiles;
    }
}
