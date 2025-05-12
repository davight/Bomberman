package entity.movement;

import fri.shapesge.ImageData;
import game.Game;
import grid.map.Tile;
import util.Waiter;

import java.util.Map;
import java.util.Set;

/**
 * Trieda entity.movement.Movement, ktorá sa stará o plynulý pohyb entít a hráča po plátne.
 */
public class MovementManager {

    private final Map<Direction, Pack> registeredDirectionPacks;
    private final Movable movable;

    private Movement activeMovement = null;

    public MovementManager(Movable movable) {
        this.movable = movable;
        this.registeredDirectionPacks = movable.getValidDirections();
        Game.getInstance().manageObject(this);
    }

    /**
     * Vráti hodnotu, či je obrázok v tomto momente v nejakom pohybe.
     */
    public boolean isActive() {
        return this.activeMovement != null;
    }

    /**
     * Instantne teleportuje obrázok na daný tile s daným smerom.
     * @param direction smer státia
     * @param at konečný tile
     */
    public void teleport(Direction direction, Tile at) {
        this.movable.getImage().changePosition(at.getBoardX() * Tile.TILE_SIZE, at.getBoardY() * Tile.TILE_SIZE);
        if (this.registeredDirectionPacks.containsKey(direction)) {
            this.movable.getImage().changeImage(this.registeredDirectionPacks.get(direction).staying());
        }
        this.movable.afterMovementEvent(at);
    }

    /**
     * Spustí pohyb obrázku od štartovacieho tilu daným smerom s daným minimálnym rozosputom medzi krokmi.
     *
     * @param direction sme pohybu
     * @param startTile zaciatocny tile
     * @param endTile konecny tile
     */
    public void startMoving(Direction direction, Tile startTile, Tile endTile) {
        if (!this.registeredDirectionPacks.containsKey(direction)) {
            throw new RuntimeException("You can move only within registered directions.");
        }
        if (this.activeMovement == null) {
            this.activeMovement = new Movement(direction, startTile, endTile);
        }
    }

    private void afterMovementEvent(Tile at) {
        this.movable.afterMovementEvent(at);
        this.activeMovement = null;
    }

    public Set<Direction> getValidDirections() {
        return this.registeredDirectionPacks.keySet();
    }

    private class Movement {

        private final Direction dir;
        private final Tile from;
        private final Tile to;
        private final Pack pack;
        private final int maxStep;

        private long lastMs;
        private int step;

        private Movement(Direction direction, Tile from, Tile to) {
            this.pack = MovementManager.this.registeredDirectionPacks.get(direction);
            this.maxStep = Math.max(this.pack.moving().length, 3);
            this.step = 0;
            this.dir = direction;
            this.to = to;
            this.from = from;
            this.lastMs = System.currentTimeMillis();

            Waiter moving = new Waiter(MovementManager.this.movable.getTimeBetweenSteps(), this::movingStep);
            Waiter start = moving;
            for (int i = 0; i < this.maxStep - 1; i++) {
                moving = moving.andThen(new Waiter(MovementManager.this.movable.getTimeBetweenSteps(), this::movingStep));
            }
            moving.andThen(new Waiter(MovementManager.this.movable.getTimeBetweenSteps(), this::lastStep));

            start.waitAndRun();
        }

        private void movingStep(Waiter waiter) {
            this.moveTo(this.step);
            int i = this.step >= this.pack.moving().length ? this.pack.moving().length - 1 : this.step;
            MovementManager.this.movable.getImage().changeImage(this.pack.moving(i));
            this.step++;
        }

        private void lastStep(Waiter waiter) {
            this.moveTo(this.step);
            MovementManager.this.movable.getImage().changeImage(this.pack.staying());
            MovementManager.this.afterMovementEvent(this.to);
        }

        private void moveTo(int n) {
            MovementManager.this.movable.getImage().changePosition(
                    (this.from.getBoardX() * Tile.TILE_SIZE) + (n * ((Tile.TILE_SIZE / this.maxStep) * this.dir.getX())),
                    (this.from.getBoardY() * Tile.TILE_SIZE) + (n * ((Tile.TILE_SIZE / this.maxStep) * this.dir.getY()))
            );
        }
    }

    public record Pack(ImageData staying, ImageData... moving) {
        private ImageData moving(int n) {
            return this.moving[n];
        }
    }

}
