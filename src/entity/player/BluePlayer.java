package entity.player;

import entity.movement.Direction;
import entity.movement.MovementManager;
import fri.shapesge.Image;
import fri.shapesge.ImageData;
import grid.map.Tile;
import items.Usable;
import util.ImageManager;
import util.ShapesGeListener;

import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;

public class BluePlayer extends AbstractPlayer {

    private static final ImageData HEALTH_POINT = ImageManager.getImage("player/blue_heart");
    private final ArrayList<Image> healthPoints = new ArrayList<>(AbstractPlayer.START_HEALTH);
    private final ArrayList<Usable> inventory = new ArrayList<>(AbstractPlayer.INVENTORY_SIZE);

    public BluePlayer(Tile tile) {
        super(tile, false);

        int startX = 10;
        for (int i = 0; i < AbstractPlayer.START_HEALTH; i++) {
            Image hp = new Image(HEALTH_POINT);
            hp.changePosition(startX, 10);
            hp.makeVisible();
            this.healthPoints.add(hp);
            startX += HEALTH_POINT.getWidth() + 10;
        }
    }

    @ShapesGeListener
    public void moveBlueDown() {
        super.move(Direction.DOWN);
    }

    @ShapesGeListener
    public void moveBlueUp() {
        super.move(Direction.UP);
    }

    @ShapesGeListener
    public void moveBlueLeft() {
        super.move(Direction.LEFT);
    }

    @ShapesGeListener
    public void moveBlueRight() {
        super.move(Direction.RIGHT);
    }

    @ShapesGeListener
    public void activateBlue() {
        super.pressEnter();
    }

    @Override
    public Map<Direction, MovementManager.Pack> getValidDirections() {
        return Map.of(
                Direction.UP, new MovementManager.Pack("player/blue_up_staying", "player/blue_up_moving"),
                Direction.DOWN, new MovementManager.Pack("player/blue_down_staying", "player/blue_down_moving"),
                Direction.LEFT, new MovementManager.Pack("player/blue_left_staying", "player/blue_left_moving"),
                Direction.RIGHT, new MovementManager.Pack("player/blue_right_staying", "player/blue_right_moving")
        );
    }

    @Override
    public void updateHealthPoints(int currentHealth) {
        for (int i = 0; i < this.healthPoints.size(); i++) {
            if (currentHealth >= i + 1) {
                this.healthPoints.get(i).makeVisible();
            } else {
                this.healthPoints.get(i).makeInvisible();
            }
        }
    }

    @Override
    public boolean addToInventory(Usable item) {
        return false;
    }

    @Override
    public boolean takeFromInventory(Usable item) {
        return false;
    }

    @Override
    public Optional<Usable> activeItem() {
        return Optional.empty();
    }

}
