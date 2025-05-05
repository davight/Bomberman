package entity.enemy;

import entity.movement.Direction;
import entity.movement.Movable;
import entity.movement.MovementManager;
import fri.shapesge.Image;
import grid.GameCanvas;
import grid.Tile;

import java.util.ArrayList;
import java.util.Collections;

public abstract class AbstractEnemy implements Movable {

    private final Image image;
    private final MovementManager movement;

    private GameCanvas gameCanvas;
    private long lastMillis;
    private int health;
    private Tile tile;
    private boolean isAlive;

    public AbstractEnemy(Image image) {
        this.lastMillis = System.currentTimeMillis();
        this.isAlive = true;
        this.image = image;
        this.movement = new MovementManager(this);
    }

    @Override
    public final Image getImage() {
        return this.image;
    }

    @Override
    public void afterMovementEvent(Tile tile) {
        tile.afterEntityEnterTile(this);
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
        Tile playerTile = this.gameCanvas.getPlayer().getTile();
        Direction[] arr = null;
        if (playerTile.getBoardX() == this.tile.getBoardX()) { // They are on the same X coords, it is possible that enemy can see him
            int y = this.tile.getBoardY();
            boolean seePlayer = true;
            for (int x = Math.min(playerTile.getBoardX(), this.tile.getBoardX()); x <= Math.max(playerTile.getBoardX(), this.tile.getBoardX()); x++) {
                Tile path = this.gameCanvas.getTileAtBoard(x, y);
                if (path == null || !path.getBlockT().isSeeThrough()) {
                    seePlayer = false;
                    break;
                }
            }
            if (seePlayer) {
                arr = this.shuffleExcept(this.tile.getBoardX() > playerTile.getBoardX() ? Direction.LEFT : Direction.RIGHT);
            }
        } else if (playerTile.getBoardY() == this.tile.getBoardY()) { // They are on the same Y coords...
            int x = this.tile.getBoardX();
            boolean seePlayer = true;
            for (int y = Math.min(playerTile.getBoardY(), this.tile.getBoardY()); y <= Math.max(playerTile.getBoardY(), this.tile.getBoardY()); y++) {
                Tile path = this.gameCanvas.getTileAtBoard(x, y);
                if (path == null || !path.getBlockT().isSeeThrough()) {
                    seePlayer = false;
                    break;
                }
            }
            if (seePlayer) {
                arr = this.shuffleExcept(this.tile.getBoardY() > playerTile.getBoardY() ? Direction.UP : Direction.DOWN);
            }
        }
        if (arr == null) {
            arr = Direction.toShuffledArray();
        }

        for (Direction dir : arr) { // Actual movement
            if (this.tryMove(dir)) {
                this.lastMillis = System.currentTimeMillis();
                return;
            }
        }
    }

    public final Tile getTile() {
        return this.tile;
    }

    private boolean tryMove(Direction dir) {
        if (this.movement.isActive() || this.health <= 0) {
            return false;
        }
        Tile newTile = this.gameCanvas.getTileAtBoard(this.tile.getBoardX() + dir.getX(), this.tile.getBoardY() + dir.getY());
        if (newTile != null && newTile.onEntityEnterTile(this, this.tile)) {
            this.movement.startMoving(dir, this.tile, newTile);
            return true;
        }
        return false;
    }

    public void takeDamage(int amount) {
        this.health -= amount;
        if (this.health <= 0) {
            this.die();
        }
    }

    public void die() {
        this.isAlive = false;
    }

    public abstract void attack();

    public abstract void ultimate();

    private Direction[] validDirections() {
        return null;
    }

    private Direction[] shuffleExcept(Direction dir) {
        ArrayList<Direction> directions = new ArrayList<>(this.getValidDirections().keySet());
        directions.remove(dir);
        Collections.shuffle(directions);
        directions.addFirst(dir);
        return directions.toArray(new Direction[0]);
    }

}
