package roguelike;

public class LightSource {
    private static final int[][] DIAGONALS = {{1, 1}, {-1, 1}, {1, -1}, {-1, -1}};
    private int x;
    private int y;
    private int radius;
    private final Level level;

    public LightSource(int x, int y, int radius, Level level) {
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.level = level;
    }

    public void update() {
        this.level.getTile(this.x, this.y).setToVisible();

        for (int[] d : DIAGONALS) {
            this.castLight(1, 1.0f, 0.0f, 0, d[0], d[1], 0);
            this.castLight(1, 1.0f, 0.0f, d[0], 0, 0, d[1]);
        }
    }

    public void updateLocation(int x, int y) {
        this.x = x;
        this.y = y;
        this.update();
    }


    /*
    Taken from SquidLib Library.
    */
    private void castLight(int row, float start, float end, int xx, int xy, int yx, int yy) {
        float newStart = 0.0f;
        if (start < end) {
            return;
        }
        boolean blocked = false;
        for (int distance = row; distance <= this.radius && !blocked; distance++) {
            int deltaY = -distance;
            for (int deltaX = -distance; deltaX <= 0; deltaX++) {
                int currentX = this.x + deltaX * xx + deltaY * xy;
                int currentY = this.y + deltaX * yx + deltaY * yy;
                float leftSlope = (deltaX - 0.5f) / (deltaY + 0.5f);
                float rightSlope = (deltaX + 0.5f) / (deltaY - 0.5f);

                if (!(currentX >= 0 && currentY >= 0 && currentX < this.level.getWidth() && currentY < this.level.getHeight())
                        || start < rightSlope) {
                    continue;
                } else if (end > leftSlope) {
                    break;
                }

                //check if it's within the lightable area and light if needed
                if (distanceFromSource(currentX, currentY) <= this.radius) {
                    // float bright = (float) (1 - (distanceFromPlayer(deltaX, deltaY) / radius));
                    // his.level.getTile(currentX, currentY).explore();
                    this.level.getTile(currentX, currentY).setToVisible();

                    if (blocked) { //previous cell was a blocking one
                        if (!this.level.getTile(currentX, currentY).isTransparent()) {//hit a wall
                            newStart = rightSlope;
                            // continue;
                        } else {
                            blocked = false;
                            start = newStart;
                        }
                    } else {
                        if (!this.level.getTile(currentX, currentY).isTransparent() && distance < this.radius) {//hit a wall within sight line
                            blocked = true;
                            castLight(distance + 1, start, leftSlope, xx, xy, yx, yy);
                            newStart = rightSlope;
                        }
                    }
                }
            }
        }
    }

    private double distanceFromSource(int otherX, int otherY) {
        int height = this.level.getHeight();
        int trueY = height - otherY;
        int truePlayerY = height - this.y;
        return Math.hypot(this.x - otherX, truePlayerY - trueY);
    }
}