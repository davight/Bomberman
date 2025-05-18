package game;

import entity.enemy.AbstractEnemy;
import entity.player.AbstractPlayer;
import entity.player.BluePlayer;
import entity.player.RedPlayer;
import fri.shapesge.Manager;
import game.gui.LoseLevelScreen;
import game.gui.WinLevelScreen;
import util.Waiter;

import java.util.ArrayList;

/**
 * Trieda na manazovanie celej hry. Prepina medzi prislusnymi obrazovkami a levelmi. Manazuje objekty.
 */
public class Game {

    private static ArrayList<Object> managedObjects = new ArrayList<>();
    private static final Manager MANAGER = new Manager();
    private static Level currentLevel = null;

    /**
     * Spusti novu hru pre dvoch hracov.
     */
    public static void startDuoGame() {
        currentLevel = new Level(1);
        currentLevel.addPlayer(new BluePlayer(currentLevel.getRandomSpawnable()));
        currentLevel.addPlayer(new RedPlayer(currentLevel.getRandomSpawnable()));
    }

    /**
     * Spusti novu hru pre jedneho hraca.
     */
    public static void startSoloGame() {
        currentLevel = new Level(1);
        currentLevel.addPlayer(new BluePlayer(currentLevel.getRandomSpawnable()));
    }

    protected static void endLevel(Level level) {
        if (currentLevel != level) {
            return;
        }
        new Waiter(2000, (w) -> {
            switch (level.getState()) {
                case WIN -> {
                    WinLevelScreen screen = new WinLevelScreen(level);
                    screen.showAll();
                    new Waiter(5000, (w2) -> {
                        screen.hideAll();
                        nextLevel();
                    }).waitAndRun();
                }
                case LOSE -> {
                    LoseLevelScreen screen = new LoseLevelScreen(level);
                    screen.showAll();
                }
            }
        }).waitAndRun();
    }

    private static void nextLevel() {
        Level newLevel = new Level(currentLevel.getId() + 1);
        for (AbstractPlayer player : currentLevel.getPlayers()) {
            newLevel.addPlayer(player);
            player.teleport(null, newLevel.getRandomSpawnable());
        }
        currentLevel = newLevel;
    }

    /**
     * @return Vsetkych hracov, ktori prave hraju v nejakom leveli.
     */
    public static AbstractPlayer[] getPlayers() {
        return currentLevel.getPlayers();
    }

    /**
     * @return Vsetkych nepriatelov, ktori prave existuju v nejakom leveli.
     */
    public static AbstractEnemy[] getEnemies() {
        return currentLevel.getEnemies();
    }

    /**
     * @return ID levela, ktory sa prave hra
     */
    public static int getLevelId() {
        return currentLevel.getId();
    }

    /**
     * @param id ID levela, ktory kontrolujeme
     * @return Ci sa tento level prave teraz hra
     */
    public static boolean isPlayingLevel(int id) {
        return currentLevel.getId() == id && currentLevel.getState() == Level.State.PLAYING;
    }

    /**
     * Posle ShapesGE novy objekt na manazovanie.
     * @param object objekt na manazovanie
     */
    public static void manageObject(Object object) {
        managedObjects.add(object);
        MANAGER.manageObject(object);
    }

    /**
     * Odstrani zo ShapesGE objekt, ktory sa manazoval
     * @param object na odstranenie manazovania
     */
    public static void stopManagingObject(Object object) {
        managedObjects.remove(object);
        MANAGER.stopManagingObject(object);
    }

    private static void clearManagedObjects() {
        for (Object object : managedObjects) {
            MANAGER.stopManagingObject(object);
        }
        managedObjects = new ArrayList<>();
    }

}
