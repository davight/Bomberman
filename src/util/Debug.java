package util;

/**
 * Pomocna trieda na jednoduchsie debugovanie.
 */
public class Debug {

    private static final String GREEN = "\u001B[32m";
    private static final String RED = "\u001B[31m";
    private static final String RESET = "\u001B[0m";

    private static final String LOG_PREFIX = GREEN + "[LOG]";
    private static final String ERR_PREFIX = RED + "[ERR]";

    private static boolean enabled = true;

    /**
     * Zapne debug vypis.
     */
    public static void enable() {
        enabled = true;
    }

    /**
     * Vypne debug vypis.
     */
    public static void disable() {
        enabled = false;
    }

    /**
     * Vypise zoznam stringov do konzoly, ak vyjde podmienka true.
     * @param condition podmienka na skontrolovanie
     * @param s zoznam stringov na vypis
     */
    public static void log(boolean condition, String... s) {
        if (condition) {
            log(s);
        }
    }

    /**
     * Vypise zoznam stringov do konzoly.
     * @param s zoznam stringov na vypis
     */
    public static void log(String... s) {
        if (enabled) {
            System.out.println(LOG_PREFIX + " " + String.join(" ", s));
        }
        System.out.print(RESET);
    }

    /**
     * Vypise cislo do konzoly
     * @param i cislo na vypis
     */
    public static void log(Number i) {
        log(String.valueOf(i));
    }

    /**
     * Vyhodi runtime vynimkou s danou spravou
     * @param s sprava do vynimky
     */
    public static void err(String s) {
        if (enabled) {
            throw new RuntimeException(ERR_PREFIX + s + RESET);
        }
    }

    /**
     * Vyhodi runtime exception s danou spravou expcetion.
     * @param e exception na vyhodenie
     */
    public static void err(Exception e) {
        if (enabled) {
            throw new RuntimeException(ERR_PREFIX + e + RESET);
        }
    }
}
