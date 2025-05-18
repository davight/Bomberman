package game;

import entity.enemy.AbstractEnemy;
import entity.enemy.EnemyRegister;
import entity.player.AbstractPlayer;
import events.EnemyDeathEvent;
import events.EventManager;
import events.ItemSpawnEvent;
import events.PlayerDeathEvent;
import grid.map.Map;
import grid.map.RandomMap;
import grid.map.Tile;
import items.AbstractItem;
import util.Debug;

import java.util.HashSet;

/**
 * Trieda Level, ktora manazuje hrajuci level. Stara sa o sledovanie zivych hracov a nepriatelov.
 */
public class Level {

    private final HashSet<AbstractEnemy> enemies = new HashSet<>();
    private final HashSet<AbstractPlayer> players = new HashSet<>();
    private final HashSet<AbstractItem> items = new HashSet<>();
    private final long start;
    private final int id;
    private final Map map;

    private long levelTime;
    private State state = State.PLAYING;

    /**
     * Inicializuje novy level s danym ID.
     * @param id ID levelu
     */
    public Level(int id) {
        this.id = id;
        this.start = System.currentTimeMillis();

        RandomMap randomMap = new RandomMap();
        this.map = new Map(randomMap.getMapChunks());

        for (int i = 0; i < randomMap.getMapChunks().length; i++) {
            AbstractEnemy randomEnemy = EnemyRegister.getRandom().getNew();
            randomEnemy.setTile(this.map.getRandomSpawnable());
            this.enemies.add(randomEnemy);
        }

        EventManager.registerHandler(ItemSpawnEvent.class, (e) -> this.addItem(e.item()));

        EventManager.registerHandler(PlayerDeathEvent.class, (e) -> this.removePlayer(e.player()));
        EventManager.registerHandler(EnemyDeathEvent.class, (e) -> this.removeEnemy(e.enemy()));
    }

    /**
     * Prida do tohto levelu noveho hraca
     * @param player hrac na pridanie
     */
    public void addPlayer(AbstractPlayer player) {
        this.players.add(player);
    }

    /**
     * Odstrani hraca z tohto levelu
     * @param player hrac na odstranenie
     */
    public void removePlayer(AbstractPlayer player) {
        this.players.remove(player);
        if (this.state == State.PLAYING && this.players.isEmpty()) {
            this.endLevel(State.LOSE);
        }
    }

    /**
     * @return Hracov, ktori aktualne hraju tento level
     */
    public AbstractPlayer[] getPlayers() {
        return this.players.toArray(new AbstractPlayer[0]);
    }

    /**
     * Prida noveho nepriatela do levelu.
     * @param enemy nepriatel, ktory sa ma pridat
     */
    public void addEnemy(AbstractEnemy enemy) {
        this.enemies.add(enemy);
    }

    /**
     * Odstrani nepriatela z tohto levelu.
     * @param enemy nepriatel, ktory sa ma odstranit
     */
    public void removeEnemy(AbstractEnemy enemy) {
        this.enemies.remove(enemy);
        if (this.state == State.PLAYING && this.enemies.isEmpty()) {
            this.endLevel(State.WIN);
        }
    }

    /**
     * @return Nepriatelov, ktori aktualne ziju na tomto leveli.
     */
    public AbstractEnemy[] getEnemies() {
        return this.enemies.toArray(new AbstractEnemy[0]);
    }

    /**
     * Prida novy item do levelu.
     * @param item item, ktory sa ma pridat
     */
    public void addItem(AbstractItem item) {
        this.items.add(item);
    }

    /**
     * Odstrani item z levelu.
     * @param item item, ktory sa ma odstranit
     */
    public void removeItem(AbstractItem item) {
        item.remove();
        this.items.remove(item);
    }

    /**
     * @return Stav v ktorom sa aktualne level nachadza
     * @see State
     */
    public State getState() {
        return this.state;
    }

    /**
     * @return Nahodny tile, ktory je bezpecny na spawnutie nepriatela alebo hraca
     */
    public Tile getRandomSpawnable() {
        return this.map.getRandomSpawnable();
    }

    /**
     * @return ID tohto levela.
     */
    public int getId() {
        return this.id;
    }

    private void endLevel(State state) {
        for (AbstractEnemy e : this.enemies) {
            e.despawn();
        }
        for (AbstractItem i : new HashSet<>(this.items)) {
            this.removeItem(i);
        }
        this.state = state;
        this.levelTime = System.currentTimeMillis() - this.start;
        Game.endLevel(this);
    }

    /**
     * @return Cas v milisekundach, ktory sa tento level hral.
     */
    public long getLevelTime() {
        if (this.state == State.PLAYING) {
            throw new RuntimeException("Still playing");
        }
        return this.levelTime;
    }

    /**
     * Enum stavov, ktore level moze nadobudnut.
     */
    public enum State { PLAYING, WIN, LOSE }
}
