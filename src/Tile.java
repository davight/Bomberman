import fri.shapesge.Image;

/**
 * Trieda tile, ktorá reprezentuje pomyselný priestor na hernom plátne. Na tomto priestore sa vykreslujú blocky.
 */
public class Tile {

    /**
     * Veľkosť tilu a teda zároveň aj odporúčaná veľkosť blocku, ktorý sa má na ňom vykresliť
     */
    public static final int TILE_SIZE = 50;

    private final Image image;
    private final int boardX;
    private final int boardY;
    private final GameCanvas gameCanvas;

    private BlockType blockType;

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

    /**
     * Preverí a vráti hodnotu či je možný vstup entity na daný tile.
     */
    public boolean entityEnterTile() {
        if (!this.blockType.isPassable()) {
            return false;
        }
        for (Entity e : this.gameCanvas.getEntities()) {
            if (e.getTile() == this) {
                return false;
            }
        }
        for (Bomb b : this.gameCanvas.getBombs()) {
            if (b.getTile() == this) {
                return false;
            }
        }
        if (this.gameCanvas.getPlayer() != null && this.gameCanvas.getPlayer().getTile() == this) {
            this.gameCanvas.getPlayer().takeDamage(1);
        }
        return true;
    }

    /**
     * Preverí a vráti hodnotu či je možný vstup hráča na daný tile.
     */
    public boolean playerEnterTile() {
        if (this.blockType.isPassable() && this.gameCanvas.getPlayer() != null) {
            for (Entity e : this.gameCanvas.getEntities()) {
                if (e.getTile() == this) {
                    this.gameCanvas.getPlayer().takeDamage(1);
                    return true;
                }
            }
        }
        return this.blockType.isPassable();
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
        for (Entity e : this.gameCanvas.getEntities()) {
            if (e.getTile() == this) {
                this.gameCanvas.killEntity(e);
            }
        }
    }

    /**
     * Vráti block, ktorý sa aktuálne vykresľuje na tomto tile
     */
    public BlockType getBlock() {
        return this.blockType;
    }

    /**
     * Nastaví block, ktorý sa má vykresľovať na tomto tile.
     */
    public void setBlock(BlockType block) {
        this.blockType = block;
        this.image.changeImage(block.getImageData());
    }

}
