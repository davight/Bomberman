package grid.map;

import entity.enemy.AbstractEnemy;
import entity.player.AbstractPlayer;
import events.AfterEntityEnterBlockListener;
import events.EntityEnterBlockEvent;
import events.PlayerEnterBlockEvent;
import fri.shapesge.Image;
import grid.blocks.AbstractBlock;
import grid.blocks.BlockRegister;
import items.AbstractItem;
import util.Debug;

import java.util.ArrayList;

/**
 * Trieda tile, ktorá reprezentuje pomyselný priestor na hernom plátne. Na tomto priestore sa vykreslujú blocky.
 */
public class Tile {

    /**
     * Veľkosť tilu a teda zároveň aj odporúčaná veľkosť blocku, ktorý sa má na ňom vykresliť
     */
    public static final int TILE_SIZE = 50;

    private final ArrayList<AbstractEnemy> enemies = new ArrayList<>();
    private final ArrayList<AbstractPlayer> players = new ArrayList<>();
    private final ArrayList<AbstractItem> items = new ArrayList<>();

    private final Image image;
    private final int boardX;
    private final int boardY;

    private AbstractBlock block;

    public Tile(int boardX, int boardY, AbstractBlock block) {
        this.block = block;
        this.boardX = boardX;
        this.boardY = boardY;
        this.image = new Image(block.getTexture());
        this.image.changePosition(Tile.TILE_SIZE * boardX, Tile.TILE_SIZE * boardY);
        this.image.makeVisible();
    }

    public boolean onEntityEnterTile(AbstractEnemy entity, Tile oldTile) {
        if (this.block == null) {
            return false;
        }
        if (!this.enemies.isEmpty()) {
            return false;
        }
        return this.block.onEntityEnterBlock(new EntityEnterBlockEvent(entity, this, oldTile));
    }

    public void afterEntityEnterTile(AbstractEnemy enemy, Tile oldTile) {
        if (oldTile != null) {
            oldTile.enemies.remove(enemy);
        }
        this.enemies.add(enemy);
        if (this.block instanceof AfterEntityEnterBlockListener listener) {
            listener.afterEnemyEnterBlock(new EntityEnterBlockEvent(enemy, this, oldTile));
        }
        for (AbstractPlayer p : this.players) {
            enemy.attack(p);
        }
    }

    public boolean onPlayerEnterTile(AbstractPlayer player, Tile oldTile) {
        if (this.block == null) {
            return false;
        }
        if (!this.enemies.isEmpty()) {
            Debug.log("");
            return false;
        }
        return this.block.onPlayerEnterBlock(new PlayerEnterBlockEvent(player, this, oldTile));
    }

    public void afterPlayerEnterTile(AbstractPlayer player, Tile oldTile) {
        if (this.block instanceof AfterEntityEnterBlockListener listener) {
            listener.afterPlayerEnterBlock(new PlayerEnterBlockEvent(player, this, oldTile));
        }
        if (oldTile != null) {
            oldTile.players.remove(player);
        }
        this.players.add(player);
        for (AbstractEnemy e : this.enemies) {
            e.attack(player);
        }
        ArrayList<AbstractItem> toRemove = new ArrayList<>(); // aby som needitoval a zaroven neprechadzal cez list
        for (AbstractItem i : this.items) {
            if (i.onPickup(player)) {
                toRemove.add(i);
            }
        }
        this.items.removeAll(toRemove);
    }

    public void spawnItem(AbstractItem item) {
        this.items.add(item);
    }

    /**
     * Preverí a vráti hodnotu či je možné položenie bomby na daný tile.
     */
    public boolean bombEnterTile() {
        return false; // fix
    }

    public int getX() {
        return this.boardX * TILE_SIZE;
    }

    public int getY() {
        return this.boardY * TILE_SIZE;
    }

    /**
     * Vráti x-ovú súradnicu z pola na hernom plátne, na ktorom sa tento tile nachádza
     */
    public int getBoardX() {
        return this.boardX;
    }

    /**
     * Vráti y-ovú súradnicu z pola na hernom plátne, na ktorom sa tento tile nachádza
     */
    public int getBoardY() {
        return this.boardY;
    }

    /**
     * Vráti herné pole, na ktorom sa tento tile nachádza.
     */

    /**
     * Zabije entitu alebo hráča, ktorí sa nachádzajú na tomto tile.
     */
    public void killAll() {
        for (AbstractPlayer p : this.players) {
            // die
            System.out.println("PLAYER DIE");
        }
        for (AbstractEnemy e : this.enemies) {
            e.die();
        }
    }

    public void hurtAll(int amount) {
        for (AbstractPlayer p : this.players) {
            p.hurt(amount);
        }
        for (AbstractEnemy e : this.enemies) {
            e.hurt(amount);
        }
    }

    public AbstractBlock getBlock() {
        return this.block;
    }

    public void setBlock(BlockRegister block) {
        this.setBlock(block.getNew());
    }

    public void setBlock(AbstractBlock block) {
        this.block = block;
        this.image.changeImage(this.block.getTexture());
    }
}
