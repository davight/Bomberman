package grid.map;

import grid.blocks.AbstractBlock;
import grid.blocks.BlockRegister;

import java.util.ArrayList;
import java.util.Random;

public class Map {

    public static final int HEIGHT_CHUNKS = 3;
    public static final int WIDTH_CHUNKS = 4;

    private static Tile[][] tiles = new Tile[HEIGHT_CHUNKS * Chunk.HEIGHT][WIDTH_CHUNKS * Chunk.WIDTH];
    static {
        for (int y = 0 ; y < HEIGHT_CHUNKS * Chunk.HEIGHT ; y++) {
            for (int x = 0 ; x < WIDTH_CHUNKS * Chunk.WIDTH ; x++) {
                tiles[y][x] = new Tile(x, y);
            }
        }
    }
    private static final Random RAND = new Random();

    private final ArrayList<Tile> spawnableTiles = new ArrayList<>();

    public Map(Chunk[][] chunks) {
        for (int chunkY = 0; chunkY < chunks.length; chunkY++) {
            for (int chunkX = 0; chunkX < chunks[chunkY].length; chunkX++) {
                BlockRegister[][] blocks = chunks[chunkY][chunkX].getBlocks();
                for (int blockY = 0; blockY < Chunk.HEIGHT; blockY++) {
                    for (int blockX = 0; blockX < Chunk.WIDTH; blockX++) {
                        int tileY = chunkY * Chunk.HEIGHT + blockY;
                        int tileX = chunkX * Chunk.WIDTH + blockX;
                        AbstractBlock block = blocks[blockY][blockX].getNew();
                        Tile tile = getTileAtBoard(tileX, tileY);
                        tile.setBlock(block);
                        if (block.isSpawnable()) {
                            this.spawnableTiles.add(tile);
                        }
                    }
                }
            }
        }
    }

    public static Tile getTileAtBoard(int x, int y) {
        if (x >= 0 && x < tiles[0].length && y >= 0 && y < tiles.length) {
            return tiles[y][x];
        }
        return null;
    }

    /**
     * Returns an array of all currently available spawnable tiles.
     * @return an array
     */
    public Tile[] getSpawnableTiles() {
        return this.spawnableTiles.toArray(new Tile[0]);
    }

    /**
     * Returns a randomly selected spawnable tile and removes it from list.
     * @return chosen tile
     */
    public Tile getRandomSpawnable() {
        return this.spawnableTiles.remove(RAND.nextInt(this.spawnableTiles.size()));
    }

}
