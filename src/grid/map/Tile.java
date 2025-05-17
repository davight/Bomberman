package grid.map;

import entity.enemy.AbstractEnemy;
import entity.player.AbstractPlayer;
import events.EnemyDeathEvent;
import events.EnemyStepOnBlockEvent;
import events.EnemyEnterTileEvent;
import events.EventManager;
import events.PlayerDeathEvent;
import events.PlayerEnterTileEvent;
import events.PlayerStepOnBlockEvent;
import fri.shapesge.Image;
import fri.shapesge.ImageData;
import grid.blocks.AbstractBlock;
import grid.blocks.BlockRegister;
import items.AbstractItem;
import util.ImageManager;

import java.util.ArrayList;

/**
 * Trieda tile, ktorá reprezentuje pomyselný priestor na hernom plátne. Na tomto priestore sa vykreslujú blocky.
 */
public class Tile {

    /**
     * Veľkosť tilu a teda zároveň aj odporúčaná veľkosť blocku, ktorý sa má na ňom vykresliť
     */
    public static final int TILE_SIZE = 50;
    private static final ImageData EMPTY_TILE = ImageManager.getImage("blocks/empty");

    private final ArrayList<AbstractEnemy> enemies = new ArrayList<>();
    private final ArrayList<AbstractPlayer> players = new ArrayList<>();
    private final ArrayList<AbstractItem> items = new ArrayList<>();
    private final Image image;
    private final int boardX;
    private final int boardY;

    private AbstractBlock block;

    static {
        // Pass directly to the corresponding tile
        EventManager.registerHandler(PlayerEnterTileEvent.class, (e) -> e.newTile().afterPlayerEnterTile(e));
        EventManager.registerHandler(EnemyEnterTileEvent.class, (e) -> e.newTile().afterEntityEnterTile(e));
        // Handle static
        // TODO popremyslat nad Living interfacom, ci je to vobec dobry napad, ci mu pridat getTile metodu a mozno aj zvysne ostatne
        // TODO robit itemy, GUI, testovanie, mam na to cas do nedele
        EventManager.registerHandler(PlayerDeathEvent.class, (e) -> e.player().getTile().players.remove(e.player()));
        EventManager.registerHandler(EnemyDeathEvent.class, (e) -> e.enemy().getTile().enemies.remove(e.enemy()));
    }

    public Tile(int boardX, int boardY) {
        this.boardX = boardX;
        this.boardY = boardY;
        this.image = new Image(EMPTY_TILE);
        this.image.changePosition(Tile.TILE_SIZE * boardX, Tile.TILE_SIZE * boardY);
        this.image.makeVisible();
    }

    public boolean canEnemyEnterTile(AbstractEnemy entity, Tile oldTile) {
        if (this.block == null) {
            return false;
        }
        if (!this.enemies.isEmpty()) {
            return false;
        }
        return this.block.canEnemyEnterBlock(new EnemyEnterTileEvent(entity, this, oldTile));
    }

    public void afterEntityEnterTile(EnemyEnterTileEvent event) {
        if (event.oldTile() != null) {
            event.oldTile().enemies.remove(event.entity());
        }
        this.enemies.add(event.entity());
        new ArrayList<>(this.players).forEach((p) -> event.entity().attack(p));
        EventManager.fireEvent(new EnemyStepOnBlockEvent(this.block, event.entity()));
    }

    public boolean canEnemyEnterTile(AbstractPlayer player, Tile oldTile) {
        if (this.block == null) {
            return false;
        }
        return this.block.canPlayerEnterBlock(new PlayerEnterTileEvent(player, this, oldTile));
    }

    public void afterPlayerEnterTile(PlayerEnterTileEvent event) {
        if (event.oldTile() != null) {
            event.oldTile().players.remove(event.player());
        }
        this.players.add(event.player());
        new ArrayList<>(this.enemies).forEach((e) -> e.attack(event.player()));
        ArrayList<AbstractItem> toRemove = new ArrayList<>(); // aby som needitoval a zaroven neprechadzal cez list
        for (AbstractItem i : this.items) {
            if (i.canPickup(event.player())) {
                toRemove.add(i);
            }
        }
        this.items.removeAll(toRemove);
        EventManager.fireEvent(new PlayerStepOnBlockEvent(event.player(), this.block));
    }

    public void spawnItem(AbstractItem item) {
        this.items.add(item);
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
     * Zabije entitu alebo hráča, ktorí sa nachádzajú na tomto tile.
     */
    public void killAll() {
        new ArrayList<>(this.players).forEach(AbstractPlayer::kill);
        new ArrayList<>(this.enemies).forEach(AbstractEnemy::kill);
    }

    public void removeEnemy(AbstractEnemy e) {
        this.enemies.remove(e);
    }

    public AbstractBlock getBlock() {
        return this.block;
    }

    public void update() {
        this.image.changeImage(this.block.getTexture());
    }

    public void setBlock(BlockRegister block) {
        this.setBlock(block.getNew());
    }

    public void setBlock(AbstractBlock block) {
        this.block = block;
        this.update();
    }
}
