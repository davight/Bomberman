package entity.movement;

import fri.shapesge.Image;
import grid.map.Tile;

import java.util.Map;

public interface Movable {

    Image getImage();

    int getTimeBetweenSteps();

    Map<Direction, MovementManager.Pack> getValidDirections();

    void afterSuccessfulMovement(Tile tile);

}
