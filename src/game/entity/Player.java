package game.entity;

import fri.shapesge.Image;
import fri.shapesge.Manager;
import game.canvas.GameCanvas;
import game.canvas.Movement;
import game.canvas.Tile;

public class Player {

    private static final Manager PLAYER_MANAGER = new Manager();
    private static final int BOMB_COOLDOWN = 1000;

    private final GameCanvas gameCanvas;
    private final Movement movement;
    private final Image image;

    private Tile tile;
    private long bombTime = 0;

    public Player(Tile tile) {
        this.image = new Image("game/images/player/down_staying.png");
        this.movement = new Movement(this.image);
        this.movement.registerDirection(Movement.Direction.UP, "game/images/player/up_staying.png", "game/images/player/up_moving.png");
        this.movement.registerDirection(Movement.Direction.DOWN, "game/images/player/down_staying.png", "game/images/player/down_moving.png");
        this.movement.registerDirection(Movement.Direction.LEFT, "game/images/player/left_staying.png", "game/images/player/left_moving.png");
        this.movement.registerDirection(Movement.Direction.RIGHT, "game/images/player/right_staying.png", "game/images/player/right_moving.png");
        this.movement.teleport(Movement.Direction.UP, tile);
        this.image.makeVisible();
        this.gameCanvas = tile.getGameCanvas();
        this.tile = tile;
        PLAYER_MANAGER.manageObject(this);
    }

    public void die() {
        this.image.changeImage("game/images/player/death.png");
        PLAYER_MANAGER.stopManagingObject(this);
    }

    public Tile getTile() {
        return this.tile;
    }

    ////////////////////
    /// Player actions
    //////////////////

    public void pressEnter() {
        if (!this.movement.isActive() && this.bombTime + BOMB_COOLDOWN < System.currentTimeMillis()) {
            if (this.gameCanvas.spawnBomb(this.tile)) {
                this.image.makeInvisible();
                this.image.makeVisible();
                this.bombTime = System.currentTimeMillis();
            }
        }
    }

    public void moveDown() {
        this.move(Movement.Direction.DOWN);
    }

    public void moveUp() {
        this.move(Movement.Direction.UP);
    }

    public void moveLeft() {
        this.move(Movement.Direction.LEFT);
    }

    public void moveRight() {
        this.move(Movement.Direction.RIGHT);
    }

    private void move(Movement.Direction dir) {
        if (this.movement.isActive()) {
            return;
        }
        Tile newTile = this.gameCanvas.getTileAtBoard(this.tile.getBoardX() + dir.getX(), this.tile.getBoardY() + dir.getY());
        if (newTile != null && newTile.playerEnterTile()) {
            this.movement.startMoving(dir, this.tile, 70);
            this.tile = newTile;
        }
    }

}
