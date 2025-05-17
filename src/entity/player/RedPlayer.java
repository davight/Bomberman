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
import java.util.Collections;
import java.util.Map;
import java.util.Optional;

public class RedPlayer extends AbstractPlayer {

    private static final ImageData HEALTH_POINT = ImageManager.getImage("player/red_heart");

    private final ArrayList<Image> healthPoints = new ArrayList<>(AbstractPlayer.START_HEALTH);
    private final ArrayList<Usable> inventory = new ArrayList<>(AbstractPlayer.INVENTORY_SIZE);
    private final ArrayList<Image> hotbar = new ArrayList<>(AbstractPlayer.INVENTORY_SIZE);

    private int activeItem = -1;

    public RedPlayer(Tile tile) {
        super(tile, true);

        int startX = 1000;
        for (int i = 0; i < AbstractPlayer.START_HEALTH; i++) {
            startX -= HEALTH_POINT.getWidth() + 10;
            Image hp = new Image(HEALTH_POINT);
            hp.changePosition(startX, 10);
            hp.makeVisible();
            this.healthPoints.add(hp);
        }

        startX -= 20;
        for (int i = 0; i < AbstractPlayer.INVENTORY_SIZE; i++) {
            startX -= AbstractPlayer.EMPTY_SLOT.getWidth() + 10;
            Image slot = new Image(AbstractPlayer.EMPTY_SLOT);
            slot.changePosition(startX, 10);
            slot.makeVisible();
            this.hotbar.add(slot);
        }
        Collections.reverse(this.hotbar);
    }

    @ShapesGeListener
    public void moveRedDown() {
        super.move(Direction.DOWN);
    }

    @ShapesGeListener
    public void moveRedUp() {
        super.move(Direction.UP);
    }

    @ShapesGeListener
    public void moveRedLeft() {
        super.move(Direction.LEFT);
    }

    @ShapesGeListener
    public void moveRedRight() {
        super.move(Direction.RIGHT);
    }

    @ShapesGeListener
    public void activateRed() {
        super.pressEnter();
    }

    @Override
    public Map<Direction, MovementManager.Pack> getValidDirections() {
        return Map.of(
                Direction.UP, new MovementManager.Pack("player/up_staying", "player/up_moving"),
                Direction.DOWN, new MovementManager.Pack("player/down_staying", "player/down_moving"),
                Direction.LEFT, new MovementManager.Pack("player/left_staying", "player/left_moving"),
                Direction.RIGHT, new MovementManager.Pack("player/right_staying", "player/right_moving")
        );
    }

    @Override
    public void updateHealthPoints(int currentHealth) {
        for (int i = 0; i < this.healthPoints.size(); i++) {
            if (currentHealth >= i + 1) { // takto alebo naopak idk
                this.healthPoints.get(i).makeVisible();
            } else {
                this.healthPoints.get(i).makeInvisible();
            }
        }
    }

    @Override
    public boolean addToInventory(Usable item) {
        if (this.inventory.size() < INVENTORY_SIZE) {
            this.activeItem++;
            this.inventory.add(item);
            this.hotbar.get(this.activeItem).changeImage(item.getInventoryImage());
            return true;
        }
        return false;
    }

    @Override
    public boolean takeFromInventory(Usable item) {
        if (this.inventory.contains(item)) {
            this.inventory.remove(item);
            this.hotbar.get(this.activeItem).changeImage(AbstractPlayer.EMPTY_SLOT);
            this.activeItem--;
            return true;
        }
        return false;
    }

    @Override
    public Optional<Usable> activeItem() {
        return this.activeItem == -1 ? Optional.empty() : Optional.of(this.inventory.get(this.activeItem));
    }
}
