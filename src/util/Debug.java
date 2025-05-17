package util;

import java.util.Arrays;

public class Debug {

    private static final String GREEN = "\u001B[32m";
    private static final String RED = "\u001B[31m";
    private static final String RESET = "\u001B[0m";

    private static final String LOG_PREFIX = GREEN + "[LOG]";
    private static final String ERR_PREFIX = RED + "[ERR]";

    private static boolean enabled = true;

    public static void enable() {
        enabled = true;
    }

    public static void disable() {
        enabled = false;
    }

    public static void log(boolean condition, String... s) {
        if (condition) {
            log(s);
        }
    }

    public static void log(String... s) {
        if (enabled) {
            System.out.println(LOG_PREFIX + " " + String.join(" ", s));
        }
        System.out.print(RESET);
    }

    public static void log(Number i) {
        log(String.valueOf(i));
    }

    public static void err(String s) {
        if (enabled) {
            throw new RuntimeException(ERR_PREFIX + s + RESET);
        }
    }

    public static void err(Exception e) {
        if (enabled) {
            throw new RuntimeException(ERR_PREFIX + e + RESET);
        }
    }

}
