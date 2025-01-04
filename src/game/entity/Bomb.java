package game.entity;

import fri.shapesge.Image;
import fri.shapesge.ImageData;
import fri.shapesge.Manager;
import game.canvas.BlockType;
import game.canvas.GameCanvas;
import game.canvas.Tile;

import java.util.HashMap;

public class Bomb {

    private static final Manager BOMB_MANAGER = new Manager();
    private static final int TIME_TO_EXPLODE = 2;
    private static final ImageData BOMB_IMAGE = new ImageData("game/canvas/grass_bomb.png");
    private static final ImageData EXPLOSION_IMAGE = new ImageData("game/canvas/explosion.png");

    private final HashMap<Tile, Image> mappedExplosion = new HashMap<>(9);
    private final Image image = new Image(BOMB_IMAGE);
    private final Tile tile;

    private long start;
    private boolean exploded = false;

    public Bomb(Tile tile) {
        BOMB_MANAGER.manageObject(this);
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

    public Tile getTile() {
        return this.tile;
    }

    public void tick() {
        if (this.start + (TIME_TO_EXPLODE * 1000) > System.currentTimeMillis()) {
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
            if (tempTile.getBlock() == BlockType.BRICKS || tempTile.getBlock() == BlockType.GRASS) {
                this.mappedExplosion.get(tempTile).makeVisible();
                tempTile.kill();
            }
        }
    }

    private void unexplodeTiles() {
        for (Tile tempTile : this.mappedExplosion.keySet()) {
            this.mappedExplosion.get(tempTile).makeInvisible();
            if (tempTile.getBlock() == BlockType.BRICKS) {
                tempTile.setBlock(BlockType.GRASS);
            }
        }
        BOMB_MANAGER.stopManagingObject(this);
        this.tile.getGameCanvas().removeBomb(this);
    }

}
