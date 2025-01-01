package game.entity;

import fri.shapesge.Image;
import fri.shapesge.Manager;
import game.canvas.EntityType;
import game.canvas.GameCanvas;
import game.canvas.Tile;

import java.util.Random;

public class Entity {

    private static final Manager ENTITY_MANAGER = new Manager();
    private static final Random RANDOM = new Random();

    private final EntityType type;
    private final GameCanvas gameCanvas;
    private final Image image;

    private long lastMillis;
    private Tile tile;
    private int moveStatus;
    private Tile newTile;

    public Entity(EntityType type, Tile tile) {
        ENTITY_MANAGER.manageObject(this);
        this.image = new Image(type.getImageData());
        this.type = type;
        this.tile = tile;
        this.lastMillis = System.currentTimeMillis() + RANDOM.nextInt(type.getRandomMovement());
        this.gameCanvas = tile.getGameCanvas();
        this.moveStatus = 0;
    }

    public Tile getTile() {
        return this.tile;
    }

    public void tik() {
        if (this.lastMillis + this.type.getRandomMovement() < System.currentTimeMillis()) {
            return;
        }
        if (!this.generateMovement()) {
            return;
        }
        this.lastMillis = System.currentTimeMillis();
        switch (this.moveStatus) {
            case 1:
                this.image.changePosition(calculateNthPosition(this.tile.getBoardX(), this.movement.getX(), 2), calculateNthPosition(this.tile.getBoardY(), this.movement.getY(), 2));
                this.moveStatus++;
                break;
            case 2:
                this.image.changePosition(calculateLastPosition(this.tile.getBoardX(), this.movement.getX()), calculateLastPosition(this.tile.getBoardY(), this.movement.getY()));
                this.tile = this.newTile;
                this.moveStatus = 0;
                break;

        }

    }

    private boolean moveTo(int x, int y) {
        return true;
    }

    private boolean generateMovement() {
        Tile temp = null;
        for (int direction : Entity.shuffleArray(new int[]{0, 1, 2, 3})) {
            switch (direction) {
                case 0: // LEFT
                    temp = this.gameCanvas.getTileAtBoard(-1, 0);
                    break;
                case 1: // RIGHT
                    temp = this.gameCanvas.getTileAtBoard(1, 0);
                    break;
                case 2: // UP
                    temp = this.gameCanvas.getTileAtBoard(0, -1);
                    break;
                case 3: // DOWN
                    temp = this.gameCanvas.getTileAtBoard(0, 1);
                    break;
            }
            if (temp != null) {
                break;
            }
        }
        if (temp == null) {
            return false;
        }
        this.moveStatus = 1;
        this.newTile = temp;
        return true;
    }

    public void die() {
        ENTITY_MANAGER.stopManagingObject(this);
        if (this.type == EntityType.RED) {
            this.gameCanvas.spawnBomb(this.tile);
        }
    }

    private static int[] shuffleArray(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            int randomIndex = RANDOM.nextInt(arr.length);
            int temp = arr[i];
            arr[i] = arr[randomIndex];
            arr[randomIndex] = temp;
        }
        return arr;
    }

    private static int calculateNthPosition(int start, int dir, int n) {
        return (start * Tile.TILE_SIZE) + ((Tile.TILE_SIZE / n) * dir);
    }

    private static int calculateLastPosition(int start, int dir) {
        return calculateNthPosition(start, dir, 1);
    }


}
