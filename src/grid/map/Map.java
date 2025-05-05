package grid.map;

import grid.GameCanvas;
import grid.Tile;
import grid.blocks.BlockRegister;

import java.util.Arrays;

public class Map {

    public static final int HEIGHT_CHUNKS = 3;
    public static final int WIDTH_CHUNKS = 4;

    private final Tile[][] tiles;

    public Map(Chunk[][] chunks, GameCanvas gameCanvas) {

        this.tiles = new Tile[HEIGHT_CHUNKS * Chunk.HEIGHT][WIDTH_CHUNKS * Chunk.WIDTH];

        System.out.println(Arrays.deepToString(chunks));
        for (int chunkY = 0; chunkY < chunks.length; chunkY++) {
            for (int chunkX = 0; chunkX < chunks[chunkY].length; chunkX++) {
                Chunk currentChunk = chunks[chunkY][chunkX];
                BlockRegister[][] blocks = currentChunk.getBlocks();
                for (int blockY = 0; blockY < Chunk.HEIGHT; blockY++) {
                    for (int blockX = 0; blockX < Chunk.WIDTH; blockX++) {
                        int tileY = chunkY * Chunk.HEIGHT + blockY;
                        int tileX = chunkX * Chunk.WIDTH + blockX;
                        BlockRegister currentBlock = blocks[blockY][blockX];
                        this.tiles[tileY][tileX] = new Tile(tileX, tileY, gameCanvas, currentBlock == null ? null : currentBlock.getNew());
                    }
                }
            }
        }
    }

    public Tile getTileAt(int x, int y) {
        if (x >= 0 && x < this.tiles[0].length && y >= 0 && y < this.tiles.length) {
            return this.tiles[y][x];
        }
        return null; // Or a default tile
    }

}
