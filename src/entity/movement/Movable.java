package entity.movement;

import fri.shapesge.Image;
import grid.map.Tile;

import java.util.EnumMap;

/**
 * Triedy implementujuce tento interface znacia, ze ich obrazok sa moze pohybovat po platne.
 * A teda ho mozu registrovat do MovementManagera
 */
public interface Movable {

    /**
     * @return Obrazok, ktory sa bude pohybovat.
     */
    Image getImage();

    /**
     * @return Cas v milisekundach medzi krokmi pri pohybe.
     */
    int getTimeBetweenSteps();

    /**
     * @return Zoznam stran s ich prislusnymi texturami, ktorymi sa objekt moze pohybovat.
     */
    EnumMap<Direction, MovementManager.Pack> getValidDirections();

    /**
     * Vykona sa po uspesnom presunuti obrazku na novy tile.
     * @param tile Tile, na ktory sa obrazok posunul.
     */
    void afterSuccessfulMovement(Tile tile);

}
