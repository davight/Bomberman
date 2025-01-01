package game.entity;

import fri.shapesge.Image;
import fri.shapesge.ImageData;
import fri.shapesge.Manager;
import game.canvas.GameCanvas;
import game.canvas.MovementHelper;
import game.canvas.PlayerMovement;
import game.canvas.Tile;

import java.util.HashMap;
import java.util.Map;

public class Player {

    private static final Manager PLAYER_MANAGER = new Manager();
    private static final int MOVEMENT_DURATION = 70;
    private static final int BOMB_COOLDOWN = 3000;
    private static final Map<MovementHelper, ImageData> MAPPED_IMAGES = Map.of(
            MovementHelper.UP, new ImageData(""),
            MovementHelper.DOWN, new ImageData(""),
            MovementHelper.LEFT, new ImageData(""),
            MovementHelper.RIGHT, new ImageData("")
    );

    private final GameCanvas gameCanvas;
    private final Image image;

    private Tile tile;
    private long moveTime;
    private int moveStatus;
    private Tile newTile;
    private MovementHelper movement;
    private long bombTime;

    public Player(Tile tile) {
        this.image = new Image(MAPPED_IMAGES.get(MovementHelper.UP));
        this.image.changePosition(tile.getBoardX() * Tile.TILE_SIZE, tile.getBoardY() * Tile.TILE_SIZE);
        this.image.makeVisible();
        this.gameCanvas = tile.getGameCanvas();
        this.tile = tile;
        this.moveTime = 0;
        this.moveStatus = 0;
        this.newTile = null;
        this.movement = null;
        this.bombTime = 0;
        PLAYER_MANAGER.manageObject(this);
    }

    public void die() {
        this.image.makeInvisible();
        PLAYER_MANAGER.stopManagingObject(this);
    }

    public Tile getTile() {
        return this.tile;
    }

    ////////////////////
    /// Player actions
    //////////////////

    public void pressEnter() {
        if (this.moveStatus == 0 && this.bombTime + BOMB_COOLDOWN < System.currentTimeMillis()) {
            if (this.gameCanvas.spawnBomb(this.tile)) {
                this.image.makeInvisible();
                this.image.makeVisible();
                this.bombTime = System.currentTimeMillis();
            }
        }
    }

    public void moveDown() {
        this.move(MovementHelper.DOWN);
    }

    public void moveUp() {
        this.move(MovementHelper.UP);
    }

    public void moveLeft() {
        this.move(MovementHelper.LEFT);
    }

    public void moveRight() {
        this.move(MovementHelper.RIGHT);
    }

    private void move(MovementHelper movement) {
        //this.image.changeImage(movement.getStaying());
        Tile temp = this.gameCanvas.getTileAtBoard(this.tile.getBoardX() + movement.getX(), this.tile.getBoardY() + movement.getY());
        if (temp == null || !temp.playerEnterTile() || this.moveStatus != 0) {
            return;
        }
        this.movement = movement;
        this.newTile = temp;
        this.moveTime = System.currentTimeMillis();
        this.moveStatus = 1;
    }

    private void moveOld(PlayerMovement movement) {
        this.image.changeImage(movement.getStaying());
        Tile temp = this.gameCanvas.getTileAtBoard(this.tile.getBoardX() + movement.getX(), this.tile.getBoardY() + movement.getY());
        if (temp == null || !temp.playerEnterTile() || this.moveStatus != 0) {
            return;
        }
        //this.movement = movement;
        this.newTile = temp;
        this.moveTime = System.currentTimeMillis();
        this.moveStatus = 1;
    }

    public void tick() {
        if (this.moveStatus == 0 || this.moveTime + MOVEMENT_DURATION > System.currentTimeMillis()) {
            return;
        }
        this.moveTime = System.currentTimeMillis();
        int[] xy;
        switch (this.moveStatus) {
            case 1:
                this.image.changeImage(this.movement.getMoving());
                xy = this.movement.calculateNthPosition(this.tile, 1, 2);
                this.image.changePosition(xy[0], xy[1]);
                this.moveStatus++;
                break;
            case 2:
                //this.image.changeImage(this.movement.getStaying());
                xy = this.movement.calculateLastPosition(this.tile, 2);
                this.image.changePosition(xy[0], xy[1]);
                this.tile = this.newTile;
                this.moveStatus = 0;
                break;
        }
    }

    private static int calculateNthPosition(int start, int dir, int n) {
        return (start * Tile.TILE_SIZE) + ((Tile.TILE_SIZE / n) * dir);
    }

    private static int calculateLastPosition(int start, int dir) {
        return calculateNthPosition(start, dir, 1);
    }

}
