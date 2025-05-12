package grid.map;

import grid.blocks.BlockRegister;
import util.Util;
import util.WeightedRandomness;

import java.util.Arrays;
import java.util.UUID;

public class Chunk {

    public static final int WIDTH = 5;
    public static final int HEIGHT = 5;

    private static final int SIZE = WIDTH * HEIGHT;
    private static final WeightedRandomness<Chunk> CHUNKS = new WeightedRandomness<>();

    private final UUID uuid;
    private final BlockRegister[][] blocks;

    public static final Chunk EMPTY_CHUNK = new Chunk(fill(SIZE, BlockRegister.BARRIER));

    static {
        new Chunk(
                BlockRegister.GRASS, BlockRegister.GRASS, BlockRegister.GRASS, BlockRegister.GRASS, BlockRegister.GRASS,
                BlockRegister.GRASS, BlockRegister.GRASS, BlockRegister.GRASS, BlockRegister.GRASS, BlockRegister.GRASS,
                BlockRegister.GRASS, BlockRegister.GRASS, BlockRegister.GRASS, BlockRegister.GRASS, BlockRegister.GRASS,
                BlockRegister.GRASS, BlockRegister.GRASS, BlockRegister.GRASS, BlockRegister.GRASS, BlockRegister.GRASS,
                BlockRegister.GRASS, BlockRegister.GRASS, BlockRegister.GRASS, BlockRegister.GRASS, BlockRegister.GRASS);
        new Chunk(
                BlockRegister.STONE, BlockRegister.STONE, BlockRegister.STONE, BlockRegister.STONE, BlockRegister.STONE,
                BlockRegister.STONE, BlockRegister.STONE, BlockRegister.STONE, BlockRegister.STONE, BlockRegister.STONE,
                BlockRegister.STONE, BlockRegister.STONE, BlockRegister.STONE, BlockRegister.STONE, BlockRegister.STONE,
                BlockRegister.STONE, BlockRegister.STONE, BlockRegister.STONE, BlockRegister.STONE, BlockRegister.STONE,
                BlockRegister.STONE, BlockRegister.STONE, BlockRegister.STONE, BlockRegister.STONE, BlockRegister.STONE);
        new Chunk(1,
                BlockRegister.GRASS, BlockRegister.GRASS, BlockRegister.GRASS, BlockRegister.GRASS, BlockRegister.GRASS,
                BlockRegister.GRASS, BlockRegister.GRASS, BlockRegister.WATER, BlockRegister.WATER, BlockRegister.GRASS,
                BlockRegister.GRASS, BlockRegister.WATER, BlockRegister.GRASS, BlockRegister.STONE, BlockRegister.GRASS,
                BlockRegister.WATER, BlockRegister.GRASS, BlockRegister.WATER, BlockRegister.STONE, BlockRegister.GRASS,
                BlockRegister.GRASS, BlockRegister.GRASS, BlockRegister.GRASS, BlockRegister.GRASS, BlockRegister.GRASS);
        new Chunk(
                BlockRegister.GRASS, BlockRegister.GRASS, BlockRegister.GRASS, BlockRegister.GRASS, BlockRegister.GRASS,
                BlockRegister.GRASS, BlockRegister.STONE, BlockRegister.FIRE, BlockRegister.STONE, BlockRegister.GRASS,
                BlockRegister.GRASS, BlockRegister.GRASS, BlockRegister.STONE, BlockRegister.GRASS, BlockRegister.GRASS,
                BlockRegister.GRASS, BlockRegister.STONE, BlockRegister.FIRE, BlockRegister.STONE, BlockRegister.GRASS,
                BlockRegister.GRASS, BlockRegister.GRASS, BlockRegister.GRASS, BlockRegister.GRASS, BlockRegister.GRASS);
        new Chunk(1,
                BlockRegister.GRASS, BlockRegister.GRASS, BlockRegister.GRASS, BlockRegister.GRASS, BlockRegister.GRASS,
                BlockRegister.WATER, BlockRegister.GRASS, BlockRegister.GRASS, BlockRegister.STONE, BlockRegister.GRASS,
                BlockRegister.GRASS, BlockRegister.GRASS, BlockRegister.STONE, BlockRegister.GRASS, BlockRegister.GRASS,
                BlockRegister.GRASS, BlockRegister.GRASS, BlockRegister.WATER, BlockRegister.GRASS, BlockRegister.GRASS,
                BlockRegister.GRASS, BlockRegister.GRASS, BlockRegister.GRASS, BlockRegister.GRASS, BlockRegister.GRASS);
        new Chunk(1,
                BlockRegister.GRASS, BlockRegister.GRASS, BlockRegister.GRASS, BlockRegister.GRASS, BlockRegister.GRASS,
                BlockRegister.GRASS, BlockRegister.STONE, BlockRegister.BARREL, BlockRegister.STONE, BlockRegister.GRASS,
                BlockRegister.GRASS, BlockRegister.BARREL, BlockRegister.GRASS, BlockRegister.BARREL, BlockRegister.GRASS,
                BlockRegister.GRASS, BlockRegister.STONE, BlockRegister.BARREL, BlockRegister.STONE, BlockRegister.GRASS,
                BlockRegister.GRASS, BlockRegister.GRASS, BlockRegister.GRASS, BlockRegister.GRASS, BlockRegister.GRASS);
        new Chunk(1,
                BlockRegister.STONE, BlockRegister.BARREL, BlockRegister.GRASS, BlockRegister.BARREL, BlockRegister.STONE,
                BlockRegister.BARREL, BlockRegister.GRASS, BlockRegister.GRASS, BlockRegister.GRASS, BlockRegister.STONE,
                BlockRegister.GRASS, BlockRegister.GRASS, BlockRegister.GRASS, BlockRegister.GRASS, BlockRegister.STONE,
                BlockRegister.BARREL, BlockRegister.GRASS, BlockRegister.GRASS, BlockRegister.GRASS, BlockRegister.GRASS,
                BlockRegister.BARREL, BlockRegister.GRASS, BlockRegister.STONE, BlockRegister.GRASS, BlockRegister.BARREL);

    }

