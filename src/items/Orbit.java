package items;

import entity.enemy.AbstractEnemy;
import entity.player.AbstractPlayer;
import fri.shapesge.ImageData;
import game.Game;
import resources.ImageManager;

public class Orbit extends AbstractItem implements Usable {

    public Orbit() {
        super("orbit");
    }

    @Override
    public boolean onUse(AbstractPlayer player) {
        if (player.getHealth() <= 1) {
            return false;
        }
        player.hurt(1);
        for (AbstractEnemy e : Game.getInstance().getEnemies()) {
            e.hurt(1);
        }
        return true;
    }

    @Override
    public ImageData getInventoryImage() {
        return ImageManager.getImage("items/orbit_inventory");
    }

    @Override
    public boolean onPickup(AbstractPlayer player) {
        if (player.addToInventory(this)) {
            return true;
        }
        return false;
    }

}
