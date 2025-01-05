import fri.shapesge.Image;

import java.util.Random;

/**
 * Trieda entity predstavuje monštrá, ktoré sa pohybujú po plátne a snažia sa zabiť hráča.
 */
public class Entity {

    private static final Random RANDOM = new Random();

    private final EntityType type;
    private final GameCanvas gameCanvas;
    private final Image image;
    private final Movement movement;

    private long lastMillis;
    private Tile tile;
    private boolean isAlive = true;

    /**
     * Inicializuje novú entitu s daným typom a na danom mieste.
     *
     * @param type typ entity, ktorý sa zobrazí
     * @param tile tile, na ktorom sa entita zobrazí
     */
    public Entity(EntityType type, Tile tile) {
        this.image = new Image(type.getStayingImage());
        this.image.makeVisible();

        this.movement = new Movement(this.image);
        this.movement.registerDirection(Movement.Direction.UP, type.getStayingImage(), type.getUpMovingImage());
        this.movement.registerDirection(Movement.Direction.DOWN, type.getStayingImage(), type.getDownMovingImage());
        this.movement.registerDirection(Movement.Direction.LEFT, type.getStayingImage(), type.getLeftMovingImage());
        this.movement.registerDirection(Movement.Direction.RIGHT, type.getStayingImage(), type.getRightMovingImage());
        this.movement.teleport(Movement.Direction.UP, tile);

        this.gameCanvas = tile.getGameCanvas();
        this.gameCanvas.manageObject(this.movement);
        this.type = type;
        this.tile = tile;
        this.lastMillis = System.currentTimeMillis() + RANDOM.nextInt(type.getRandomMovement());
    }

    /**
     * Vráti tile, na ktorom sa entita aktuálne nachádza.
     */
    public Tile getTile() {
        return this.tile;
    }

    /**
     * ShapesGE listener ticku. Spravuje ako často sa entita pohybuje na základe jej prednastavenej frekvencie.
     */
    public void tick() {
        if (this.lastMillis + this.type.getRandomMovement() > System.currentTimeMillis() || this.movement.isActive() || !this.isAlive) {
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

    /**
     * Zabije entitu a zmaže ju z plátna.
     */
    public void die() {
        if (!this.isAlive) {
            return;
        }
        this.isAlive = false;
        this.image.makeInvisible();
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
