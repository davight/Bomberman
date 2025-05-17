package game;

import entity.enemy.AbstractEnemy;
import entity.enemy.EnemyRegister;
import entity.player.AbstractPlayer;
import events.EnemyDeathEvent;
import events.EventManager;
import events.PlayerDeathEvent;
import grid.map.Map;
import grid.map.RandomMap;
import grid.map.Tile;
import util.Debug;

import java.util.ArrayList;
import java.util.HashSet;

public class Level {

    private final HashSet<AbstractEnemy> enemies = new HashSet<>();
    private final HashSet<AbstractPlayer> players = new HashSet<>();
    private final long start;
    private final int id;
    private final Map map;

    private long levelTime;
    private State state = State.PLAYING;

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

        EventManager.registerHandler(PlayerDeathEvent.class, (e) -> this.removePlayer(e.player()));
        EventManager.registerHandler(EnemyDeathEvent.class, (e) -> this.removeEnemy(e.enemy()));
    }

    public void addPlayer(AbstractPlayer player) {
        this.players.add(player);
    }

    public void removePlayer(AbstractPlayer player) {
        this.players.remove(player);
        Debug.log("Removed player " + player, "Left " + this.players.size());
        if (this.state == State.PLAYING && this.players.isEmpty()) {
            this.endLevel(State.LOSE);
        }
    }

    public AbstractPlayer[] getPlayers() {
        return this.players.toArray(new AbstractPlayer[0]);
    }

    public void addEnemy(AbstractEnemy enemy) {
        this.enemies.add(enemy);
    }

    public void removeEnemy(AbstractEnemy enemy) {
        Debug.log("Removed enemy " + enemy, "Left " + this.enemies.size());
        this.enemies.remove(enemy);
        if (this.state == State.PLAYING && this.enemies.isEmpty()) {
            this.endLevel(State.WIN);
        }
    }

    public AbstractEnemy[] getEnemies() {
        return this.enemies.toArray(new AbstractEnemy[0]);
    }

    public State getState() {
        return this.state;
    }

    public Tile getRandomSpawnable() {
        return this.map.getRandomSpawnable();
    }

    public int getId() {
        return this.id;
    }

    private void endLevel(State state) {
        for (AbstractEnemy e : this.enemies) {
            e.despawn();
        }
        this.state = state;
        this.levelTime = System.currentTimeMillis() - this.start;
        Game.endLevel(this);
    }

    public long getLevelTime() {
        if (this.state == State.PLAYING) {
            throw new RuntimeException("Still playing");
        }
        return this.levelTime;
    }

    public enum State { PLAYING, WIN, LOSE }
}
