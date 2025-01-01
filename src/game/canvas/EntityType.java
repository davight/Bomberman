package game.canvas;

import fri.shapesge.ImageData;

public enum EntityType {

    RED(4000, 99, ""),
    BLUE(3000, 5, ""),
    YELLOW(2000, 3, "");

    private final int randomMovement;
    private final int walkingRange;
    private final ImageData imageData;

    EntityType(int randomMovement, int walkingRange, String texture) {
        this.imageData = new ImageData(texture);
        this.randomMovement = randomMovement;
        this.walkingRange = walkingRange;
    }

    public int getRandomMovement() {
        return this.randomMovement;
    }

    public int getWalkingRange() {
        return this.walkingRange;
    }

    public ImageData getImageData() {
        return this.imageData;
    }

}
