package game.canvas;

import fri.shapesge.Image;
import game.entity.Bomb;
import game.entity.Entity;
import game.entity.Player;

public class Tile {

    public static final int TILE_SIZE = 50;

    private Block block;

    private final Image image;
    private final int boardX;
    private final int boardY;
    private final GameCanvas gameCanvas;

    public Tile(int boardX, int boardY, Block block, GameCanvas gameCanvas) {
        this.gameCanvas = gameCanvas;
        this.boardX = boardX;
        this.boardY = boardY;
        this.block = block;
        this.image = new Image(this.block.getImageData());
        this.image.changePosition(Tile.TILE_SIZE * boardX, Tile.TILE_SIZE * boardY);
        this.image.makeVisible();
    }

    public boolean entityEnterTile() {
        if (!this.block.isPassable()) {
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
        return true;
    }

    public boolean playerEnterTile() {
        return this.block.isPassable();
    }

    public boolean bombEnterTile() {
        return this.block.isPassable() && this.getBlock() == Block.GRASS;
    }

    public int getBoardX() {
        return this.boardX;
    }

    public int getBoardY() {
        return this.boardY;
    }

    public GameCanvas getGameCanvas() {
        return this.gameCanvas;
    }

    public void kill() {
        Player player = this.gameCanvas.getPlayer();
        if (player != null && player.getTile() == this) {
            player.die();
        }
        for (Entity e : this.gameCanvas.getEntities()) {
            if (e.getTile() == this) {
                e.die();
            }
        }
    }

    public Block getBlock() {
        return this.block;
    }

    public void setBlock(Block block) {
        this.block = block;
        this.image.changeImage(block.getImageData());
    }

}
