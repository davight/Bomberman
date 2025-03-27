package grid;

import entity.enemy.AbstractEnemy;
import entity.Bomb;
import entity.Player;
import events.EntityEnterBlockEvent;
import events.PlayerEnterBlockEvent;
import fri.shapesge.Image;
import fri.shapesge.ImageData;
import grid.blocks.AbstractBlock;

/**
 * Trieda tile, ktorá reprezentuje pomyselný priestor na hernom plátne. Na tomto priestore sa vykreslujú blocky.
 */
public class Tile {

    /**
     * Veľkosť tilu a teda zároveň aj odporúčaná veľkosť blocku, ktorý sa má na ňom vykresliť
     */
    public static final int TILE_SIZE = 50;
    private static final ImageData EMPTY_TILE = new ImageData("images/blocks/empty.png");

    private final Image image;
    private final int boardX;
    private final int boardY;
    private final GameCanvas gameCanvas;

    private BlockType blockType;
    private AbstractBlock block;

    public Tile(int boardX, int boardY, GameCanvas gameCanvas) {
        this(boardX, boardY, gameCanvas, EMPTY_TILE);
        this.block = null;
    }

    public Tile(int boardX, int boardY, GameCanvas gameCanvas, AbstractBlock block) {
        this(boardX, boardY, gameCanvas, block == null ? EMPTY_TILE : block.getTexture());
        if (block != null) {
            this.block = block;
        }
    }

    private Tile(int boardX, int boardY, GameCanvas gameCanvas, ImageData image) {
        this.boardX = boardX;
        this.boardY = boardY;
        this.gameCanvas = gameCanvas;
        this.image = new Image(image);
        this.image.changePosition(Tile.TILE_SIZE * boardX, Tile.TILE_SIZE * boardY);
        this.image.makeVisible();
    }

    /**
     * Inicializuje nový tile s danými parametrami.
     *
     * @param boardX X-ová súradnica z pola na hernom plátne
     * @param boardY Y-ová súradnica z pola na hernom plátne
     * @param block Block, ktorý sa počiatočne vykreslí v tomto priestore
     * @param gameCanvas plátno na ktorom sa tile nachádza
     */
    public Tile(int boardX, int boardY, BlockType block, GameCanvas gameCanvas) {
        this.gameCanvas = gameCanvas;
        this.boardX = boardX;
        this.boardY = boardY;
        this.blockType = block;
        this.image = new Image(this.blockType.getImageData());
        this.image.changePosition(Tile.TILE_SIZE * boardX, Tile.TILE_SIZE * boardY);
        this.image.makeVisible();
    }

    public boolean onEntityEnterTile(AbstractEnemy entity, Tile oldTile) {
        if (!this.block.onEntityEnterBlock(new EntityEnterBlockEvent(entity, this, oldTile))) {
            return false;
        }
        for (AbstractEnemy e : this.gameCanvas.getEntities()) {
            if (e.getTile() == this) {
                return false;
            }
        }
        for (Bomb b : this.gameCanvas.getBombs()) {
            if (b.getTile() == this) {
                return false;
            }
        }
        return true;
    }

    public void afterEntityEnterTile(AbstractEnemy entity) {
        this.block.afterEntityEnterBlockEvent(new EntityEnterBlockEvent(entity, this, this));
        if (this.gameCanvas.getPlayer() != null && this.gameCanvas.getPlayer().getTile() == this) {
            this.gameCanvas.getPlayer().takeDamage(1);
        }
    }

    public boolean onPlayerEnterTile(Tile oldTile) {
        return this.block.onPlayerEnterBlock(new PlayerEnterBlockEvent(this.gameCanvas.getPlayer(), this, oldTile));
    }

    public void afterPlayerEnterTile() {
        this.block.afterPlayerEnterBlockEvent(new PlayerEnterBlockEvent(this.gameCanvas.getPlayer(), this, this));
        if (this.gameCanvas.getPlayer() != null) {
            for (AbstractEnemy e : this.gameCanvas.getEntities()) {
                if (e.getTile() == this) {
                    this.gameCanvas.getPlayer().takeDamage(1);
                }
            }
        }
    }

    /**
     * Preverí a vráti hodnotu či je možné položenie bomby na daný tile.
     */
    public boolean bombEnterTile() {
        return this.blockType.isPassable() && this.getBlock() == BlockType.GRASS;
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
    public GameCanvas getGameCanvas() {
        return this.gameCanvas;
    }

    /**
     * Zabije entitu alebo hráča, ktorí sa nachádzajú na tomto tile.
     */
    public void kill() {
        Player player = this.gameCanvas.getPlayer();
        if (player != null && player.getTile() == this) {
            this.gameCanvas.killPlayer();
        }
        for (AbstractEnemy e : this.gameCanvas.getEntities()) {
            if (e.getTile() == this) {
                this.gameCanvas.killEntity(e);
            }
        }
    }

    public AbstractBlock getBlockT() {
        return this.block;
    }

    public void setBlock(AbstractBlock block) {
        this.block = block;
    }

    /**
     * Vráti block, ktorý sa aktuálne vykresľuje na tomto tile
     */
    public BlockType getBlock() {
        return this.blockType;
    }

}
