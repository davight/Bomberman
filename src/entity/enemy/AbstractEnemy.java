package entity.enemy;

import entity.Living;
import entity.movement.Direction;
import entity.movement.Movable;
import entity.movement.MovementManager;
import entity.player.AbstractPlayer;
import fri.shapesge.Image;
import game.Game;
import grid.map.Tile;
import util.Util;

import java.util.ArrayList;
import java.util.Collections;

public abstract class AbstractEnemy implements Movable, Living {

    // TODO FIX PROBLEM WHEN TILE THINKS ENEMY IS ON IT BUT ISNT ACTUALLY CAUSE ITS DEAD
    private final Image image;
    private final MovementManager movement;

    private long lastMillis;
    private int health;
    private Tile tile;
    private boolean isAlive;

    public AbstractEnemy() {
        this.health = 4;
        this.lastMillis = System.currentTimeMillis();
        this.isAlive = true;
        Direction random = Util.randomElement(this.getValidDirections().keySet().toArray(new Direction[0]));
        this.image = new Image(this.getValidDirections().get(random).staying());
        this.image.makeVisible();
        this.movement = new MovementManager(this);
        Game.getInstance().manageObject(this);
    }

    @Override
    public final Image getImage() {
        return this.image;
    }

    @Override
    public final void afterMovementEvent(Tile tile) {
        tile.afterEntityEnterTile(this, this.tile);
        this.tile = tile;
    }

    public final void setTile(Tile tile, Direction direction) {
        this.tile = tile;
        this.movement.teleport(direction, tile);
    }

    public final void setTile(Tile tile) {
        this.setTile(tile, Direction.UP);
    }

    public final void tick() {
        if (!this.isAlive) {
            this.image.makeInvisible();
            return;
        }
        if (this.lastMillis + this.getTimeBetweenSteps() * 10L > System.currentTimeMillis() || this.movement.isActive()) {
            return;
        }
        Direction[] arr = null;
        for (AbstractPlayer p : Game.getInstance().getPlayers()) {
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
                this.lastMillis = System.currentTimeMillis();
                return;
            }
        }
    }

    private Direction[] checkForPlayer(AbstractPlayer player) {
        Tile playerTile = player.getTile();
        Direction yDir = this.tile.getBoardY() > playerTile.getBoardY() ? Direction.UP : Direction.DOWN;
        if (playerTile.getBoardX() == this.tile.getBoardX() && this.getValidDirections().containsKey(yDir)) { // They are on the same X coords, it is possible that enemy can see him
            int x = this.tile.getBoardX();
            boolean seePlayer = true;
            for (int y = Math.min(playerTile.getBoardY(), this.tile.getBoardY()); y <= Math.max(playerTile.getBoardY(), this.tile.getBoardY()); y++) {
                Tile path = Game.getInstance().getMap().getTileAtBoard(x, y);
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
        if (playerTile.getBoardY() == this.tile.getBoardY() && this.getValidDirections().containsKey(xDir)) { // They are on the same Y coords...
            int y = this.tile.getBoardY();
            boolean seePlayer = true;
            for (int x = Math.min(playerTile.getBoardX(), this.tile.getBoardX()); x <= Math.max(playerTile.getBoardX(), this.tile.getBoardX()); x++) {
                Tile path = Game.getInstance().getMap().getTileAtBoard(x, y);
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

    public final Tile getTile() {
        return this.tile;
    }

    private boolean tryMove(Direction dir) {
        if (this.movement.isActive() || this.health <= 0) {
            return false;
        }
        Tile newTile = Game.getInstance().getMap().getTileAtBoard(this.tile.getBoardX() + dir.getX(), this.tile.getBoardY() + dir.getY());
        if (newTile != null && newTile.onEntityEnterTile(this, this.tile)) {
            this.movement.startMoving(dir, this.tile, newTile);
            return true;
        }
        return false;
    }

    @Override
    public void hurt(int amount) {
        this.health -= amount;
        if (this.health <= 0) {
            this.die();
        }
    }

    @Override
    public final void die() {
        this.isAlive = false;
        this.image.makeInvisible();
        Game.getInstance().stopManagingObject(this);
        Game.getInstance().removeEnemy(this);
    }

    public void freeze(long ms) {
        this.lastMillis += ms;
    }

    @Override
    public int getHealth() {
        return this.health;
    }

    public boolean isAlive() {
        return this.isAlive;
    }

    public abstract void attack(AbstractPlayer player);

    public abstract void ultimate();

    private Direction[] shuffleExcept(Direction dir) {
        ArrayList<Direction> directions = new ArrayList<>(this.getValidDirections().keySet());
        boolean r = directions.remove(dir);
        Collections.shuffle(directions);
        if (r) {
            directions.addFirst(dir);
        }
        return directions.toArray(new Direction[0]);
    }
}
