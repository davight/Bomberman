package items;

import entity.player.AbstractPlayer;
import fri.shapesge.ImageData;

public interface Usable {

    boolean onUse(AbstractPlayer player);

    ImageData getInventoryImage();

}
