package entity.player;

import entity.movement.Direction;
import entity.movement.Movable;
import entity.movement.MovementManager;
import events.EventManager;
import events.PlayerDeathEvent;
import events.PlayerEnterTileEvent;
import fri.shapesge.Image;
import fri.shapesge.ImageData;
import game.Game;
import grid.map.Map;
import grid.map.Tile;
import items.Bomb;
import items.Usable;
import util.ImageManager;
import util.Util;
import util.Waiter;

import java.util.ArrayList;
import java.util.Optional;

/**
 * Trieda player predstavuje hráča ovládaného používateľom pomocou klávesnice.
 */
public abstract class AbstractPlayer implements Movable {

    protected static final ImageData EMPTY_SLOT = ImageManager.getImage("player/empty_inventory_slot");
    protected static final int START_HEALTH = 3;
    protected static final int INVENTORY_SIZE = 3;
    private static final int BOMB_COOLDOWN = 4000;

    private final ArrayList<Usable> inventory = new ArrayList<>(3);
    private final MovementManager movement;
    private final Image image;

    private int activeInventorySlot = -1;
    private Tile tile;
    private long bombTime = 0;
    private int health = START_HEALTH;
    private boolean isAlive = true;

    /**
     * Inicializuje nového hráča na danom mieste.
     * @param tile tile, na ktorom sa hráč zobrazí
     */
    public AbstractPlayer(Tile tile, boolean reversed) {
        Direction startDirection = Util.randomElement(this.getValidDirections().keySet());
        this.image = new Image(this.getValidDirections().get(startDirection).staying());

        this.movement = new MovementManager(this);
        this.movement.teleport(startDirection, tile);
        this.image.makeVisible();

        this.tile = tile;
        this.bombTime = System.currentTimeMillis();
        Game.manageObject(this);
    }

    public void kill() {
        if (!this.isAlive) {
            return;
        }
        this.health = 0;
        this.die();
    }

    private void die() {
        this.updateHealthPoints(0);
        this.isAlive = false;
        if (this.movement.isActive()) {
            this.movement.stopMoving();
        }
        this.image.changeImage(ImageManager.getImage("player/death"));
        new Waiter(4000, (w) -> this.image.makeInvisible()).waitAndRun();
        Game.stopManagingObject(this);
        EventManager.fireEvent(new PlayerDeathEvent(this));
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
        if (!this.isAlive) {
            return;
        }
        this.health -= Math.min(amount, this.health);
        if (this.health <= 0 ) {
            this.die();
        } else {
            this.updateHealthPoints(this.health);
        }
    }

    public void heal(int amount) {
        if (!this.isAlive) {
            return;
        }
        this.health = Math.min(amount + this.health, START_HEALTH);
        this.updateHealthPoints(this.health);
    }

    /**
     * Vráti počet životov, ktoré hráč má.
     */
    public int getHealth() {
        return this.health;
    }

    public boolean isAlive() {
        return this.isAlive;
    }

    /**
     * ShapesGE listener pre kliknutie klávesy enter. Po kliknutí hráč položí na svoje miesto bombu.
     * Pred ďalším položením má určitý cooldown.
     */
    public void pressEnter() {
        if (this.movement.isActive() || !this.isAlive) {
            return;
        }
        if (!this.inventory.isEmpty() && this.activeInventorySlot != -1) {
            Usable item = this.inventory.get(this.activeInventorySlot);
            if (item.onUse(this)) {
                this.takeFromInventory(item);
            }
        } else if (this.bombTime + BOMB_COOLDOWN < System.currentTimeMillis()) {
            new Bomb(this.tile, Game.getLevelId());
            this.bombTime = System.currentTimeMillis();
        }
    }

    public void move(Direction dir) {
        if (this.movement.isActive() || !this.isAlive) {
            return;
        }
        Tile newTile = Map.getTileAtBoard(this.tile.getBoardX() + dir.getX(), this.tile.getBoardY() + dir.getY());
        if (newTile != null && newTile.canEnemyEnterTile(this, this.tile)) {
            this.movement.startMoving(dir, this.tile, newTile);
        }
    }

    @Override
    public void afterSuccessfulMovement(Tile tile) {
        if (this.isAlive) {
            EventManager.fireEvent(new PlayerEnterTileEvent(this, tile, this.tile));
            this.tile = tile;
        } else {
            this.image.changeImage(ImageManager.getImage("player/death"));
        }
    }

    @Override
    public Image getImage() {
        return this.image;
    }

    @Override
    public int getTimeBetweenSteps() {
        return 70;
    }

    public abstract void updateHealthPoints(int currentHealth);

    public abstract boolean addToInventory(Usable item);

    public abstract boolean takeFromInventory(Usable item);

    public abstract Optional<Usable> activeItem();

    public boolean addToInventory2(Usable item) {
        if (this.inventory.size() < INVENTORY_SIZE) {
            this.inventory.add(item);
            this.activeInventorySlot = this.inventory.size() - 1;
            return true;
        }
        return false;
    }

    public void takeFromInventory2(Usable item) {
        if (this.inventory.contains(item)) {
            this.inventory.remove(item);
            this.activeInventorySlot = this.inventory.size() - 1;
        }
    }
}
