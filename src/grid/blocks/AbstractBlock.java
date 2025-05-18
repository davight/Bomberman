package grid.blocks;

import events.EnemyEnterTileEvent;
import events.PlayerEnterTileEvent;
import fri.shapesge.ImageData;
import util.ImageManager;
import util.WeightedRandomness;

import java.util.ArrayList;
import java.util.Map;

public abstract class AbstractBlock {

    private final WeightedRandomness<ImageData> randomAccessTextures = new WeightedRandomness<>();
    private final ArrayList<ImageData> linearTextures = new ArrayList<>();
    private ImageData activeImage = null;

    /**
     * Inicializuje novu kocku s danymi texturami a ich pravdepodobnostou zobrazenia sa na kocke pri nahodnom vybere.
     * @param randomTextures mapa s texturami a pravdepodobnostou
     */
    public AbstractBlock(Map<String, Integer> randomTextures) {
        for (Map.Entry<String, Integer> e : randomTextures.entrySet()) {
            ImageData image = ImageManager.getImage("blocks/" + e.getKey());
            this.randomAccessTextures.add(image, e.getValue());
            this.linearTextures.add(image);
        }
        this.setRandomTexture();
    }

    /**
     * Inicializuje novu kocku s danymi texturami a rovnomernou pravdepodobnostou ich zobrazenia sa na kocke pri nahodnom vybere.
     * @param textures zoznam textur
     */
    public AbstractBlock(String... textures) {
        for (String s : textures) {
            ImageData image = ImageManager.getImage("blocks/" + s);
            this.randomAccessTextures.add(image);
            this.linearTextures.add(image);
        }
        this.setRandomTexture();
    }

    /**
     * Inicializuje novu kocku s danymi texturami a rovnomernou pravdepodobnostou ich zobrazenia sa na kocke pri nahodnom vybere.
     * S prvotnou texturou kocky ako oznacenu.
     * @param activeImage index pociatocnej textury
     * @param textures zoznam textur
     */
    public AbstractBlock(int activeImage, String... textures) {
        this(textures);
        this.setTexture(activeImage);
    }

    /**
     * @return Aktivnu texturu kocky.
     */
    public ImageData getTexture() {
        return this.activeImage;
    }

    /**
     * Nastavi novu nahodnu texturu kocky.
     */
    public void setRandomTexture() {
        this.activeImage = this.randomAccessTextures.getRandom();
    }

    /**
     * Nastavit novu aktivnu texturu kocky podla indexu zo zoznamu.
     * @param index index textury
     */
    public void setTexture(int index) {
        this.activeImage = this.linearTextures.get(index);
    }

    /**
     * @return Ci je kocka bezpecna pre spawn hracov a nepriatelov.
     */
    public abstract boolean isSpawnable();

    /**
     * @return Ci je mozne cez kocku vidiet dalej.
     */
    public abstract boolean isSeeThrough();

    /**
     * @return Ci je mozne aby nepriatel vosiel na tuto kocku.
     */
    public abstract boolean canEnemyEnterBlock(EnemyEnterTileEvent e);

    /**
     * @return Ci je mozne aby hrac vosiel na tuto kocku.
     */
    public abstract boolean canPlayerEnterBlock(PlayerEnterTileEvent e);

}
