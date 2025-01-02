package game.canvas;

import fri.shapesge.ImageData;

public enum EntityType {

    CLASSIC(3000, 2, "game/images/entity/classic/staying.png", "game/images/entity/classic/up_moving.png", "game/images/entity/classic/down_moving.png", "game/images/entity/classic/left_moving.png", "game/images/entity/classic/right_moving.png"),
    SPEEDY(1, 1, "game/images/entity/speedy/staying.png", "game/images/entity/speedy/up_moving.png", "game/images/entity/speedy/down_moving.png", "game/images/entity/speedy/left_moving.png", "game/images/entity/speedy/right_moving.png"),
    EXPLOSIVE(4000, 3, "game/images/entity/explosive/staying.png", "game/images/entity/explosive/up_moving.png", "game/images/entity/explosive/down_moving.png", "game/images/entity/explosive/left_moving.png", "game/images/entity/explosive/right_moving.png");

    private final int randomMovement;
    private final int walkingRange;
    private final ImageData stayingImage;
    private final ImageData upMovingImage;
    private final ImageData downMovingImage;
    private final ImageData leftMovingImage;
    private final ImageData rightMovingImage;

    EntityType(int randomMovement, int walkingRange, String stayingTexture, String upMovingTexture, String downMovingTexture, String leftMovingTexture, String rightMovingTexture) {
        this.stayingImage = new ImageData(stayingTexture);
        this.upMovingImage = new ImageData(upMovingTexture);
        this.downMovingImage = new ImageData(downMovingTexture);
        this.leftMovingImage = new ImageData(leftMovingTexture);
        this.rightMovingImage = new ImageData(rightMovingTexture);
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
        return this.stayingImage;
    }

    public ImageData getUpMovingImage() {
        return this.upMovingImage;
    }

    public ImageData getDownMovingImage() {
        return this.downMovingImage;
    }

    public ImageData getLeftMovingImage() {
        return this.leftMovingImage;
    }

    public ImageData getRightMovingImage() {
        return this.rightMovingImage;
    }

    public ImageData getStayingImage() {
        return this.stayingImage;
    }

}
