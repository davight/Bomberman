package items;

import entity.player.AbstractPlayer;

public class Heart extends AbstractItem {

    protected Heart() {
        super("heart");
    }

    @Override
    public boolean canPickup(AbstractPlayer player) {
        player.heal(1);
        return true;
    }
}
