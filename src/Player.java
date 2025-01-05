import fri.shapesge.Image;
import fri.shapesge.ImageData;

import java.util.ArrayList;

/**
 * Trieda player predstavuje hráča ovládaného používateľom pomocou klávesnice.
 */
public class Player {

    private static final ImageData HEALTH = new ImageData("images/misc/health.png");
    private static final int BOMB_COOLDOWN = 1000;

    private final ArrayList<Image> healthPoints = new ArrayList<>(this.health);
    private final GameCanvas gameCanvas;
    private final Movement movement;
    private final Image image;

    private Tile tile;
    private long bombTime = 0;
    private int health = 3;

    /**
     * Inicializuje nového hráča na danom mieste.
     * @param tile tile, na ktorom sa hráč zobrazí
     */
    public Player(Tile tile) {
        this.image = new Image("images/player/down_staying.png");

        this.movement = new Movement(this.image);
        this.movement.registerDirection(Movement.Direction.UP, "images/player/up_staying.png", "images/player/up_moving.png");
        this.movement.registerDirection(Movement.Direction.DOWN, "images/player/down_staying.png", "images/player/down_moving.png");
        this.movement.registerDirection(Movement.Direction.LEFT, "images/player/left_staying.png", "images/player/left_moving.png");
        this.movement.registerDirection(Movement.Direction.RIGHT, "images/player/right_staying.png", "images/player/right_moving.png");
        this.movement.teleport(Movement.Direction.UP, tile);

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
        this.image.changeImage("images/player/death.png");
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
            this.healthPoints.get(this.healthPoints.size() - 1).makeInvisible();
            this.healthPoints.remove(this.healthPoints.size() - 1);
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
        this.move(Movement.Direction.DOWN);
    }

    /**
     * ShapesGE listener pre kliknutie klávesy W alebo šípka hore. Spustí pokus o posun smerom hore.
     */
    public void moveUp() {
        this.move(Movement.Direction.UP);
    }

    /**
     * ShapesGE listener pre kliknutie klávesy A alebo šípka vľavo. Spustí pokus o posun smerom vľavo.
     */
    public void moveLeft() {
        this.move(Movement.Direction.LEFT);
    }

    /**
     * ShapesGE listener pre kliknutie klávesy D alebo šípka vpravo. Spustí pokus o posun smerom vpravo.
     */
    public void moveRight() {
        this.move(Movement.Direction.RIGHT);
    }

    private void move(Movement.Direction dir) {
        if (this.movement.isActive() || this.health <= 0) {
            return;
        }
        Tile newTile = this.gameCanvas.getTileAtBoard(this.tile.getBoardX() + dir.getX(), this.tile.getBoardY() + dir.getY());
        if (newTile != null && newTile.playerEnterTile()) {
            this.movement.startMoving(dir, this.tile, 70);
            this.tile = newTile;
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

}
