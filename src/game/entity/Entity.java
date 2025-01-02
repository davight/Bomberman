package game.entity;

import fri.shapesge.Image;
import fri.shapesge.Manager;
import game.canvas.EntityType;
import game.canvas.GameCanvas;
import game.canvas.Movement;
import game.canvas.Tile;

import java.util.Random;

public class Entity {

    private static final Manager ENTITY_MANAGER = new Manager();
    private static final Random RANDOM = new Random();

    private final EntityType type;
    private final GameCanvas gameCanvas;
    private final Image image;
    private final Movement movement;

    private long lastMillis;
    private Tile tile;

    public Entity(EntityType type, Tile tile) {
        ENTITY_MANAGER.manageObject(this);
        this.image = new Image(type.getStayingImage());
        this.image.makeVisible();
        this.movement = new Movement(this.image);
        this.movement.registerDirection(Movement.Direction.UP, type.getStayingImage(), type.getUpMovingImage());
        this.movement.registerDirection(Movement.Direction.DOWN, type.getStayingImage(), type.getDownMovingImage());
        this.movement.registerDirection(Movement.Direction.LEFT, type.getStayingImage(), type.getLeftMovingImage());
        this.movement.registerDirection(Movement.Direction.RIGHT, type.getStayingImage(), type.getRightMovingImage());
        this.movement.teleport(Movement.Direction.UP, tile);
        this.type = type;
        this.tile = tile;
        this.lastMillis = System.currentTimeMillis() + RANDOM.nextInt(type.getRandomMovement());
        this.gameCanvas = tile.getGameCanvas();
    }

    public Tile getTile() {
        return this.tile;
    }

    public void tick() {
        if (this.lastMillis + this.type.getRandomMovement() > System.currentTimeMillis() || this.movement.isActive()) {
            return;
        }
        if (this.generateMovement()) {
            this.lastMillis = System.currentTimeMillis();
        }
    }

    private boolean generateMovement() {
        Tile temp;
        for (Movement.Direction direction : Entity.shuffleArray(Movement.Direction.values())) {
            temp = this.gameCanvas.getTileAtBoard(this.tile.getBoardX() + direction.getX(), this.tile.getBoardY() + direction.getY());
            if (temp != null && temp.entityEnterTile()) {
                this.movement.startMoving(direction, this.tile, 50);
                this.tile = temp;
                return true;
            }
        }
        return false;
    }

    public void die() {
        this.image.makeInvisible();
        ENTITY_MANAGER.stopManagingObject(this);
        if (this.type == EntityType.EXPLOSIVE) {
            this.gameCanvas.spawnBomb(this.tile);
        }
    }

    private static Movement.Direction[] shuffleArray(Movement.Direction[] arr) {
        for (int i = 0; i < arr.length; i++) {
            int randomIndex = RANDOM.nextInt(arr.length);
            Movement.Direction temp = arr[i];
            arr[i] = arr[randomIndex];
            arr[randomIndex] = temp;
        }
        return arr;
    }

}
