package grid.map;

import grid.blocks.AbstractBlock;
import grid.blocks.BlockRegister;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Map {

    public static final int HEIGHT_CHUNKS = 3;
    public static final int WIDTH_CHUNKS = 4;
    private static final Random RAND = new Random();

    private final Tile[][] tiles;
    private final ArrayList<Tile> spawnableTiles = new ArrayList<>();

    public Map(Chunk[][] chunks) {

        this.tiles = new Tile[HEIGHT_CHUNKS * Chunk.HEIGHT][WIDTH_CHUNKS * Chunk.WIDTH];

        System.out.println(Arrays.deepToString(chunks));
        for (int chunkY = 0; chunkY < chunks.length; chunkY++) {
            for (int chunkX = 0; chunkX < chunks[chunkY].length; chunkX++) {
                BlockRegister[][] blocks = chunks[chunkY][chunkX].getBlocks();
                for (int blockY = 0; blockY < Chunk.HEIGHT; blockY++) {
                    for (int blockX = 0; blockX < Chunk.WIDTH; blockX++) {
                        int tileY = chunkY * Chunk.HEIGHT + blockY;
                        int tileX = chunkX * Chunk.WIDTH + blockX;
                        AbstractBlock block = blocks[blockY][blockX].getNew();
                        Tile tile = new Tile(tileX, tileY, block);
                        this.tiles[tileY][tileX] = tile;
                        if (block.isSpawnable()) {
                            this.spawnableTiles.add(tile);
                        }
                    }
                }
            }
        }
    }

    public Tile getTileAtBoard(int x, int y) {
        if (x >= 0 && x < this.tiles[0].length && y >= 0 && y < this.tiles.length) {
            return this.tiles[y][x];
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
