package game;

import entity.enemy.AbstractEnemy;
import entity.enemy.EnemyRegister;
import entity.player.AbstractPlayer;
import entity.player.BluePlayer;
import entity.player.RedPlayer;
import fri.shapesge.Manager;
import grid.map.Map;
import grid.map.RandomMap;

import java.util.ArrayList;
import java.util.Arrays;

public class Game {

    private static final Manager MANAGER = new Manager();
    private final ArrayList<Object> managedObjects = new ArrayList<>();

    private RedPlayer redPlayer = null;
    private BluePlayer bluePlayer = null;
    private Map map = null;
    private ArrayList<AbstractEnemy> enemies = new ArrayList<>();
    private ArrayList<AbstractPlayer> players = new ArrayList<>();
    private boolean isSolo;
    private long startMillis;

    private static Game instance;

    public static Game getInstance() {
        if (instance == null) {
            throw new IllegalStateException("Game has not been started yet");
        }
        return instance;
    }

    public static Game startDuoGame() {
        instance = new Game();
        instance.isSolo = false;
        instance.startMillis = System.currentTimeMillis();

        RandomMap generatedMap = new RandomMap();
        instance.map = new Map(generatedMap.getMapChunks());

        instance.players.add(new BluePlayer(instance.map.getRandomSpawnable()));
        instance.players.add(new RedPlayer(instance.map.getRandomSpawnable()));

        for (int i = 0; i < generatedMap.getMapChunks().length; i++) {
            AbstractEnemy random = EnemyRegister.getRandom().getNew();
            random.setTile(instance.map.getRandomSpawnable());
            instance.enemies.add(random);
        }

        return instance;
    }

    public static Game startSoloGame() {
        instance = new Game();
        instance.isSolo = true;
        instance.startMillis = System.currentTimeMillis();

        RandomMap generatedMap = new RandomMap();
        instance.map = new Map(generatedMap.getMapChunks());

        instance.players.add(new BluePlayer(instance.map.getRandomSpawnable()));

        for (int i = 0; i < generatedMap.getMapChunks().length; i++) {
            AbstractEnemy random = EnemyRegister.getRandom().getNew();
            random.setTile(instance.map.getRandomSpawnable());
            instance.enemies.add(random);
        }

        return instance;
    }

    public void nextLevel() {
        RandomMap generatedMap = new RandomMap();
        instance.map = new Map(generatedMap.getMapChunks());

        for (AbstractPlayer player : instance.players) {
            player.teleport(null, instance.map.getRandomSpawnable());
        }

        for (int i = 0; i < generatedMap.getMapChunks().length; i++) {
            AbstractEnemy random = EnemyRegister.getRandom().getNew();
            random.setTile(instance.map.getRandomSpawnable());
            this.enemies.add(random);
        }
    }

    public void removePlayer(AbstractPlayer player) {
        this.players.remove(player);

        if (this.players.isEmpty()) {
            System.out.println("LOST");
        }
    }

    public Map getMap() {
        return this.map;
    }

    public void removeEnemy(AbstractEnemy enemy) {
        this.enemies.remove(enemy);
        if (this.enemies.isEmpty()) {
            System.out.println("WIN");
            this.nextLevel();
        }
    }

    public AbstractPlayer getRedPlayer() {
        return this.redPlayer;
    }

    public AbstractPlayer getBluePlayer() {
        return this.bluePlayer;
    }

    public AbstractEnemy[] getEnemies() {
        return this.enemies.toArray(new AbstractEnemy[0]);
    }

    public AbstractPlayer[] getPlayers() {
        return this.players.toArray(new AbstractPlayer[0]);
    }

    public boolean isSoloGame() {
        return this.isSolo;
    }

    public boolean isDuoGame() {
        return !this.isSolo;
    }

    public void manageObject(Object... objects) {
        this.managedObjects.addAll(Arrays.asList(objects));
        for (Object o : objects) {
            MANAGER.manageObject(o);
        }
    }

    public void stopManagingObject(Object... objects) {
        this.managedObjects.removeAll(Arrays.asList(objects));
        for (Object o : objects) {
            MANAGER.stopManagingObject(o);
        }
    }

    private void clearManagedObjects() {
        this.stopManagingObject(this.managedObjects);
    }

    private Game() {
    }

}
