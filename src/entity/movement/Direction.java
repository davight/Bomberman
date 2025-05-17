package entity.movement;

import util.Util;

/**
 * Enum smerov s definovanými rozdielmi súradníc.
 */
public enum Direction {
    UP(0, -1),
    DOWN(0, 1),
    LEFT(-1, 0),
    RIGHT(1, 0);

    private final int x;
    private final int y;

    Direction(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public static Direction[] toShuffledArray() {
        return Util.shuffleArray(Direction.values());
    }

}
