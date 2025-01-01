package game.canvas;

import fri.shapesge.ImageData;

public enum PlayerMovement {

    UP(0, -1, "game/canvas/player.png", "game/canvas/player_moving.png"),
    DOWN(0, 1, "game/canvas/player.png", "game/canvas/player_moving.png"),
    LEFT(-1, 0, "game/canvas/player.png", "game/canvas/player_moving.png"),
    RIGHT(1, 0, "game/canvas/player.png", "game/canvas/player_moving.png");

    private final int x;
    private final int y;
    private final ImageData staying;
    private final ImageData moving;

    PlayerMovement(int x, int y, String pathStaying, String pathMoving) {
        this.x = x;
        this.y = y;
        this.staying = new ImageData(pathStaying);
        this.moving = new ImageData(pathMoving);
    }

    public ImageData getMoving() {
        return this.moving;
    }

    public ImageData getStaying() {
        return this.staying;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

}
