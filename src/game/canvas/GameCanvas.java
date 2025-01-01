package game.canvas;

import game.entity.Bomb;
import game.entity.Entity;
import game.entity.Player;

import java.util.ArrayList;

/**
 * @Author David Gregor
 */
public class GameCanvas {

    private final Tile[][] tiles;
    private final ArrayList<Entity> entities;
    private final ArrayList<Bomb> bombs;
    private Player player;

    /**
     * Inicializuje nové herné plátno.
     * @param blocks Block array s n*m prvkami
     */
    public GameCanvas(Block[][] blocks) {
        this.tiles = new Tile[blocks.length][blocks[0].length];
        this.entities = new ArrayList<>();
        this.bombs = new ArrayList<>();
        this.player = null;

        for (int y = 0; y < blocks.length; y++) {
            for (int x = 0; x < blocks[y].length; x++) {
                this.tiles[y][x] = new Tile(x, y, blocks[y][x], this);
            }
        }
    }

    public Tile getTileAtBoard(int x, int y) {
        if (y < 0 || y >= this.tiles.length || x < 0 || x >= this.tiles[y].length) {
            return null;
        }
        return this.tiles[y][x];
    }

    public Tile getTileAtCoords(int x, int y) {
        return this.getTileAtBoard(x / Tile.TILE_SIZE, y / Tile.TILE_SIZE);
    }

    ///////////////
    /// Entities
    /////////////

    public boolean spawnEntity(EntityType type, Tile at) {
        if (at.entityEnterTile()) {
            Entity entity = new Entity(type, at);
            this.entities.add(entity);
            return true;
        }
        return false;
    }

    public void killEntity(Entity entity) {
        this.entities.remove(entity);
    }

    public Entity[] getEntities() {
        return this.entities.toArray(new Entity[0]);
    }

    /////////////
    /// Player
    ///////////

    public boolean spawnPlayer(Tile at) {
        if (at.playerEnterTile()) {
            this.player = new Player(at);
            return true;
        }
        return false;
    }

    public void killPlayer() {
        this.player.die();
        this.player = null;
    }

    public Player getPlayer() {
        return this.player;
    }

    //////////
    /// Bomb
    ////////

    public boolean spawnBomb(Tile at) {
        if (at.bombEnterTile()) {
            Bomb bomb = new Bomb(at);
            this.bombs.add(bomb);
            return true;
        }
        return false;
    }

    public void removeBomb(Bomb bomb) {
        this.bombs.remove(bomb);
    }

    public Bomb[] getBombs() {
        return this.bombs.toArray(new Bomb[0]);
    }

}
