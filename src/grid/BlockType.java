package grid;

import fri.shapesge.ImageData;
import java.util.Random;

/**
 * Enum blockov a ich vlastností. Jedná sa o blocky, ktoré sa môžu na plátne vykresliť.
 */
public enum BlockType {

    GRASS(true, false, "grass", "grass2"),
    ROCK(false, false, "rock3"),
    BARRIER(false, false, "border"),
    BRICKS(false, true, "barrel", "barrel2");

    private static final Random RANDOM = new Random();
    private static final String PATH = "images/blocks/";

    private final boolean isPassable;
    private final boolean isDestroyable;
    private final ImageData[] imageData;

    BlockType(boolean isPassable, boolean isDestroyable,  String... texture) {
        this.isDestroyable = isDestroyable;
        this.isPassable = isPassable;
        this.imageData = new ImageData[texture.length];
        for (int i = 0; i < texture.length; i++) {
            this.imageData[i] = new ImageData(PATH + texture[i] + ".png");
        }
    }

    /**
     * Vráti textúru blocku. Pokiaľ je registrovaných viacero textúr vyberie sa jedna náhodná.
     */
    public ImageData getImageData() {
        return this.imageData[RANDOM.nextInt(this.imageData.length)];
    }

    /**
     * Vráti hodnotu či je možné cez block prejsť.
     */
    public boolean isPassable() {
        return this.isPassable;
    }

    /**
     * Vráti hodnotu či je možné block zničiť. (Pomocou bomby)
     */
    public boolean isDestroyable() {
        return this.isDestroyable;
    }

}
