package entity.enemy;

import entity.movement.Direction;
import entity.movement.Movable;
import entity.movement.MovementManager;
import entity.player.AbstractPlayer;
import events.EnemyDeathEvent;
import events.EnemyEnterTileEvent;
import events.EventManager;
import fri.shapesge.Image;
import game.Game;
import grid.map.Map;
import grid.map.Tile;
import util.Util;
import util.Waiter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumMap;

/**
 * Abstraktna trieda ako template pre vytvaranie nepriatelov.
 */
public abstract class AbstractEnemy implements Movable {

    private final Image image;
    private final MovementManager movement;
    private final EnumMap<Direction, MovementManager.Pack> directions;

    private Waiter mover = null;
    private Tile tile;
    private boolean isAlive;

    /**
     * Vytvori novu instanciu nepriatela s prednastavenymi hodnotami.
     */
    public AbstractEnemy() {
        this.tile = null;
        this.isAlive = true;
        this.directions = this.getValidDirections();
        Direction random = Util.randomElement(this.directions.keySet());
        this.image = new Image(this.directions.get(random).staying());
        this.image.makeVisible();
        this.movement = new MovementManager(this);

        this.startMover();
    }

    /**
     * Zmaze nepriatela z mapy.
     */
    public void despawn() {
        this.movement.stopMoving();
        this.tile.removeEnemy(this);
        this.isAlive = false;
        this.image.makeInvisible();
    }

    /**
     * @return obrazok tohto nepriatela.
     */
    @Override
    public final Image getImage() {
        return this.image;
    }

    /**
     * Vykona sa po uspesnom presunuti nepriatela na novy tile.
     * @param newTile Tile, na ktory sa nepriatel posunul.
     */
    @Override
    public final void afterSuccessfulMovement(Tile newTile) {
        if (!this.isAlive) { // enemy can die while moving so lets not fire event in that case
            return;
        }
        EventManager.fireEvent(new EnemyEnterTileEvent(this, newTile, this.tile));
        this.tile = newTile;
    }

    public final void setTile(Tile tile, Direction direction) {
        this.tile = tile;
        this.movement.teleport(direction, tile);
    }

    public final void setTile(Tile tile) {
        this.setTile(tile, Util.randomElement(this.directions.keySet()));
    }

    private void movement(Waiter waiter) {
        Direction[] arr = null;
        for (AbstractPlayer p : Game.getPlayers()) {
            arr = this.checkForPlayer(p);
            if (arr != null) {
                break;
            }
        }
        if (arr == null) {
            arr = this.shuffleExcept(null);
        }

        for (Direction dir : arr) { // Actual movement
            if (this.tryMove(dir)) {
                return;
            }
        }
    }

    private Direction[] checkForPlayer(AbstractPlayer player) {
        Tile playerTile = player.getTile();
        Direction yDir = this.tile.getBoardY() > playerTile.getBoardY() ? Direction.UP : Direction.DOWN;
        if (playerTile.getBoardX() == this.tile.getBoardX() && this.directions.containsKey(yDir)) { // They are on the same X coords, it is possible that enemy can see him
            int x = this.tile.getBoardX();
            boolean seePlayer = true;
            for (int y = Math.min(playerTile.getBoardY(), this.tile.getBoardY()); y <= Math.max(playerTile.getBoardY(), this.tile.getBoardY()); y++) {
                Tile path = Map.getTileAtBoard(x, y);
                if (path == null || !path.getBlock().isSeeThrough()) {
                    seePlayer = false;
                    break;
                }
            }
            if (seePlayer) {
                return this.shuffleExcept(yDir);
            }
        }
        Direction xDir = this.tile.getBoardX() > playerTile.getBoardX() ? Direction.LEFT : Direction.RIGHT;
        if (playerTile.getBoardY() == this.tile.getBoardY() && this.directions.containsKey(xDir)) { // They are on the same Y coords...
            int y = this.tile.getBoardY();
            boolean seePlayer = true;
            for (int x = Math.min(playerTile.getBoardX(), this.tile.getBoardX()); x <= Math.max(playerTile.getBoardX(), this.tile.getBoardX()); x++) {
                Tile path = Map.getTileAtBoard(x, y);
                if (path == null || !path.getBlock().isSeeThrough()) {
                    seePlayer = false;
                    break;
                }
            }
            if (seePlayer) {
                return this.shuffleExcept(xDir);
            }
        }
        return null;
    }

    private void startMover() {
        if (this.mover != null) {
            this.mover.cancelWait();
        }
        this.mover = new Waiter(this.millisBetweenMovement(), this::movement);
        this.mover.waitAndRepeat();
    }

    private void cancelMover() {
        if (this.mover != null) {
            this.mover.cancelWait();
            this.mover = null;
        }
    }

    /**
     * @return tile, na ktorom sa nepriatel prave nachadza.
     */
    public final Tile getTile() {
        return this.tile;
    }

    private boolean tryMove(Direction dir) {
        if (this.movement.isActive()) {
            return false;
        }
        Tile newTile = Map.getTileAtBoard(this.tile.getBoardX() + dir.getX(), this.tile.getBoardY() + dir.getY());
        if (newTile != null && newTile.canEnemyEnterTile(this, this.tile)) {
            this.movement.startMoving(dir, this.tile, newTile);
            return true;
        }
        return false;
    }

    /**
     * Zabije nepriatela a zmaze ho z mapy.
     */
    public void kill() {
        if (this.movement.isActive()) {
            this.movement.stopMoving();
        }
        this.cancelMover();
        this.isAlive = false;
        this.image.makeInvisible();
        EventManager.fireEvent(new EnemyDeathEvent(this));
    }

    /**
     * Posunie cas, o ktory sa nepriatel ma pohybovat dalej.
     * @param ms cas v milisekundach.
     */
    public void freeze(long ms) {
        this.cancelMover();
        Waiter freeze = new Waiter(ms, (w) -> {
            if (this.isAlive) {
                this.startMover();
            }
        });
        freeze.waitAndRun();
    }

    /**
     * @return cas v milisekundach, ktory ma nepriatel cakat pred pokusom o dalsi pohyb.
     */
    public abstract int millisBetweenMovement();

    /**
     * Vlastna implementacia zautocenia na hraca.
     */
    public abstract void attack(AbstractPlayer player);

    private Direction[] shuffleExcept(Direction dir) {
        ArrayList<Direction> list = new ArrayList<>(this.directions.keySet());
        boolean r = list.remove(dir);
        Collections.shuffle(list);
        if (r) {
            list.addFirst(dir);
        }
        return list.toArray(new Direction[0]);
    }
}
