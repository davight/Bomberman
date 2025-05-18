package items;

import entity.enemy.AbstractEnemy;
import entity.player.AbstractPlayer;
import fri.shapesge.ImageData;
import game.Game;
import util.ImageManager;
import util.Util;

public class OrbitItem extends AbstractItem implements Usable {

    public OrbitItem() {
        super("orbit_tile");
    }

    /**
     * Preveri ci hrac moze pouzit tento item.
     * @param player hrac, ktory sa ho snazi pouzit
     * @return Ci hrac moze pouzit tento item
     */
    @Override
    public boolean onUse(AbstractPlayer player) {
        if (player.getHealth() > 1) {
            player.hurt(1);
            AbstractEnemy randomEnemy = Util.randomElement(Game.getEnemies());
            randomEnemy.kill();
            return true;
        }
        return false;
    }

    /**
     * @return Vrati texturu, ktora sa pouzije na zobrazenie itemu v inventari.
     */
    @Override
    public ImageData getInventoryImage() {
        return ImageManager.getImage("items/orbit_inventory");
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

}
