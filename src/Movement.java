import fri.shapesge.Image;
import fri.shapesge.ImageData;
import java.util.HashMap;

/**
 * Trieda Movement, ktorá sa stará o plynulý pohyb entít a hráča po plátne.
 */
public class Movement {

    private static final int STEPS = 3;

    private final HashMap<Direction, ImageData[]> registeredDirections = new HashMap<>();
    private final Image image;

    private boolean isActive = false;
    private long lastAction;
    private Tile startTile;
    private int timeBetweenSteps;
    private int step;
    private Direction direction;

    /**
     * Inicializuje novú inštanciu, ktorá sa bude starať o pohyb definovaného obrázka
     * @param image obrázok, ktorý sa bude pohybovať
     */
    public Movement(Image image) {
        this.image = image;
        for (Direction dir : Direction.values()) {
            this.registeredDirections.put(dir, new ImageData[2]);
        }
    }

    /**
     * Registruje smer s danými textúrami.
     *
     * @param direction smer pohybu
     * @param staying textúra státia v danom smere
     * @param moving textúra pohybu v danom smere
     */
    public void registerDirection(Direction direction, ImageData staying, ImageData moving) {
        ImageData[] i = this.registeredDirections.get(direction);
        i[0] = staying;
        i[1] = moving;
    }

    /**
     * Registruje smer s danými textúrami.
     *
     * @param direction smer pohybu
     * @param stayingPath cesta k textúre státia v danom smere
     * @param movingPath cesta k textúre pohybu v danom smere
     */
    public void registerDirection(Direction direction, String stayingPath, String movingPath) {
        this.registerDirection(direction, new ImageData(stayingPath), new ImageData(movingPath));
    }

    /**
     * Vráti hodnotu, či je obrázok v tomto momente v nejakom pohybe.
     */
    public boolean isActive() {
        return this.isActive;
    }

    /**
     * Instantne teleportuje obrázok na daný tile s daným smerom.
     * @param direction smer státia
     * @param at konečný tile
     */
    public void teleport(Direction direction, Tile at) {
        this.direction = direction;
        this.image.changePosition(at.getBoardX() * Tile.TILE_SIZE, at.getBoardY() * Tile.TILE_SIZE);
        this.changeIfNotNull(0);
    }

    /**
     * Spustí pohyb obrázku od štartovacieho tilu daným smerom s daným minimálnym rozosputom medzi krokmi.
     *
     * @param direction smer pohybu
     * @param startTile počiatočný tile
     * @param timeBetweenActions minimálny čas medzi krokmi
     */
    public void startMoving(Direction direction, Tile startTile, int timeBetweenActions) {
        if (this.isActive) {
            return;
        }
        this.direction = direction;
        this.isActive = true;
        this.startTile = startTile;
        this.timeBetweenSteps = timeBetweenActions;
        this.step = 1;
    }

    /**
     * ShapesGE listener ticku. Spravuje kroky pri pohybe obrázku.
     */
    public void tick() {
        if (!this.isActive || this.lastAction + this.timeBetweenSteps > System.currentTimeMillis()) {
            return;
        }
        this.lastAction = System.currentTimeMillis();
        this.moveTo(this.step);
        if (this.step == STEPS) {
            this.changeIfNotNull(0);
            this.isActive = false;
        } else {
            this.changeIfNotNull(1);
            this.step++;
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

    /**
     * Enum smerov s definovanými rozdielmi súradníc.
     */
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
