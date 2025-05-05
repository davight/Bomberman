package entity.movement;

import java.util.Random;

/**
 * Enum smerov s definovanými rozdielmi súradníc.
 */
public enum Direction {
    UP(0, -1),
    DOWN(0, 1),
    LEFT(-1, 0),
    RIGHT(1, 0);

    private static final Random RANDOM = new Random();
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
        Direction[] arr = Direction.values();
        for (int i = 0; i < arr.length; i++) {
            int randomIndex = RANDOM.nextInt(arr.length);
            Direction temp = arr[i];
            arr[i] = arr[randomIndex];
            arr[randomIndex] = temp;
        }
        return arr;
    }

}
