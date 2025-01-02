package game.utils;

import game.canvas.Block;
import game.canvas.EntityType;
import game.canvas.GameCanvas;

public class Main {

    public static void main(String[] args) {

        Block[][] board2 = new Block[][] {
                {Block.STONE, Block.STONE, Block.STONE, Block.STONE, Block.STONE, Block.STONE, Block.STONE, Block.STONE, Block.STONE, Block.STONE, Block.STONE, Block.STONE, Block.STONE, Block.STONE, Block.STONE, Block.STONE, Block.STONE, Block.STONE, Block.STONE},
                {Block.STONE, Block.GRASS, Block.BRICKS, Block.GRASS, Block.GRASS, Block.GRASS, Block.GRASS, Block.GRASS, Block.GRASS, Block.GRASS, Block.BRICKS, Block.GRASS, Block.GRASS, Block.GRASS, Block.BRICKS, Block.GRASS, Block.BRICKS, Block.GRASS, Block.STONE},
                {Block.STONE, Block.GRASS, Block.STONE, Block.GRASS, Block.STONE, Block.GRASS, Block.STONE, Block.GRASS, Block.STONE, Block.GRASS, Block.STONE, Block.GRASS, Block.STONE, Block.GRASS, Block.STONE, Block.GRASS, Block.STONE, Block.GRASS, Block.STONE},
                {Block.STONE, Block.GRASS, Block.GRASS, Block.BRICKS, Block.BRICKS, Block.GRASS, Block.BRICKS, Block.BRICKS, Block.GRASS, Block.GRASS, Block.GRASS, Block.GRASS, Block.GRASS, Block.GRASS, Block.BRICKS, Block.GRASS, Block.GRASS, Block.GRASS, Block.STONE},
                {Block.STONE, Block.GRASS, Block.STONE, Block.GRASS, Block.STONE, Block.GRASS, Block.STONE, Block.GRASS, Block.STONE, Block.GRASS, Block.STONE, Block.GRASS, Block.STONE, Block.GRASS, Block.STONE, Block.GRASS, Block.STONE, Block.GRASS, Block.STONE},
                {Block.STONE, Block.GRASS, Block.GRASS, Block.GRASS, Block.GRASS, Block.BRICKS, Block.GRASS, Block.GRASS, Block.BRICKS, Block.GRASS, Block.GRASS, Block.GRASS, Block.BRICKS, Block.GRASS, Block.GRASS, Block.GRASS, Block.GRASS, Block.GRASS, Block.STONE},
                {Block.STONE, Block.GRASS, Block.STONE, Block.GRASS, Block.STONE, Block.GRASS, Block.STONE, Block.GRASS, Block.STONE, Block.GRASS, Block.STONE, Block.GRASS, Block.STONE, Block.GRASS, Block.STONE, Block.GRASS, Block.STONE, Block.GRASS, Block.STONE},
                {Block.STONE, Block.BRICKS, Block.BRICKS, Block.GRASS, Block.GRASS, Block.GRASS, Block.GRASS, Block.GRASS, Block.BRICKS, Block.GRASS, Block.GRASS, Block.GRASS, Block.GRASS, Block.GRASS, Block.GRASS, Block.BRICKS, Block.GRASS, Block.BRICKS, Block.STONE},
                {Block.STONE, Block.GRASS, Block.STONE, Block.GRASS, Block.STONE, Block.GRASS, Block.STONE, Block.GRASS, Block.STONE, Block.GRASS, Block.STONE, Block.BRICKS, Block.STONE, Block.GRASS, Block.STONE, Block.BRICKS, Block.STONE, Block.GRASS, Block.STONE},
                {Block.STONE, Block.GRASS, Block.BRICKS, Block.GRASS, Block.GRASS, Block.GRASS, Block.BRICKS, Block.GRASS, Block.GRASS, Block.GRASS, Block.GRASS, Block.BRICKS, Block.GRASS, Block.GRASS, Block.GRASS, Block.GRASS, Block.GRASS, Block.GRASS, Block.STONE},
                {Block.STONE, Block.STONE, Block.STONE, Block.STONE, Block.STONE, Block.STONE, Block.STONE, Block.STONE, Block.STONE, Block.STONE, Block.STONE, Block.STONE, Block.STONE, Block.STONE, Block.STONE, Block.STONE, Block.STONE, Block.STONE, Block.STONE},
        };

        GameCanvas gameCanvas = new GameCanvas(board2);
        System.out.println(gameCanvas.getTileAtBoard(0, 0));
        System.out.println(gameCanvas.getTileAtBoard(2, 0));
        gameCanvas.spawnPlayer(gameCanvas.getTileAtBoard(1, 1));
        gameCanvas.spawnEntity(EntityType.EXPLOSIVE, gameCanvas.getTileAtBoard(9, 9));
        //gameCanvas.spawnPlayerAt(gameCanvas.getTileAt(2, 2));
        //gameCanvas.spawnEntityAt(gameCanvas.getTileAt(5, 2));
        //MANAZER.spravujObjekt(player);

    }

}
