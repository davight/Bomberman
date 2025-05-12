package grid;

import game.Game;
import items.Bomb;

public class Main {

    public static void main(String[] args) {
        Game.startDuoGame();

        Bomb bomb = new Bomb(Game.getInstance().getMap().getTileAtBoard(10, 10));
    }

}
