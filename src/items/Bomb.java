package items;

import fri.shapesge.Image;
import fri.shapesge.ImageData;
import game.Game;
import grid.blocks.AbstractBlock;
import grid.blocks.Explodable;
import grid.map.Map;
import grid.map.Tile;
import resources.ImageManager;
import util.Waiter;

import java.util.ArrayList;

public class Bomb {

    private static final ImageData EXPLOSION = ImageManager.getImage("misc/explosion");
    private static final ImageData BOMB = ImageManager.getImage("misc/bomb");

    private final Image image;
    private final ArrayList<Tile> tilesAround = new ArrayList<>();
    private final ArrayList<Image> images = new ArrayList<>();

    public Bomb(Tile at) {
        this.image = new Image(BOMB);
        this.image.changePosition(at.getX(), at.getY());
        this.image.makeVisible();
        int x = at.getBoardX();
        int y = at.getBoardY();
        Map map = Game.getInstance().getMap();
        for (int x1 = -1; x1 <= 1; x1++) {
            for (int y1 = -1; y1 <= 1; y1++) {
                Tile temp = map.getTileAtBoard(x1 + x, y1 + y);
                if (temp != null) {
                    this.tilesAround.add(temp);
                }
            }
        }

        Waiter toExplode = new Waiter(3000, this::explode);
        Waiter toCleanup = new Waiter(2000, this::cleanup);
        toExplode.andThen(toCleanup);
        toExplode.waitAndRun();
    }

    private void explode(Waiter waiter) {
        this.image.makeInvisible();
        for (Tile t : this.tilesAround) {
            Image fire = new Image(EXPLOSION);
            fire.changePosition(t.getX(), t.getY());
            fire.makeVisible();
            this.images.add(fire);
            t.killAll();
        }
    }

    private void cleanup(Waiter waiter) {
        for (Image i : this.images) {
            i.makeInvisible();
        }
        for (Tile t : this.tilesAround) {
            AbstractBlock block  = t.getBlock();
            if (block instanceof Explodable explodable) {
                t.setBlock(explodable.afterExplosion());
            }
        }
    }

}
