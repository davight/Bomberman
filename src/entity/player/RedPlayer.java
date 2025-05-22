package entity.player;

import entity.movement.Direction;
import entity.movement.MovementManager;
import fri.shapesge.Image;
import fri.shapesge.ImageData;
import grid.map.Tile;
import items.Usable;
import util.ImageManager;
import util.ShapesGeListener;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.Optional;

public class RedPlayer extends AbstractPlayer {

    private static final ImageData HEALTH_POINT = ImageManager.getImage("player/red_heart");

    private final ArrayList<Image> healthPoints = new ArrayList<>(AbstractPlayer.START_HEALTH);
    private final ArrayList<Usable> inventory = new ArrayList<>(AbstractPlayer.INVENTORY_SIZE);
    private final ArrayList<Image> hotbar = new ArrayList<>(AbstractPlayer.INVENTORY_SIZE);

    private int activeItem = -1;

    /**
     * Inicializuje noveho cerveneho hraca na danom mieste.
     * @param tile tile, kde sa hrac zobrazi
     */
    public RedPlayer(Tile tile) {
        super(tile);

        int startX = 1000;
        for (int i = 0; i < AbstractPlayer.START_HEALTH; i++) {
            startX -= HEALTH_POINT.getWidth() + 10;
            Image hp = new Image(HEALTH_POINT);
            hp.changePosition(startX, 10);
            hp.makeVisible();
            this.healthPoints.add(hp);
        }

        startX -= 20;
        for (int i = 0; i < AbstractPlayer.INVENTORY_SIZE; i++) {
            startX -= AbstractPlayer.EMPTY_SLOT.getWidth() + 10;
            Image slot = new Image(AbstractPlayer.EMPTY_SLOT);
            slot.changePosition(startX, 10);
            slot.makeVisible();
            this.hotbar.add(slot);
        }
    }

    /**
     * ShapesGE listener na stlacenie klavesy DOWN pre pohyb modreho hraca smerom dole.
     */
    @ShapesGeListener
    public void moveRedDown() {
        super.move(Direction.DOWN);
    }

    /**
     * ShapesGE listener na stlacenie klavesy UP pre pohyb modreho hraca smerom hore.
     */
    @ShapesGeListener
    public void moveRedUp() {
        super.move(Direction.UP);
    }

    /**
     * ShapesGE listener na stlacenie klavesy LEFT pre pohyb modreho hraca smerom vlavo.
     */
    @ShapesGeListener
    public void moveRedLeft() {
        super.move(Direction.LEFT);
    }

    /**
     * ShapesGE listener na stlacenie klavesy RIGHT pre pohyb modreho hraca smerom vpravo.
     */
    @ShapesGeListener
    public void moveRedRight() {
        super.move(Direction.RIGHT);
    }

    /**
     * ShapesGE listener na stlacenie klavesy ENTER pre interagovanie s inventarom.
     */
    @ShapesGeListener
    public void activateRed() {
        super.activate();
    }

    /**
     * @return Zoznam stran s prislusnymi texturami, ktorymi sa hrac moze pohybovat.
     */
    @Override
    public EnumMap<Direction, MovementManager.Pack> getValidDirections() {
        EnumMap<Direction, MovementManager.Pack> pack = new EnumMap<>(Direction.class);
        pack.put(Direction.UP, new MovementManager.Pack("player/red_up_staying", "player/red_up_moving"));
        pack.put(Direction.DOWN, new MovementManager.Pack("player/red_down_staying", "player/red_down_moving"));
        pack.put(Direction.LEFT, new MovementManager.Pack("player/red_left_staying", "player/red_left_moving"));
        pack.put(Direction.RIGHT, new MovementManager.Pack("player/red_right_staying", "player/red_right_moving"));
        return pack;
    }

    /**
     * Updatne inventar podla poctu zivotov.
     * @param currentHealth aktualny pocet zivotov hraca
     */
    @Override
    public void updateHealthPoints(int currentHealth) {
        for (int i = 0; i < this.healthPoints.size(); i++) {
            if (currentHealth >= i + 1) {
                this.healthPoints.get(i).makeVisible();
            } else {
                this.healthPoints.get(i).makeInvisible();
            }
        }
    }

    /**
     * Pokusi sa pridat item do inventara.
     * @param item item, ktory sa ma pridat
     * @return ci sa podarilo pridat item do inventara
     */
    @Override
    public boolean addToInventory(Usable item) {
        if (this.inventory.size() < INVENTORY_SIZE) {
            this.activeItem++;
            this.inventory.add(item);
            this.hotbar.get(this.activeItem).changeImage(item.getInventoryImage());
            return true;
        }
        return false;
    }

    /**
     * Zoberie hracovi item z inventara. (Pokial ho ma)
     * @param item item, ktory sa ma zobrat
     */
    @Override
    public void takeFromInventory(Usable item) {
        if (this.inventory.contains(item)) {
            this.inventory.remove(item);
            this.hotbar.get(this.activeItem).changeImage(AbstractPlayer.EMPTY_SLOT);
            this.activeItem--;
        }
    }

    /**
     * @return Optional item, ktory hrac "aktualne drzi", teda ktory posledne zobral zo zeme.
     */
    @Override
    public Optional<Usable> activeItem() {
        return this.activeItem == -1 ? Optional.empty() : Optional.of(this.inventory.get(this.activeItem));
    }
}
