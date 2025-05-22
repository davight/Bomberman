package items;

import events.EnemyEnterTileEvent;
import events.EventManager;
import events.PlayerEnterTileEvent;
import fri.shapesge.Image;
import fri.shapesge.ImageData;
import game.Game;
import grid.blocks.AbstractBlock;
import grid.blocks.Explodable;
import grid.map.Map;
import grid.map.Tile;
import util.ImageManager;
import util.Waiter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.function.Consumer;

/**
 *
 */
public class Bomb { // This special items does not extend AbstractItem

    private static final int TIME_TO_EXPLODE = 3000;
    private static final int TIME_TO_CLEANUP = 2000;
    private static final ImageData EXPLOSION = ImageManager.getImage("misc/explosion");
    private static final ImageData BOMB = ImageManager.getImage("misc/bomb");

    private final HashSet<Tile> tilesAround = new HashSet<>();
    private final ArrayList<Image> images = new ArrayList<>();
    private final Image image;
    private final int levelId;

    private boolean isActive = false;

    public Bomb(Tile at, int levelId) {
        this.levelId = levelId;
        this.image = new Image(BOMB);
        this.image.changePosition(at.getX(), at.getY());
        this.image.makeVisible();
        int x = at.getBoardX();
        int y = at.getBoardY();
        for (int x1 = -1; x1 <= 1; x1++) {
            for (int y1 = -1; y1 <= 1; y1++) {
                Tile temp = Map.getTileAtBoard(x1 + x, y1 + y);
                if (temp != null) {
                    this.tilesAround.add(temp);
                }
            }
        }

        Consumer<Tile> killer = (tile) -> {
            if (this.isActive && this.tilesAround.contains(tile)) {
                tile.killAll();
            }
        };
        EventManager.registerHandler(PlayerEnterTileEvent.class, (event) -> killer.accept(event.newTile()));
        EventManager.registerHandler(EnemyEnterTileEvent.class, (event) -> killer.accept(event.newTile()));

        Waiter toExplode = new Waiter(TIME_TO_EXPLODE, this::explode);
        Waiter toCleanup = new Waiter(TIME_TO_CLEANUP, this::cleanup);
        toExplode.andThen(toCleanup);
        toExplode.waitAndRun();
    }

    private void explode(Waiter waiter) {
        this.image.makeInvisible();
        if (!Game.isPlayingLevel(this.levelId)) {
            return;
        }
        this.isActive = true;
        for (Tile t : this.tilesAround) {
            Image fire = new Image(EXPLOSION);
            fire.changePosition(t.getX(), t.getY());
            fire.makeVisible();
            this.images.add(fire);
            t.killAll();
        }
    }

    private void cleanup(Waiter waiter) {
        this.isActive = false;
        this.images.forEach(Image::makeInvisible);
        for (Tile t : this.tilesAround) {
            AbstractBlock block  = t.getBlock();
            if (block instanceof Explodable explodable) {
                explodable.onExplosion(t);
                explodable.newBlock().ifPresentOrElse((b) -> t.setBlock(b.getNew()), t::update);
            }
        }
    }

}
