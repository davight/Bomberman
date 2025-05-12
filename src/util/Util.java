package util;

import java.util.*;

public class Util {

    private static final Random RAND = new Random();

    public static boolean randomBoolean(int chance) {
        return RAND.nextInt(100) < chance;
    }

    public static void randomBooleanThen(int chance, Runnable action) {
        if (randomBoolean(chance)) {
            action.run();
        }
    }

    public static int randomInt(int from, int to) {
        return RAND.nextInt(from, to + 1);
    }

    public static double randomDouble(double from, double to) {
        return RAND.nextDouble() * (to - from) + from;
    }

    public static double randomDouble() {
        return RAND.nextDouble();
    }

    public static <T> T[] shuffleArray(T[] arr) {
        ArrayList<T> list = new ArrayList<>(List.of(arr));
        Collections.shuffle(list);
        return list.toArray(arr);
    }

    public static <T> T randomElement(T[] arr) {
        return arr[RAND.nextInt(arr.length)];
    }

    @SuppressWarnings("unchecked")
    public static <T> T randomElement(Collection<? extends T> collection) {
        return (T)randomElement(collection.toArray(new Object[0]));
    }

}
