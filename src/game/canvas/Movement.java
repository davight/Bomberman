package game.canvas;

import fri.shapesge.Image;
import fri.shapesge.ImageData;
import fri.shapesge.Manager;

import java.util.HashMap;

public class Movement {

    private static final Manager MOVEMENT_MANAGER = new Manager();
    private static final int STEPS = 3;

    private final HashMap<Direction, ImageData[]> registeredDirections = new HashMap<>();
    private final Image image;

    private boolean isActive = false;
    private long lastAction;
    private Tile startTile;
    private int timeBetweenActions;
    private int action;
    private Direction direction;

    public Movement(Image image) {
        this.image = image;
        for (Direction dir : Direction.values()) {
            this.registeredDirections.put(dir, new ImageData[2]);
        }
    }

    public void registerDirection(Direction direction, ImageData staying, ImageData moving) {
        ImageData[] i = this.registeredDirections.get(direction);
        i[0] = staying;
        i[1] = moving;
    }

    public void registerDirection(Direction direction, String stayingPath, String movingPath) {
        this.registerDirection(direction, new ImageData(stayingPath), new ImageData(movingPath));
    }

    public boolean isActive() {
        return this.isActive;
    }

    public void teleport(Direction direction, Tile at) {
        this.direction = direction;
        this.image.changePosition(at.getBoardX() * Tile.TILE_SIZE, at.getBoardY() * Tile.TILE_SIZE);
        this.changeIfNotNull(0);
    }

    public void startMoving(Direction direction, Tile startTile, int timeBetweenActions) {
        if (this.isActive) {
            return;
        }
        MOVEMENT_MANAGER.manageObject(this);
        this.direction = direction;
        this.isActive = true;
        this.startTile = startTile;
        this.timeBetweenActions = timeBetweenActions;
        this.action = 1;
    }

    public void tick() {
        if (!this.isActive || this.lastAction + this.timeBetweenActions > System.currentTimeMillis()) {
            return;
        }
        this.lastAction = System.currentTimeMillis();
        this.moveTo(this.action);
        if (this.action == STEPS) {
            this.changeIfNotNull(0);
            this.isActive = false;
            MOVEMENT_MANAGER.stopManagingObject(this);
        } else {
            this.changeIfNotNull(1);
            this.action++;
        }
    }

    private void moveTo(int n) {
        this.image.changePosition(
                (this.startTile.getBoardX() * Tile.TILE_SIZE) + (n * ((Tile.TILE_SIZE / STEPS) * this.direction.getX())),
                (this.startTile.getBoardY() * Tile.TILE_SIZE) + (n * ((Tile.TILE_SIZE / STEPS) * this.direction.getY()))
        );
    }

    private void changeIfNotNull(int type) {
        if (this.registeredDirections.get(this.direction)[type] != null) {
            this.image.changeImage(this.registeredDirections.get(this.direction)[type]);
        }
    }

    public enum Direction {
        UP(0, -1),
        DOWN(0, 1),
        LEFT(-1, 0),
        RIGHT(1, 0);

        private final int x;
        private final int y;

        Direction(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int getX() {
            return this.x;
        }

        public int getY() {
            return this.y;
        }

    }

}
