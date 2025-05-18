package items;

import entity.player.AbstractPlayer;
import events.EventManager;
import events.ItemDespawnEvent;
import events.ItemSpawnEvent;
import fri.shapesge.Image;
import grid.map.Tile;
import util.ImageManager;

/**
 * Abstraktna trieda na vytvaranie itemov.
 */
public abstract class AbstractItem {

    private final Image tileImage;

    private Tile tile = null;

    /**
     * Inicializuje novy item s danou texturou.
     * @param tileImagePath textura
     */
    public AbstractItem(String tileImagePath) {
        this.tileImage = new Image(ImageManager.getImage("items/" + tileImagePath));
    }

    /**
     * Teleportuje item na dany tile.
     * @param tile miesto teleportu
     */
    public void setTile(Tile tile) {
        if (this.tile == null) {
            EventManager.fireEvent(new ItemSpawnEvent(this, tile));
            this.tile = tile;
        }
        this.tileImage.changePosition(tile.getX(), tile.getY());
        this.tileImage.makeVisible();
    }

    /**
     * Zmaze item z existencie
     */
    public void remove() {
        EventManager.fireEvent(new ItemDespawnEvent(this));
        this.tileImage.makeInvisible();
    }

    /**
     * Preveri ci hrac moze zobrat tento item.
     * @param player hrac, ktory sa ho pokusa zobrat
     * @return Ci ho moze zobrat.
     */
    public abstract boolean canPickup(AbstractPlayer player);

}
