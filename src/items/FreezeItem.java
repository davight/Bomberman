package items;

import entity.enemy.AbstractEnemy;
import entity.player.AbstractPlayer;
import fri.shapesge.ImageData;
import game.Game;
import util.ImageManager;
import util.Util;

public class FreezeItem extends AbstractItem implements Usable {

    private static final int MINIMUM_TIME = 3000;
    private static final int MAXIMUM_TIME = 5000;

    protected FreezeItem() {
        super("freeze_tile");
    }

    /**
     * Preveri ci hrac moze zobrat tento item.
     * @param player hrac, ktory sa ho pokusa zobrat
     * @return Ci ho moze zobrat.
     */
    @Override
    public boolean canPickup(AbstractPlayer player) {
        return player.addToInventory(this);
    }

    /**
     * Preveri ci hrac moze pouzit tento item.
     * @param player hrac, ktory sa ho snazi pouzit
     * @return Ci hrac moze pouzit tento item
     */
    @Override
    public boolean onUse(AbstractPlayer player) {
        int durationMs = Util.randomInt(MINIMUM_TIME, MAXIMUM_TIME);
        for (AbstractEnemy e : Game.getEnemies()) {
            e.freeze(durationMs);
        }
        return true;
    }

    /**
     * @return Vrati texturu, ktora sa pouzije na zobrazenie itemu v inventari.
     */
    @Override
    public ImageData getInventoryImage() {
        return ImageManager.getImage("items/freeze_inventory");
    }
}
