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
 * Abstraktna trieda ako template pre vytvaranie hracov.
 */
public abstract class AbstractPlayer implements Movable {

    protected static final ImageData EMPTY_SLOT = ImageManager.getImage("player/empty_inventory_slot");
    private static final ImageData DEAD = ImageManager.getImage("player/death");
    protected static final int START_HEALTH = 3;
    protected static final int INVENTORY_SIZE = 3;
    private static final int BOMB_COOLDOWN = 4000;

    private final ArrayList<Usable> inventory = new ArrayList<>(3);
    private final MovementManager movement;
    private final Image image;

    private Tile tile;
    private long bombTime = 0;
    private int health = START_HEALTH;
    private boolean isAlive = true;

    /**
     * Inicializuje noveho hraca na danom mieste.
     * @param tile tile, na ktorom sa hrac zobrazi
     */
    public AbstractPlayer(Tile tile) {
        Direction startDirection = Util.randomElement(this.getValidDirections().keySet());
        this.image = new Image(this.getValidDirections().get(startDirection).staying());

        this.movement = new MovementManager(this);
        this.movement.teleport(startDirection, tile);
        this.image.makeVisible();

        this.tile = tile;
        Game.manageObject(this);
    }

    /**
     * Zabije hraca, ak este zije.
     */
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

    /**
     * Teleportuje hraca na dane miesto.
     * @param direction smer, ktorym bude otoceny po teleporte
     * @param tile miesto, na ktore bude teleportovany
     */
    public void teleport(Direction direction, Tile tile) {
        this.movement.teleport(direction, tile);
    }

    /**
     * @return Vrati tile, na ktorom sa hrac aktualne nachadza.
     */
    public Tile getTile() {
        return this.tile;
    }

    /**
     * Zoberie urcity pocet zivotov hraca. Pokial zivoty hraca klesnu pod 0 umrie.
     * @param amount pocet zivotov, ktore sa vezmu
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

    /**
     * Prida hracovi urcity pocet zivotov, pokial este zije a zaroven nema plny pocet zivotov.
     * @param amount pocet zivotov, ktore sa hracovi pridaju
     * @return Ci sa podarilo hracovi pridat aspon jeden zivot
     */
    public boolean heal(int amount) {
        if (!this.isAlive) {
            return false;
        }
        if (this.health == START_HEALTH) {
            return false;
        }
        this.health = Math.min(amount + this.health, START_HEALTH);
        this.updateHealthPoints(this.health);
        return true;
    }

    /**
     * @return Vrati pocet zivotov, ktore hrac aktualne ma.
     */
    public int getHealth() {
        return this.health;
    }

    /**
     * @return Ci hrac zije.
     */
    public boolean isAlive() {
        return this.isAlive;
    }

    /**
     * ShapesGE listener pre kliknutie klávesy enter. Po kliknutí hráč položí na svoje miesto bombu.
     * Pred ďalším položením má určitý cooldown.
     */

    /**
     * Interagovanie s inventarom.
     */
    public void activate() {
        if (this.movement.isActive() || !this.isAlive) {
            return;
        }
        Optional<Usable> item = this.activeItem();
        if (item.isPresent() && item.get().onUse(this)) {
            this.takeFromInventory(item.get());
        } else if (this.bombTime + BOMB_COOLDOWN < System.currentTimeMillis()) {
            new Bomb(this.tile, Game.getLevelId());
            this.bombTime = System.currentTimeMillis();
        }
    }

    /**
     * Spusti pokus o pohyb danym smerom.
     * @param dir smer pohybu
     */
    public void move(Direction dir) {
        if (this.movement.isActive() || !this.isAlive) {
            return;
        }
        Tile newTile = Map.getTileAtBoard(this.tile.getBoardX() + dir.getX(), this.tile.getBoardY() + dir.getY());
        if (newTile != null && newTile.canPlayerEnterTile(this, this.tile)) {
            this.movement.startMoving(dir, this.tile, newTile);
        }
    }

    /**
     * Vykona sa po uspesnom presunuti hraca na novy newTile.
     * @param newTile Tile, na ktory sa hrac posunul.
     */
    @Override
    public void afterSuccessfulMovement(Tile newTile) {
        if (this.isAlive) {
            Tile oldTile = this.tile;
            this.tile = newTile;
            EventManager.fireEvent(new PlayerEnterTileEvent(this, newTile, oldTile));
        } else {
            this.image.changeImage(DEAD);
        }
    }

    /**
     * @return Obrazok hraca, ktory sa bude pohybovat
     */
    @Override
    public Image getImage() {
        return this.image;
    }

    /**
     * @return Cas v milisekundach medzi krokmi pri pohybe.
     */
    @Override
    public int getTimeBetweenSteps() {
        return 70;
    }

    /**
     * Updatne inventar podla poctu zivotov.
     * @param currentHealth aktualny pocet zivotov hraca
     */
    public abstract void updateHealthPoints(int currentHealth);

    /**
     * Pokusi sa pridat item do inventara.
     * @param item item, ktory sa ma pridat
     * @return ci sa podarilo pridat item do inventara
     */
    public abstract boolean addToInventory(Usable item);

    /**
     * Zoberie hracovi item z inventara. (Pokial ho ma)
     * @param item item, ktory sa ma zobrat
     */
    public abstract void takeFromInventory(Usable item);

    /**
     * @return Optional item, ktory hrac "aktualne drzi", teda ktory posledne zobral zo zeme.
     */
    public abstract Optional<Usable> activeItem();

}
