package entity.player;

import entity.movement.Direction;
import entity.movement.Movable;
import entity.movement.MovementManager;
import fri.shapesge.Image;
import fri.shapesge.ImageData;
import game.Game;
import grid.map.Tile;
import items.Bomb;
import items.Usable;
import resources.ImageManager;
import util.Debug;

import java.util.ArrayList;

/**
 * Trieda player predstavuje hráča ovládaného používateľom pomocou klávesnice.
 */
public abstract class AbstractPlayer implements Movable {

    private static final ImageData HEALTH = ImageManager.getImage("misc/health");
    private static final int INVENTORY_SIZE = 3;
    private static final int BOMB_COOLDOWN = 4000;
    private static final int START_HEALTH = 3;

    private final ArrayList<Image> healthPoints = new ArrayList<>(this.health);
    private final ArrayList<Usable> inventory = new ArrayList<>(3);
    private final MovementManager movement;
    private final Image image;

    private int activeInventorySlot = -1;
    private Tile tile;
    private long bombTime = 0;
    private int health = START_HEALTH;

    /**
     * Inicializuje nového hráča na danom mieste.
     * @param tile tile, na ktorom sa hráč zobrazí
     */
    public AbstractPlayer(Tile tile, boolean reversed) {
        Direction startDirection = this.getValidDirections().keySet().iterator().next();
        this.image = new Image(this.getValidDirections().get(startDirection).staying());

        this.movement = new MovementManager(this);
        this.movement.teleport(startDirection, tile);
        this.image.makeVisible();

        Game.getInstance().manageObject(this);
        this.tile = tile;
        this.bombTime = System.currentTimeMillis();

        int start = reversed ? 800 : 0;
        for (int i = 0; i < this.health; i++) {
            Image tempImage = new Image(HEALTH);
            tempImage.changePosition((i * 25) + 10 + start, 10);
            tempImage.makeVisible();
            this.healthPoints.add(tempImage);
        }
    }

    public void kill() {
        this.hurt(this.health);
    }

    public void die() {
        this.image.changeImage(ImageManager.getImage("player/death"));
        Game.getInstance().stopManagingObject(this);
        Game.getInstance().removePlayer(this);
    }

    public void teleport(Direction direction, Tile tile) {
        this.movement.teleport(direction, tile);
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
    public void hurt(int amount) {
        this.health -= Math.min(amount, this.health);
        Debug.log(Math.max(0, START_HEALTH - this.health));
        for (int i = 0; i < START_HEALTH - this.health; i++) {
            this.healthPoints.get(i).makeInvisible();
            //this.healthPoints.removeLast();
        }
    }

    public void heal(int amount) {
        this.health += amount;
        for (int i = 0; i < Math.min(amount, this.healthPoints.size()); i++) {
            this.healthPoints.get(i).makeVisible();
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
        if (this.movement.isActive()) {
            return;
        }
        if (!this.inventory.isEmpty() && this.activeInventorySlot != -1) {
            Usable item = this.inventory.get(this.activeInventorySlot);
            if (item.onUse(this)) {
                this.takeFromInventory(item);
            }
        } else if (this.bombTime + BOMB_COOLDOWN < System.currentTimeMillis()) {
            new Bomb(this.tile);
            this.bombTime = System.currentTimeMillis();
        }
    }

    public void move(Direction dir) {
        if (this.movement.isActive() || this.health <= 0) {
            return;
        }
        Tile newTile = Game.getInstance().getMap().getTileAtBoard(this.tile.getBoardX() + dir.getX(), this.tile.getBoardY() + dir.getY());
        if (newTile != null && newTile.onPlayerEnterTile(this, this.tile)) {
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
        tile.afterPlayerEnterTile(this, this.tile);
        this.tile = tile;
    }

    @Override
    public Image getImage() {
        return this.image;
    }

    @Override
    public int getTimeBetweenSteps() {
        return 70;
    }

    public boolean addToInventory(Usable item) {
        if (this.inventory.size() < INVENTORY_SIZE) {
            this.inventory.add(item);
            this.activeInventorySlot = this.inventory.size() - 1;
            return true;
        }
        return false;
    }

    public void takeFromInventory(Usable item) {
        if (this.inventory.contains(item)) {
            this.inventory.remove(item);
            this.activeInventorySlot = this.inventory.size() - 1;
        }
    }

}
