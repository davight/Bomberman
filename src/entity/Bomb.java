package entity;

import fri.shapesge.Image;
import grid.map.Tile;
import resources.ImageManager;

import java.util.HashMap;

/**
 * Trieda bomb, predstavuje bombu, ktorá po pár sekundách po položení vybuchne a premení/zničí okolité blocky.
 */
public class Bomb {

    private static final int TIME_TO_EXPLODE = 2000;

    private final HashMap<Tile, Image> mappedExplosion = new HashMap<>(9);
    private final Image image = new Image(ImageManager.getImage("images/misc/bomb.png"));
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

        //GameCanvas canvas = null;
        for (int i = tile.getBoardX() - 1; i <= tile.getBoardX() + 1; i++) {
            for (int j = tile.getBoardY() - 1; j <= tile.getBoardY() + 1; j++) {
                //Tile tempTile = canvas.getTileAtBoard(i, j);
                Tile tempTile = null;
                if (tempTile != null) {
                    Image tempImage = new Image(ImageManager.getImage("images/misc/explosion.png"));
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
//        if (!this.tile.getGameCanvas().isPlaying()) {
//            return;
//        }
        this.image.makeInvisible();
        for (Tile tempTile : this.mappedExplosion.keySet()) {
            if (tempTile.getBlock().afterBlockExplosionEvent().isPresent()) {
                this.mappedExplosion.get(tempTile).makeVisible();
                tempTile.killAll();
            }
        }
    }

    private void unexplodeTiles() {
        for (Tile tempTile : this.mappedExplosion.keySet()) {
            this.mappedExplosion.get(tempTile).makeInvisible();
            tempTile.getBlock().afterBlockExplosionEvent().ifPresent(blockRegister -> tempTile.setBlock(blockRegister.getNew()));
        }
        //this.tile.getGameCanvas().removeBomb(this);
    }

}
