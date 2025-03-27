package entity.movement;

import fri.shapesge.Image;
import grid.Tile;

public interface Movable {

    Image getImage();

    int getTimeBetweenSteps();

    Direction.Pack[] getValidDirections();

    default void afterMovementEvent(Tile tile) {
    }


}
