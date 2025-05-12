package grid.blocks;

import events.EntityEnterBlockEvent;
import events.PlayerEnterBlockEvent;
import fri.shapesge.ImageData;
import resources.ImageManager;
import util.WeightedRandomness;

import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;

public abstract class AbstractBlock {

    private final WeightedRandomness<ImageData> randomAccessTextures = new WeightedRandomness<>();
    private final ArrayList<ImageData> linearTextures = new ArrayList<>();
    private ImageData activeImage = null;

    public AbstractBlock(Map<String, Integer> randomBlocks) {
        for (Map.Entry<String, Integer> e : randomBlocks.entrySet()) {
            ImageData image = ImageManager.getImage("blocks/" + e.getKey());
            this.randomAccessTextures.add(image, e.getValue());
            this.linearTextures.add(image);
        }
        this.setRandomTexture();
    }

    public AbstractBlock(String... textures) {
        for (String s : textures) {
            ImageData image = ImageManager.getImage("blocks/" + s);
            this.randomAccessTextures.add(image);
            this.linearTextures.add(image);
        }
        this.setRandomTexture();
    }

    public AbstractBlock(int activeImage, String... textures) {
        for (String s : textures) {
            ImageData image = ImageManager.getImage("blocks/" + s);
            this.randomAccessTextures.add(image);
            this.linearTextures.add(image);
        }
        this.setTexture(activeImage);
    }

    public ImageData getTexture() {
        return this.activeImage;
    }

    public void setRandomTexture() {
        this.activeImage = this.randomAccessTextures.getRandom();
    }

    public void setTexture(int index) {
        this.activeImage = this.linearTextures.get(index);
    }

    public abstract boolean isSpawnable();

    public abstract boolean isSeeThrough();

    // TODO part of interface
    public abstract Optional<BlockRegister> afterBlockExplosionEvent();

    /**
     * Happens before actually moving, therefore being able to cancel the movement.
     * @return whether the entity can move into this block
     */
    public abstract boolean onEntityEnterBlock(EntityEnterBlockEvent e);

    /**
     * Happens before actually moving, therefore being able to cancel the movement.
     * @return whether the player can move into this block
     */
    public abstract boolean onPlayerEnterBlock(PlayerEnterBlockEvent e);

}
