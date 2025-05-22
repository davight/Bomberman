package entity.movement;

import util.Util;

/**
 * Enum smerov s definovanymi rozdielmi suradnic.
 */
public enum Direction {
    /**
     * Smer hore.
     */
    UP(0, -1),

    /**
     * Smer dole.
     */
    DOWN(0, 1),

    /**
     * Smer vlavo.
     */
    LEFT(-1, 0),

    /**
     * Smer vpravo.
     */
    RIGHT(1, 0);

    private final int x;
    private final int y;

    Direction(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * @return Rozdiel na x-ovej suradnici pri tomto smere
     */
    public int getX() {
        return this.x;
    }

    /**
     * @return Rozdiel na y-ovej suradnici pri tomto smere
     */
    public int getY() {
        return this.y;
    }

    /**
     * @return Nahodne porozhadzovane hodnoty tohto enumu
     */
    public static Direction[] toShuffledArray() {
        return Util.shuffleArray(Direction.values());
    }

}
