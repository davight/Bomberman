package grid;

import entity.EntityType;
import grid.blocks.BlockRegister;
import grid.map.Map;
import grid.map.RandomMap;

import java.util.Random;

public class Main {

    private static final Random RANDOM = new Random();

    private static final BlockType[][] BOARD1 = new BlockType[][] {
            {BlockType.BARRIER, BlockType.BARRIER, BlockType.BARRIER, BlockType.BARRIER, BlockType.BARRIER, BlockType.BARRIER, BlockType.BARRIER, BlockType.BARRIER, BlockType.BARRIER, BlockType.BARRIER, BlockType.BARRIER, BlockType.BARRIER, BlockType.BARRIER, BlockType.BARRIER, BlockType.BARRIER, BlockType.BARRIER, BlockType.BARRIER, BlockType.BARRIER, BlockType.BARRIER},
            {BlockType.BARRIER, BlockType.GRASS, BlockType.BRICKS, BlockType.GRASS, BlockType.GRASS, BlockType.GRASS, BlockType.GRASS, BlockType.GRASS, BlockType.GRASS, BlockType.GRASS, BlockType.BRICKS, BlockType.GRASS, BlockType.GRASS, BlockType.GRASS, BlockType.BRICKS, BlockType.GRASS, BlockType.BRICKS, BlockType.GRASS, BlockType.BARRIER},
            {BlockType.BARRIER, BlockType.GRASS, BlockType.ROCK, BlockType.GRASS, BlockType.ROCK, BlockType.GRASS, BlockType.ROCK, BlockType.GRASS, BlockType.ROCK, BlockType.GRASS, BlockType.ROCK, BlockType.GRASS, BlockType.ROCK, BlockType.GRASS, BlockType.ROCK, BlockType.GRASS, BlockType.ROCK, BlockType.GRASS, BlockType.BARRIER},
            {BlockType.BARRIER, BlockType.GRASS, BlockType.GRASS, BlockType.BRICKS, BlockType.BRICKS, BlockType.GRASS, BlockType.BRICKS, BlockType.BRICKS, BlockType.GRASS, BlockType.GRASS, BlockType.GRASS, BlockType.GRASS, BlockType.GRASS, BlockType.GRASS, BlockType.BRICKS, BlockType.GRASS, BlockType.GRASS, BlockType.GRASS, BlockType.BARRIER},
            {BlockType.BARRIER, BlockType.GRASS, BlockType.ROCK, BlockType.GRASS, BlockType.ROCK, BlockType.GRASS, BlockType.ROCK, BlockType.GRASS, BlockType.ROCK, BlockType.GRASS, BlockType.ROCK, BlockType.GRASS, BlockType.ROCK, BlockType.GRASS, BlockType.ROCK, BlockType.GRASS, BlockType.ROCK, BlockType.GRASS, BlockType.BARRIER},
            {BlockType.BARRIER, BlockType.GRASS, BlockType.GRASS, BlockType.GRASS, BlockType.GRASS, BlockType.BRICKS, BlockType.GRASS, BlockType.GRASS, BlockType.BRICKS, BlockType.GRASS, BlockType.GRASS, BlockType.GRASS, BlockType.BRICKS, BlockType.GRASS, BlockType.GRASS, BlockType.GRASS, BlockType.GRASS, BlockType.GRASS, BlockType.BARRIER},
            {BlockType.BARRIER, BlockType.GRASS, BlockType.ROCK, BlockType.GRASS, BlockType.ROCK, BlockType.GRASS, BlockType.ROCK, BlockType.GRASS, BlockType.ROCK, BlockType.GRASS, BlockType.ROCK, BlockType.GRASS, BlockType.ROCK, BlockType.GRASS, BlockType.ROCK, BlockType.GRASS, BlockType.ROCK, BlockType.GRASS, BlockType.BARRIER},
            {BlockType.BARRIER, BlockType.BRICKS, BlockType.BRICKS, BlockType.GRASS, BlockType.GRASS, BlockType.GRASS, BlockType.GRASS, BlockType.GRASS, BlockType.BRICKS, BlockType.GRASS, BlockType.GRASS, BlockType.GRASS, BlockType.GRASS, BlockType.GRASS, BlockType.GRASS, BlockType.BRICKS, BlockType.GRASS, BlockType.BRICKS, BlockType.BARRIER},
            {BlockType.BARRIER, BlockType.GRASS, BlockType.ROCK, BlockType.GRASS, BlockType.ROCK, BlockType.GRASS, BlockType.ROCK, BlockType.GRASS, BlockType.ROCK, BlockType.GRASS, BlockType.ROCK, BlockType.BRICKS, BlockType.ROCK, BlockType.GRASS, BlockType.ROCK, BlockType.BRICKS, BlockType.ROCK, BlockType.GRASS, BlockType.BARRIER},
            {BlockType.BARRIER, BlockType.GRASS, BlockType.BRICKS, BlockType.GRASS, BlockType.GRASS, BlockType.GRASS, BlockType.BRICKS, BlockType.GRASS, BlockType.GRASS, BlockType.GRASS, BlockType.GRASS, BlockType.BRICKS, BlockType.GRASS, BlockType.GRASS, BlockType.GRASS, BlockType.GRASS, BlockType.GRASS, BlockType.GRASS, BlockType.BARRIER},
            {BlockType.BARRIER, BlockType.BARRIER, BlockType.BARRIER, BlockType.BARRIER, BlockType.BARRIER, BlockType.BARRIER, BlockType.BARRIER, BlockType.BARRIER, BlockType.BARRIER, BlockType.BARRIER, BlockType.BARRIER, BlockType.BARRIER, BlockType.BARRIER, BlockType.BARRIER, BlockType.BARRIER, BlockType.BARRIER, BlockType.BARRIER, BlockType.BARRIER, BlockType.BARRIER},
    };

