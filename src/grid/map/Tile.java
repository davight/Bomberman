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
import items.ItemRegister;
import util.ImageManager;

import java.util.ArrayList;

/**
 * Trieda tile, ktora reprezentuje pomyselny priestor na hernom platne. Na tomto priestore sa vykresluju blocky.
 */
public class Tile {

    /**
     * Velkost tilu a teda zaroven aj odporucana velkost kocky, ktora sa ma na nom vykreslit
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
        EventManager.registerHandler(PlayerEnterTileEvent.class, (e) -> e.newTile().afterPlayerEnterTile(e));
        EventManager.registerHandler(EnemyEnterTileEvent.class, (e) -> e.newTile().afterEntityEnterTile(e));

        EventManager.registerHandler(PlayerDeathEvent.class, (e) -> e.player().getTile().players.remove(e.player()));
        EventManager.registerHandler(EnemyDeathEvent.class, (e) -> e.enemy().getTile().enemies.remove(e.enemy()));
    }

    /**
     * Inicializuje novy tile na danej pozicii.
     * @param boardX x-ova suradnica mapy
     * @param boardY y-ova suradnica mapy
     */
    public Tile(int boardX, int boardY) {
        this.boardX = boardX;
        this.boardY = boardY;
        this.image = new Image(EMPTY_TILE);
        this.image.changePosition(Tile.TILE_SIZE * boardX, Tile.TILE_SIZE * boardY);
        this.image.makeVisible();
    }

    /**
     * Preveri ci nepriatel moze vojst na tento tile.
     * @param entity nepriatel, ktory chce vojst
     * @param oldTile tile, na ktorom sa nachadza teraz
     * @return Ci moze nepriatel vojst.
     */
    public boolean canEnemyEnterTile(AbstractEnemy entity, Tile oldTile) {
        if (this.block == null) {
            return false;
        }
        if (!this.enemies.isEmpty()) {
            return false;
        }
        return this.block.canEnemyEnterBlock(new EnemyEnterTileEvent(entity, this, oldTile));
    }

    /**
     * Event po vkroceni nepriatela na tile
     * @param event dany event objekt
     */
    public void afterEntityEnterTile(EnemyEnterTileEvent event) {
        if (event.oldTile() != null) {
            event.oldTile().enemies.remove(event.entity());
        }
        this.enemies.add(event.entity());
        new ArrayList<>(this.players).forEach((p) -> event.entity().attack(p));
        EventManager.fireEvent(new EnemyStepOnBlockEvent(this.block, event.entity()));
    }

    /**
     * Preveri ci hrac moze vojst na tento tile.
     * @param player hrac, ktory chce vojst
     * @param oldTile tile, na ktorom sa nachadza teraz
     * @return Ci moze hrac vojst.
     */
    public boolean canPlayerEnterTile(AbstractPlayer player, Tile oldTile) {
        if (this.block == null) {
            return false;
        }
        return this.block.canPlayerEnterBlock(new PlayerEnterTileEvent(player, this, oldTile));
    }

    /**
     * Event po vkroceni hraca na tile
     * @param event dany event objekt
     */
    public void afterPlayerEnterTile(PlayerEnterTileEvent event) {
        if (event.oldTile() != null) {
            event.oldTile().players.remove(event.player());
        }
        this.players.add(event.player());
        new ArrayList<>(this.enemies).forEach((e) -> e.attack(event.player()));
        ArrayList<AbstractItem> toRemove = new ArrayList<>(); // aby som needitoval a zaroven neprechadzal cez list
        for (AbstractItem i : this.items) {
            if (i.canPickup(event.player())) {
                i.remove();
                toRemove.add(i);
            }
        }
        this.items.removeAll(toRemove);
        EventManager.fireEvent(new PlayerStepOnBlockEvent(event.player(), this.block));
    }

    /**
     * Spawne novy item na tomto tile
     * @param item item na spawnutie
     */
    public void spawnItem(ItemRegister item) {
        AbstractItem i = item.getNew();
        i.setTile(this);
        this.items.add(i);
    }

    /**
     * @return Realnu x-ovu poziciu laveho horneho rohu
     */
    public int getX() {
        return this.boardX * TILE_SIZE;
    }

    /**
     * @return Realnu y-ovu poziciu laveho horneho rohu
     */
    public int getY() {
        return this.boardY * TILE_SIZE;
    }

    /**
     * @return Mapovu x-ovu suradnicu z na ktorej sa tile nachadza
     */
    public int getBoardX() {
        return this.boardX;
    }

    /**
     * @return Mapovu y-ovu suradnicu z na ktorej sa tile nachadza
     */
    public int getBoardY() {
        return this.boardY;
    }

    /**
     * Zabije vsetkych hracov a nepriatelov, ktori sa na tomto tile nachadzaju.
     */
    public void killAll() {
        new ArrayList<>(this.players).forEach(AbstractPlayer::kill);
        new ArrayList<>(this.enemies).forEach(AbstractEnemy::kill);
    }

    /**
     * Odstrani nepriatela zo zoznamu nepriatelov, ktori sa nachadzaju na tomto tile.
     * @param e nepriatel na odstranenie
     */
    public void removeEnemy(AbstractEnemy e) {
        this.enemies.remove(e);
    }

    /**
     * @return Kocku, ktora sa aktualne vykresluje na tomto tile.
     */
    public AbstractBlock getBlock() {
        return this.block;
    }

    /**
     * Aktualizuje texturu kocky, ktora sa aktualne vykresluje na tomto tile.
     */
    public void update() {
        this.image.changeImage(this.block.getTexture());
    }

    /**
     * Nastavi novu kocku, ktora sa ma vykreslovat na tomto tile.
     * @param block typ novej kocky
     */
    public void setBlock(BlockRegister block) {
        this.setBlock(block.getNew());
    }

    /**
     * Nastavi novu kocku, ktora sa ma vykreslovat na tomto tile.
     * @param block nova kocka
     */
    public void setBlock(AbstractBlock block) {
        this.block = block;
        this.update();
    }
}
