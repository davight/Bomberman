package entity.movement;

import fri.shapesge.ImageData;
import grid.Tile;
import main.GameManager;

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
        GameManager.getInstance().manageObjects(this);
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

    /**
     * ShapesGE listener ticku. Spravuje kroky pri pohybe obrázku.
     */
    public void tick() {
        if (this.activeMovement != null) {
            this.activeMovement.tick();
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
            this.maxStep = this.pack.moving().length;
            this.step = 0;
            this.dir = direction;
            this.to = to;
            this.from = from;
            this.lastMs = System.currentTimeMillis();
        }

        private void tick() {
            if (this.lastMs + MovementManager.this.movable.getTimeBetweenSteps() > System.currentTimeMillis()) {
                return;
            }
            this.lastMs = System.currentTimeMillis();
            this.moveTo(this.step);
            if (this.step == this.maxStep) {
                MovementManager.this.movable.getImage().changeImage(this.pack.staying());
                MovementManager.this.movable.afterMovementEvent(this.to);
            } else {
                MovementManager.this.movable.getImage().changeImage(this.pack.moving(this.step));
                this.step++;
            }
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