    private Chunk(BlockRegister... blocks) {
        if (blocks.length != SIZE) {
            throw new IllegalArgumentException("Chunk length must be " + SIZE + " but was " + blocks.length);
        }
        this.uuid = UUID.randomUUID();
        this.blocks = new BlockRegister[HEIGHT][WIDTH];
        int i = 0;
        for (int x = 0; x < this.blocks.length; x++) {
            for (int y = 0; y < this.blocks[x].length; y++) {
                this.blocks[x][y] = blocks[i];
                i++;
            }
        }
    }

    private Chunk(int weight, BlockRegister... blocks) {
        this(blocks);
        CHUNKS.add(this, weight);
    }

    public static Chunk getRandomChunk() {
        return CHUNKS.getRandom();
    }

    public BlockRegister[][] getBlocks() {
        int rotation = Util.randomInt(0, 4);
        if (rotation == 0 || rotation == 4) {
            return this.blocks;
        }
        BlockRegister[][] toRotate = this.blocks;
        for (int i = 0; i < rotation; i++) {
            toRotate = rotate(toRotate);
        }
        return toRotate;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Chunk other && this.uuid.equals(other.uuid);
    }

    private static BlockRegister[] fill(int n, BlockRegister block) {
        BlockRegister[] blocks = new BlockRegister[n];
        Arrays.fill(blocks, block);
        return blocks;
    }

    private static BlockRegister[][] rotate(BlockRegister[][] blocks) {
        BlockRegister[][] rotated = new BlockRegister[HEIGHT][WIDTH];
        for (int r = 0; r < HEIGHT; r++) {
            for (int c = 0; c < WIDTH; c++) {
                rotated[c][WIDTH - 1 - r] = blocks[r][c];
            }
        }
        return rotated;
    }

}
