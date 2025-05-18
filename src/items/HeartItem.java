package items;

import entity.player.AbstractPlayer;

public class HeartItem extends AbstractItem {

    protected HeartItem() {
        super("heal_tile");
    }

    /**
     * Preveri ci hrac moze zobrat tento item.
     * @param player hrac, ktory sa ho pokusa zobrat
     * @return Ci ho moze zobrat.
     */
    @Override
    public boolean canPickup(AbstractPlayer player) {
        return player.heal(1);
    }
}
