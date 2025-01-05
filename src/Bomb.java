import fri.shapesge.Image;
import fri.shapesge.ImageData;

import java.util.HashMap;

/**
 * Trieda bomb, predstavuje bombu, ktorá po pár sekundách po položení vybuchne a premení/zničí okolité blocky.
 */
public class Bomb {

    private static final int TIME_TO_EXPLODE = 2000;
    private static final ImageData BOMB_IMAGE = new ImageData("images/misc/bomb.png");
    private static final ImageData EXPLOSION_IMAGE = new ImageData("images/misc/explosion.png");

    private final HashMap<Tile, Image> mappedExplosion = new HashMap<>(9);
    private final Image image = new Image(BOMB_IMAGE);
    private final Tile tile;

    private long start;
    private boolean exploded = false;

    /**
     * Inicializuje novú bombu na danom tile a spustí časovač.
     * @param tile tile, na ktorom sa bomba zobrazí
     */
    public Bomb(Tile tile) {
        this.tile = tile;
        this.start = System.currentTimeMillis();
        this.image.changePosition(tile.getBoardX() * Tile.TILE_SIZE, tile.getBoardY() * Tile.TILE_SIZE);
        this.image.makeVisible();

        GameCanvas canvas = tile.getGameCanvas();
        for (int i = tile.getBoardX() - 1; i <= tile.getBoardX() + 1; i++) {
            for (int j = tile.getBoardY() - 1; j <= tile.getBoardY() + 1; j++) {
                Tile tempTile = canvas.getTileAtBoard(i, j);
                if (tempTile != null) {
                    Image tempImage = new Image(EXPLOSION_IMAGE);
                    tempImage.changePosition(tempTile.getBoardX() * Tile.TILE_SIZE, tempTile.getBoardY() * Tile.TILE_SIZE);
                    this.mappedExplosion.put(tempTile, tempImage);
                }
            }
        }
    }

    /**
     * Vráti tile, na ktorom sa bomba nachádza.
     */
    public Tile getTile() {
        return this.tile;
    }

    /**
     * ShapesGE listener ticku. Odpočítava čas do výbuchu a následne zničí ničitelné kocky a zabije entity / hráčov.
     */
    public void tick() {
        if (this.start + TIME_TO_EXPLODE > System.currentTimeMillis()) {
            return;
        }
        if (this.exploded) {
            this.unexplodeTiles();
        } else {
            this.exploded = true;
            this.start = System.currentTimeMillis();
            this.explodeTiles();
        }
    }

    private void explodeTiles() {
        if (!this.tile.getGameCanvas().isPlaying()) {
            return;
        }
        this.image.makeInvisible();
        for (Tile tempTile : this.mappedExplosion.keySet()) {
            if (tempTile.getBlock().isDestroyable() || tempTile.getBlock() == BlockType.GRASS) {
                this.mappedExplosion.get(tempTile).makeVisible();
                tempTile.kill();
            }
        }
    }

    private void unexplodeTiles() {
        for (Tile tempTile : this.mappedExplosion.keySet()) {
            this.mappedExplosion.get(tempTile).makeInvisible();
            if (tempTile.getBlock().isDestroyable()) {
                tempTile.setBlock(BlockType.GRASS);
            }
        }
        this.tile.getGameCanvas().removeBomb(this);
    }

}
