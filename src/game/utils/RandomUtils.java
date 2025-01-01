package game.utils;

import java.util.Random;

public class RandomUtils {

    private static final Random rand = new Random();

    public static boolean randomBoolean(int chance) {
        return rand.nextInt(101) <= chance;
    }

}
