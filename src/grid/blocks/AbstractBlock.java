package grid.blocks;

import events.EntityEnterBlockEvent;
import events.PlayerEnterBlockEvent;
import fri.shapesge.ImageData;
import resources.ImageManager;

import java.util.Optional;
import java.util.Random;

public abstract class AbstractBlock {

    private static final Random RANDOM = new Random();

    private final ImageData[] images;
    private int activeImage;

    public AbstractBlock(String... textures) {
        this(RANDOM.nextInt(textures.length), textures);
    }

    public AbstractBlock(int activeImage, String... textures) {
        if (activeImage < 0 || activeImage >= textures.length) {
            throw new IllegalArgumentException("Invalid active image: " + activeImage);
        }

        this.images = new ImageData[textures.length];
        this.activeImage = activeImage;

        for (int i = 0; i < textures.length; i++) {
            this.images[i] = ImageManager.getImage("blocks/" + textures[i]);
        }
    }

    public final ImageData getTexture() {
        return this.images[this.activeImage];
    }

    public final void setRandomTexture() {
        this.activeImage = RANDOM.nextInt(this.images.length);
    }

    public final void setTexture(int index) {
        if (index < 0 || index >= this.images.length) {
            throw new IllegalArgumentException("Invalid index: " + index);
        }
        this.activeImage = index;
    }

    public abstract boolean isSeeThrough();

    public abstract Optional<BlockRegister> afterBlockExplosionEvent();

    /**
     * Happens before actually moving, therefore being able to cancel the movement.
     * @return whether the entity can move into this block
     */
    public abstract boolean onEntityEnterBlock(EntityEnterBlockEvent e);

    /**
     * Fires after moving to given block. Do nothing as default implementation.
     */
    public void afterEntityEnterBlockEvent(EntityEnterBlockEvent e) {
    }

    /**
     * Happens before actually moving, therefore being able to cancel the movement.
     * @return whether the player can move into this block
     */
    public abstract boolean onPlayerEnterBlock(PlayerEnterBlockEvent e);

    /**
     * Fires after moving to given block. Do nothing as default implementation.
     */
    public void afterPlayerEnterBlockEvent(PlayerEnterBlockEvent e) {
    }

}
