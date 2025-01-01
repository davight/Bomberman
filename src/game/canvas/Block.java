package game.canvas;

import fri.shapesge.ImageData;

public enum Block {

    GRASS(true, false, "grass"),
    GRASS_EXPLOSION(true, false, "grass_explosion"),
    STONE(false, false, "rock"),

    BRICKS(false, true, "bricks"),
    BRICKS_EXPLOSION(false, false, "bricks_explosion")
    //CRACKING_BRICKS(false, false, "cracking_bricks");
    ;
    private final boolean isPassable;
    private final boolean isDestroyable;
    private final ImageData imageData;

    Block(boolean isPassable, boolean isDestroyable, String texture) {
        this.isPassable = isPassable;
        this.isDestroyable = isDestroyable;
        this.imageData = new ImageData("game/canvas/" + texture + ".png");
    }

    public ImageData getImageData() {
        return this.imageData;
    }

    public boolean isPassable() {
        return this.isPassable;
    }

    public boolean isDestroyable() {
        return this.isDestroyable;
    }

}
