package items;

import entity.player.AbstractPlayer;
import fri.shapesge.ImageData;

/**
 * Interface na oznacenie tried itemov, ktore sa mozu ukladat do inventara na neskorsie pouzitie.
 */
public interface Usable {

    /**
     * Preveri ci hrac moze pouzit tento item.
     * @param player hrac, ktory sa ho snazi pouzit
     * @return Ci hrac moze pouzit tento item
     */
    boolean onUse(AbstractPlayer player);

    /**
     * @return Vrati texturu, ktora sa pouzije na zobrazenie itemu v inventari.
     */
    ImageData getInventoryImage();

}
