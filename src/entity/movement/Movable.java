package entity.movement;

import fri.shapesge.Image;
import grid.map.Tile;

import java.util.Map;

public interface Movable {

    Image getImage();

    int getTimeBetweenSteps();

    Map<Direction, MovementManager.Pack> getValidDirections();

    default void afterMovementEvent(Tile tile) {
    }

}
