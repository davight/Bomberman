package util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Util trieda pre rozne pomocky.
 */
public class Util {

    private static final Random RAND = new Random();

    /**
     * Vrati 'true' so zadanou pravdepobonostou.
     * @param chance pravdepobonost na 'true'
     * @return vysledok
     */
    public static boolean randomBoolean(int chance) {
        return RAND.nextInt(100) < chance;
    }

    /**
     * Vrati 'true' so zadanou pravdepodobnostou a zaroven vykona akciu
     * @param chance pravdepobonost na 'true'
     * @param action akcia na spustenie
     * @return vysledok
     */
    public static boolean randomBooleanThen(int chance, Runnable action) {
        if (randomBoolean(chance)) {
            action.run();
            return true;
        }
        return false;
    }

    /**
     * Vrati nahodny integer v rozsahu
     * @param from min (vratane)
     * @param to max (vratane)
     * @return nahodne cislo z rozsahu
     */
    public static int randomInt(int from, int to) {
        return RAND.nextInt(from, to + 1);
    }

    /**
     * @return Nahodne double cislo.
     */
    public static double randomDouble() {
        return RAND.nextDouble();
    }

    /**
     * Zamiesa array.
     * @param arr array na zamiesanie
     * @return Zamiesanu array
     */
    public static <T> T[] shuffleArray(T[] arr) {
        ArrayList<T> list = new ArrayList<>(List.of(arr));
        Collections.shuffle(list);
        return list.toArray(arr);
    }

    /**
     * Vyberie nahodny prvok z array.
     * @param arr array na vyber
     * @return Nahodny prvok
     */
    public static <T> T randomElement(T[] arr) {
        return arr[RAND.nextInt(arr.length)];
    }

    /**
     * Vyberie nahodny prvok z akejkolvek kolekcie
     * @param collection kolekcia na vyber
     * @return Nahodny prvok
     */
    @SuppressWarnings("unchecked")
    public static <T> T randomElement(Collection<? extends T> collection) {
        return (T)randomElement(collection.toArray(new Object[0]));
    }

}