    private static final BlockType[][] BOARD2 = new BlockType[][] {
            {BlockType.BARRIER, BlockType.BARRIER, BlockType.BARRIER, BlockType.BARRIER, BlockType.BARRIER, BlockType.BARRIER, BlockType.BARRIER, BlockType.BARRIER, BlockType.BARRIER, BlockType.BARRIER, BlockType.BARRIER, BlockType.BARRIER, BlockType.BARRIER, BlockType.BARRIER, BlockType.BARRIER, BlockType.BARRIER, BlockType.BARRIER, BlockType.BARRIER, BlockType.BARRIER},
            {BlockType.BARRIER, BlockType.GRASS, BlockType.BRICKS, BlockType.GRASS, BlockType.GRASS, BlockType.GRASS, BlockType.GRASS, BlockType.GRASS, BlockType.GRASS, BlockType.GRASS, BlockType.BRICKS, BlockType.GRASS, BlockType.GRASS, BlockType.GRASS, BlockType.BRICKS, BlockType.GRASS, BlockType.BRICKS, BlockType.GRASS, BlockType.BARRIER},
            {BlockType.BARRIER, BlockType.GRASS, BlockType.GRASS, BlockType.GRASS, BlockType.ROCK, BlockType.ROCK, BlockType.ROCK, BlockType.ROCK, BlockType.ROCK, BlockType.GRASS, BlockType.ROCK, BlockType.ROCK, BlockType.ROCK, BlockType.ROCK, BlockType.ROCK, BlockType.GRASS, BlockType.GRASS, BlockType.GRASS, BlockType.BARRIER},
            {BlockType.BARRIER, BlockType.GRASS, BlockType.GRASS, BlockType.BRICKS, BlockType.BRICKS, BlockType.GRASS, BlockType.BRICKS, BlockType.BRICKS, BlockType.GRASS, BlockType.GRASS, BlockType.GRASS, BlockType.GRASS, BlockType.GRASS, BlockType.GRASS, BlockType.BRICKS, BlockType.GRASS, BlockType.GRASS, BlockType.GRASS, BlockType.BARRIER},
            {BlockType.BARRIER, BlockType.GRASS, BlockType.ROCK, BlockType.GRASS, BlockType.ROCK, BlockType.GRASS, BlockType.ROCK, BlockType.GRASS, BlockType.ROCK, BlockType.GRASS, BlockType.ROCK, BlockType.GRASS, BlockType.ROCK, BlockType.GRASS, BlockType.ROCK, BlockType.GRASS, BlockType.ROCK, BlockType.GRASS, BlockType.BARRIER},
            {BlockType.BARRIER, BlockType.GRASS, BlockType.GRASS, BlockType.GRASS, BlockType.GRASS, BlockType.BRICKS, BlockType.GRASS, BlockType.GRASS, BlockType.BRICKS, BlockType.GRASS, BlockType.GRASS, BlockType.GRASS, BlockType.BRICKS, BlockType.GRASS, BlockType.GRASS, BlockType.GRASS, BlockType.GRASS, BlockType.GRASS, BlockType.BARRIER},
            {BlockType.BARRIER, BlockType.GRASS, BlockType.ROCK, BlockType.GRASS, BlockType.ROCK, BlockType.GRASS, BlockType.ROCK, BlockType.GRASS, BlockType.ROCK, BlockType.GRASS, BlockType.ROCK, BlockType.GRASS, BlockType.ROCK, BlockType.GRASS, BlockType.ROCK, BlockType.GRASS, BlockType.ROCK, BlockType.GRASS, BlockType.BARRIER},
            {BlockType.BARRIER, BlockType.BRICKS, BlockType.BRICKS, BlockType.GRASS, BlockType.GRASS, BlockType.GRASS, BlockType.GRASS, BlockType.GRASS, BlockType.BRICKS, BlockType.GRASS, BlockType.GRASS, BlockType.GRASS, BlockType.GRASS, BlockType.GRASS, BlockType.GRASS, BlockType.BRICKS, BlockType.GRASS, BlockType.BRICKS, BlockType.BARRIER},
            {BlockType.BARRIER, BlockType.GRASS, BlockType.ROCK, BlockType.GRASS, BlockType.ROCK, BlockType.GRASS, BlockType.ROCK, BlockType.GRASS, BlockType.ROCK, BlockType.GRASS, BlockType.ROCK, BlockType.BRICKS, BlockType.ROCK, BlockType.GRASS, BlockType.ROCK, BlockType.BRICKS, BlockType.ROCK, BlockType.GRASS, BlockType.BARRIER},
            {BlockType.BARRIER, BlockType.GRASS, BlockType.BRICKS, BlockType.GRASS, BlockType.GRASS, BlockType.GRASS, BlockType.BRICKS, BlockType.GRASS, BlockType.GRASS, BlockType.GRASS, BlockType.GRASS, BlockType.BRICKS, BlockType.GRASS, BlockType.GRASS, BlockType.GRASS, BlockType.GRASS, BlockType.GRASS, BlockType.GRASS, BlockType.BARRIER},
            {BlockType.BARRIER, BlockType.BARRIER, BlockType.BARRIER, BlockType.BARRIER, BlockType.BARRIER, BlockType.BARRIER, BlockType.BARRIER, BlockType.BARRIER, BlockType.BARRIER, BlockType.BARRIER, BlockType.BARRIER, BlockType.BARRIER, BlockType.BARRIER, BlockType.BARRIER, BlockType.BARRIER, BlockType.BARRIER, BlockType.BARRIER, BlockType.BARRIER, BlockType.BARRIER},
    };

