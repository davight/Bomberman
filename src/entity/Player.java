package entity;

import entity.movement.Direction;
import entity.movement.Movable;
import entity.movement.MovementManager;
import fri.shapesge.Image;
import fri.shapesge.ImageData;
import grid.GameCanvas;
import grid.Tile;
import resources.ImageManager;

import java.util.ArrayList;
import java.util.Map;

/**
 * Trieda player predstavuje hráča ovládaného používateľom pomocou klávesnice.
 */
public class Player implements Movable {

    private static final ImageData HEALTH = ImageManager.getImage("misc/health");
    private static final int BOMB_COOLDOWN = 1000;

    private final ArrayList<Image> healthPoints = new ArrayList<>(this.health);
    private final GameCanvas gameCanvas;
    private final MovementManager movement;
    private final Image image;

    private Tile tile;
    private long bombTime = 0;
    private int health = 3;

    /**
     * Inicializuje nového hráča na danom mieste.
     * @param tile tile, na ktorom sa hráč zobrazí
     */
    public Player(Tile tile) {
        this.image = new Image(ImageManager.getImage("player/down_staying"));

        this.movement = new MovementManager(this);
        this.movement.teleport(Direction.UP, tile);

        this.image.makeVisible();
        this.gameCanvas = tile.getGameCanvas();
        this.gameCanvas.manageObject(this.movement);
        this.tile = tile;

        for (int i = 0; i < this.health; i++) {
            Image tempImage = new Image(HEALTH);
            tempImage.changePosition((i * 25) + 10, 10);
            tempImage.makeVisible();
            this.healthPoints.add(tempImage);
        }
    }

    private void die() {
        this.image.changeImage(ImageManager.getImage("player/death"));
        this.gameCanvas.lose();
    }

    /**
     * Vráti tile, na ktorom sa hráč aktuálne nachádza.
     */
    public Tile getTile() {
        return this.tile;
    }

    /**
     * Zoberie určitý počet životov hráča. Pokiaľ životy hráča klesnú pod 0 zabije ho.
     * @param amount počet životov, ktoré sa vezmú
     */
    public void takeDamage(int amount) {
        this.health -= amount;
        for (int i = 0; i < amount && !this.healthPoints.isEmpty(); i++) {
            this.healthPoints.getLast().makeInvisible();
            this.healthPoints.removeLast();
        }
    }

    /**
     * Vráti počet životov, ktoré hráč má.
     */
    public int getHealth() {
        return this.health;
    }

    /**
     * ShapesGE listener pre kliknutie klávesy enter. Po kliknutí hráč položí na svoje miesto bombu.
     * Pred ďalším položením má určitý cooldown.
     */
    public void pressEnter() {
        if (!this.movement.isActive() && this.bombTime + BOMB_COOLDOWN < System.currentTimeMillis()) {
            if (this.gameCanvas.spawnBomb(this.tile)) {
                this.image.makeInvisible();
                this.image.makeVisible();
                this.bombTime = System.currentTimeMillis();
            }
        }
    }

    /**
     * ShapesGE listener pre kliknutie klávesy S alebo šípka dole. Spustí pokus o posun smerom dole.
     */
    public void moveDown() {
        this.move(Direction.DOWN);
    }

    /**
     * ShapesGE listener pre kliknutie klávesy W alebo šípka hore. Spustí pokus o posun smerom hore.
     */
    public void moveUp() {
        this.move(Direction.UP);
    }

    /**
     * ShapesGE listener pre kliknutie klávesy A alebo šípka vľavo. Spustí pokus o posun smerom vľavo.
     */
    public void moveLeft() {
        this.move(Direction.LEFT);
    }

    /**
     * ShapesGE listener pre kliknutie klávesy D alebo šípka vpravo. Spustí pokus o posun smerom vpravo.
     */
    public void moveRight() {
        this.move(Direction.RIGHT);
    }

    private void move(Direction dir) {
        if (this.movement.isActive() || this.health <= 0) {
            return;
        }
        Tile newTile = this.gameCanvas.getTileAtBoard(this.tile.getBoardX() + dir.getX(), this.tile.getBoardY() + dir.getY());
        if (newTile != null && newTile.onPlayerEnterTile(this.tile)) {
            this.movement.startMoving(dir, this.tile, newTile);
        }
    }

    /**
     * ShapesGE listener ticku. Zabije hráča ak má menej ako 1 život a zároveň sa nepohybuje.
     */
    public void tick() {
        if (!this.movement.isActive() && this.health <= 0) {
            this.die();
        }
    }

    @Override
    public void afterMovementEvent(Tile tile) {
        this.tile = tile;
        tile.afterPlayerEnterTile();
    }

    @Override
    public Image getImage() {
        return this.image;
    }

    @Override
    public int getTimeBetweenSteps() {
        return 70;
    }

    @Override
    public Map<Direction, MovementManager.Pack> getValidDirections() {
        return Map.of(
                Direction.UP, new MovementManager.Pack(ImageManager.getImage("player/up_staying"), ImageManager.getImage("player/up_moving")),
                Direction.DOWN, new MovementManager.Pack(ImageManager.getImage("player/down_staying"), ImageManager.getImage("player/down_moving")),
                Direction.LEFT, new MovementManager.Pack(ImageManager.getImage("player_left_staying"), ImageManager.getImage("player/left_moving")),
                Direction.RIGHT, new MovementManager.Pack(ImageManager.getImage("player/right_staying"), ImageManager.getImage("player/right_moving"))
        );
    }

}
