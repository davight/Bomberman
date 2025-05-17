package items;

import entity.player.AbstractPlayer;
import fri.shapesge.Image;
import grid.map.Tile;
import util.ImageManager;

public abstract class AbstractItem {

    private final Image tileImage;

    public AbstractItem(String tileImagePath) {
        this.tileImage = new Image(ImageManager.getImage("items/" + tileImagePath));
    }

    public void setTile(Tile tile) {
        this.tileImage.changePosition(tile.getX(), tile.getY());
        this.tileImage.makeVisible();
    }

    public void remove() {
        this.tileImage.makeInvisible();
    }

    public abstract boolean canPickup(AbstractPlayer player);

}
