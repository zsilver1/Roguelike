package roguelike;

import asciiPanel.AsciiPanel;

public class Player extends Actor {

    /*
    (x, y)
     */
    private static final int[][] DIAGONALS = {{1, 1}, {-1, 1}, {1, -1}, {-1, -1}};
    private static final int FOV_RADIUS = 30;
    private LightSource lightSource;

    public Player(Level level, int x, int y) {
        super(level, x, y, '@', AsciiPanel.brightCyan, AsciiPanel.black);
        // this.lightSource = new LightSource(x, y, 10, level);
        level.recomputeLight();
        this.updateFOV();
    }

    @Override
    public void moveTo(int newX, int newY) {
        super.moveTo(newX, newY);
        this.updateFOV();
    }

    public void setLightSource(LightSource l) {
        this.lightSource = l;
    }

    /*
    Taken from SquidLib Library.
     */
    public void updateFOV() {
        // this.level.setTilesToNotExplored();
        this.level.getTile(this.x, this.y).setToVisible();
        this.level.getTile(this.x, this.y).explore();
        for (Tile t : this.level.getTiles(this.x, this.y, 3, false)) {
            t.setToVisible();
            t.explore();
        }
        // this.updateLightSource();
        for (int[] d : DIAGONALS) {
            this.castVision(1, 1.0f, 0.0f, 0, d[0], d[1], 0);
            this.castVision(1, 1.0f, 0.0f, d[0], 0, 0, d[1]);
        }
    }

    private void updateLightSource() {
        if (this.lightSource != null) {
            this.lightSource.update();
        }
    }

    private void castVision(int row, float start, float end, int xx, int xy, int yx, int yy) {
        float newStart = 0.0f;
        if (start < end) {
            return;
        }
        boolean blocked = false;
        for (int distance = row; distance <= FOV_RADIUS && !blocked; distance++) {
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
                if (distanceFromPlayer(currentX, currentY) <= FOV_RADIUS) {
                    // float bright = (float) (1 - (distanceFromPlayer(deltaX, deltaY) / radius));
                    // this.level.getTile(currentX, currentY).setToVisible();
                    this.level.getTile(currentX, currentY).explore();

                    if (blocked) { //previous cell was a blocking one
                        if (!this.level.getTile(currentX, currentY).isTransparent()) {//hit a wall
                            newStart = rightSlope;
                        } else {
                            blocked = false;
                            start = newStart;
                        }
                    } else {
                        if (!this.level.getTile(currentX, currentY).isTransparent() && distance < FOV_RADIUS) {//hit a wall within sight line
                            blocked = true;
                            castVision(distance + 1, start, leftSlope, xx, xy, yx, yy);
                            newStart = rightSlope;
                        }
                    }
                }
            }
        }
    }

    private double distanceFromPlayer(int otherX, int otherY) {
        int height = this.level.getHeight();
        int trueY = height - otherY;
        int truePlayerY = height - this.y;
        return Math.hypot(this.x - otherX, truePlayerY - trueY);
    }
}
