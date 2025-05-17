package items;

import entity.enemy.AbstractEnemy;
import entity.player.AbstractPlayer;
import fri.shapesge.ImageData;
import game.Game;
import util.ImageManager;
import util.Util;

public class Orbit extends AbstractItem implements Usable {

    public Orbit() {
        super("orbit");
    }

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

    @Override
    public ImageData getInventoryImage() {
        return ImageManager.getImage("items/orbit_inventory");
    }

    @Override
    public boolean canPickup(AbstractPlayer player) {
        if (player.addToInventory(this)) {
            return true;
        }
        return false;
    }

}
