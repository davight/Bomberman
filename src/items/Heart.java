package items;

import entity.player.AbstractPlayer;

public class Heart extends AbstractItem {

    protected Heart() {
        super("heart");
    }

    @Override
    public boolean onPickup(AbstractPlayer player) {
        player.hurt(-1);
        return true;
    }
}
