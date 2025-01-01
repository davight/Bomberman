package game.entity;

import fri.shapesge.Image;
import fri.shapesge.ImageData;
import fri.shapesge.Manager;
import game.canvas.Block;
import game.canvas.GameCanvas;
import game.canvas.Tile;

import java.util.ArrayList;


public class Bomb {

    private static final Manager BOMB_MANAGER = new Manager();
    private static final int TIME_TO_EXPLODE = 2;
    private static final ImageData BOMB_IMAGE = new ImageData("game/canvas/grass_bomb.png");

    private final ArrayList<Tile> tilesAround;
    private final Image image;
    private final Tile tile;

    private long start;
    private boolean exploded;

    public Bomb(Tile tile) {
        BOMB_MANAGER.manageObject(this);
        this.tile = tile;
        this.start = System.currentTimeMillis();
        this.exploded = false;
        this.tilesAround = new ArrayList<>(9);

        this.image = new Image(BOMB_IMAGE);
        this.image.changePosition(tile.getBoardX() * Tile.TILE_SIZE, tile.getBoardY() * Tile.TILE_SIZE);
        this.image.makeVisible();

        GameCanvas canvas = tile.getGameCanvas();
        for (int i = tile.getBoardX() - 1; i <= tile.getBoardX() + 1; i++) {
            for (int j = tile.getBoardY() - 1; j <= tile.getBoardY() + 1; j++) {
                if (canvas.getTileAtBoard(i, j) != null) {
                    this.tilesAround.add(canvas.getTileAtBoard(i, j));
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
        if (!this.exploded) {
            this.explodeTiles();
            this.exploded = true;
            this.start = System.currentTimeMillis();
        } else {
            this.unexplodeTiles();
        }
    }

    private void explodeTiles() {
        this.image.makeInvisible();
        for (Tile temp : this.tilesAround) {
            temp.kill();
            switch (temp.getBlock()) {
                case BRICKS:
                    temp.setBlock(Block.BRICKS_EXPLOSION);
                    break;
                case GRASS:
                    temp.setBlock(Block.GRASS_EXPLOSION);
                    break;
            }
        }
    }

    private void unexplodeTiles() {
        for (Tile temp : this.tilesAround) {
            switch (temp.getBlock()) {
                case GRASS_EXPLOSION:
                case BRICKS_EXPLOSION:
                    temp.setBlock(Block.GRASS);
                    break;
            }
        }
        BOMB_MANAGER.stopManagingObject(this);
        this.tile.getGameCanvas().removeBomb(this);
    }

}
