package items;

import entity.enemy.AbstractEnemy;
import entity.player.AbstractPlayer;
import fri.shapesge.ImageData;
import game.Game;
import resources.ImageManager;
import util.Util;

public class Freeze extends AbstractItem implements Usable {

    protected Freeze() {
        super("freeze_tile");
    }

    @Override
    public boolean onPickup(AbstractPlayer player) {
        if (player.addToInventory(this)) {
            super.remove();
            return true;
        }
        return false;
    }

    @Override
    public boolean onUse(AbstractPlayer player) {
        int durationMs = Util.randomInt(2000, 4000); // 2 to 4 seconds
        for (AbstractEnemy e : Game.getInstance().getEnemies()) {
            e.freeze(durationMs);
        }
        return true;
    }

    @Override
    public ImageData getInventoryImage() {
        return ImageManager.getImage("items/freeze_inventory");
    }
}