    public static void main(String[] args) {
        //startGame();
        //GameCanvas gameCanvas = new GameCanvas(new BlockRegister[][]{{BlockRegister.GRASS, BlockRegister.GRASS, BlockRegister.GRASS, BlockRegister.GRASS}, {null, null, BlockRegister.STONE}, {}, {BlockRegister.BRICKS}});
        RandomMap randomMap = new RandomMap();
        randomMap.printMap();
        Map map = new Map(randomMap.getMapChunks(), null);
    }

    public static void startGame() {
        GameCanvas gameCanvas;
        switch (RANDOM.nextInt(2)) {
            case 0:
                gameCanvas = new GameCanvas(BOARD1);
                gameCanvas.spawnPlayer(gameCanvas.getTileAtBoard(1, 1));
                //gameCanvas.spawnEntity(EntityType.EXPLOSIVE, gameCanvas.getTileAtBoard(9, 9));
                //gameCanvas.spawnEntity(EntityType.CLASSIC, gameCanvas.getTileAtBoard(5, 3));
                //gameCanvas.spawnEntity(EntityType.SPEEDY, gameCanvas.getTileAtBoard(4, 4));
                break;
            case 1:
                gameCanvas = new GameCanvas(BOARD2);
                gameCanvas.spawnPlayer(gameCanvas.getTileAtBoard(1, 1));
                //gameCanvas.spawnEntity(EntityType.EXPLOSIVE, gameCanvas.getTileAtBoard(9, 9));
                //gameCanvas.spawnEntity(EntityType.CLASSIC, gameCanvas.getTileAtBoard(5, 3));
                //gameCanvas.spawnEntity(EntityType.EXPLOSIVE, gameCanvas.getTileAtBoard(4, 4));
                break;
        }

    }

}
