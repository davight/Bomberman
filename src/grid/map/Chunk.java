package grid.map;

import grid.blocks.BlockRegister;
import util.Util;
import util.WeightedRandomness;

import java.util.Arrays;
import java.util.UUID;

/**
 * Trieda Chunk reprezentuje 5x5 miesto kociek na mape.
 * Chunky su vytvorene manualne a s priradenou vahou pravdepodobnosti ich nahodneho vybratia.
 */
public class Chunk {

    /**
     * Pocet kociek na dlzku.
     */
    public static final int WIDTH = 5;

    /**
     * Pocet kociek na vysku.
     */
    public static final int HEIGHT = 5;

    private static final int SIZE = WIDTH * HEIGHT;
    private static final WeightedRandomness<Chunk> CHUNKS = new WeightedRandomness<>();
    public static final Chunk EMPTY_CHUNK = new Chunk(fill(SIZE, BlockRegister.BARRIER));

    private final UUID uuid;
    private final BlockRegister[][] blocks;

    static {
        new Chunk(2,
                BlockRegister.GRASS, BlockRegister.GRASS, BlockRegister.GRASS, BlockRegister.GRASS, BlockRegister.GRASS,
                BlockRegister.GRASS, BlockRegister.GRASS, BlockRegister.WATER, BlockRegister.WATER, BlockRegister.GRASS,
                BlockRegister.GRASS, BlockRegister.WATER, BlockRegister.GRASS, BlockRegister.STONE, BlockRegister.GRASS,
                BlockRegister.WATER, BlockRegister.GRASS, BlockRegister.WATER, BlockRegister.STONE, BlockRegister.GRASS,
                BlockRegister.GRASS, BlockRegister.GRASS, BlockRegister.GRASS, BlockRegister.GRASS, BlockRegister.GRASS);
        new Chunk(1,
                BlockRegister.GRASS, BlockRegister.GRASS, BlockRegister.GRASS, BlockRegister.GRASS, BlockRegister.GRASS,
                BlockRegister.GRASS, BlockRegister.STONE, BlockRegister.FIRE, BlockRegister.STONE, BlockRegister.GRASS,
                BlockRegister.GRASS, BlockRegister.GRASS, BlockRegister.STATUE, BlockRegister.GRASS, BlockRegister.GRASS,
                BlockRegister.GRASS, BlockRegister.STONE, BlockRegister.FIRE, BlockRegister.STONE, BlockRegister.GRASS,
                BlockRegister.GRASS, BlockRegister.GRASS, BlockRegister.GRASS, BlockRegister.GRASS, BlockRegister.GRASS);
        new Chunk(1,
                BlockRegister.GRASS, BlockRegister.GRASS, BlockRegister.GRASS, BlockRegister.GRASS, BlockRegister.GRASS,
                BlockRegister.GRASS, BlockRegister.STONE, BlockRegister.SPIKES, BlockRegister.STONE, BlockRegister.GRASS,
                BlockRegister.GRASS, BlockRegister.WATER, BlockRegister.STATUE, BlockRegister.BARREL, BlockRegister.GRASS,
                BlockRegister.GRASS, BlockRegister.STONE, BlockRegister.SPIKES, BlockRegister.STONE, BlockRegister.GRASS,
                BlockRegister.GRASS, BlockRegister.GRASS, BlockRegister.GRASS, BlockRegister.GRASS, BlockRegister.GRASS);
        new Chunk(2,
                BlockRegister.GRASS, BlockRegister.GRASS, BlockRegister.GRASS, BlockRegister.GRASS, BlockRegister.GRASS,
                BlockRegister.WATER, BlockRegister.GRASS, BlockRegister.GRASS, BlockRegister.STONE, BlockRegister.GRASS,
                BlockRegister.GRASS, BlockRegister.GRASS, BlockRegister.STONE, BlockRegister.FIRE, BlockRegister.GRASS,
                BlockRegister.GRASS, BlockRegister.GRASS, BlockRegister.WATER, BlockRegister.GRASS, BlockRegister.GRASS,
                BlockRegister.GRASS, BlockRegister.GRASS, BlockRegister.GRASS, BlockRegister.GRASS, BlockRegister.GRASS);
        new Chunk(2,
                BlockRegister.GRASS, BlockRegister.GRASS, BlockRegister.GRASS, BlockRegister.GRASS, BlockRegister.GRASS,
                BlockRegister.GRASS, BlockRegister.STONE, BlockRegister.BARREL, BlockRegister.STONE, BlockRegister.GRASS,
                BlockRegister.GRASS, BlockRegister.GRASS, BlockRegister.GRASS, BlockRegister.BARREL, BlockRegister.GRASS,
                BlockRegister.GRASS, BlockRegister.STONE, BlockRegister.BARREL, BlockRegister.STONE, BlockRegister.GRASS,
                BlockRegister.GRASS, BlockRegister.GRASS, BlockRegister.GRASS, BlockRegister.GRASS, BlockRegister.GRASS);
        new Chunk(2,
                BlockRegister.STONE, BlockRegister.BARREL, BlockRegister.GRASS, BlockRegister.BARREL, BlockRegister.STONE,
                BlockRegister.BARREL, BlockRegister.GRASS, BlockRegister.GRASS, BlockRegister.GRASS, BlockRegister.STONE,
                BlockRegister.GRASS, BlockRegister.GRASS, BlockRegister.GRASS, BlockRegister.GRASS, BlockRegister.STONE,
                BlockRegister.BARREL, BlockRegister.GRASS, BlockRegister.GRASS, BlockRegister.GRASS, BlockRegister.GRASS,
                BlockRegister.BARREL, BlockRegister.GRASS, BlockRegister.STONE, BlockRegister.GRASS, BlockRegister.BARREL);
        new Chunk(2,
                BlockRegister.GRASS, BlockRegister.GRASS, BlockRegister.GRASS, BlockRegister.GRASS, BlockRegister.GRASS,
                BlockRegister.GRASS, BlockRegister.BARREL, BlockRegister.FIRE, BlockRegister.STONE, BlockRegister.GRASS,
                BlockRegister.GRASS, BlockRegister.BARREL, BlockRegister.GRASS, BlockRegister.GRASS, BlockRegister.GRASS,
                BlockRegister.GRASS, BlockRegister.GRASS, BlockRegister.GRASS, BlockRegister.WATER, BlockRegister.GRASS,
                BlockRegister.GRASS, BlockRegister.GRASS, BlockRegister.GRASS, BlockRegister.GRASS, BlockRegister.GRASS);
        new Chunk(2,
                BlockRegister.GRASS, BlockRegister.GRASS, BlockRegister.GRASS, BlockRegister.GRASS, BlockRegister.GRASS,
                BlockRegister.GRASS, BlockRegister.WATER, BlockRegister.FIRE, BlockRegister.SPIKES, BlockRegister.GRASS,
                BlockRegister.GRASS, BlockRegister.GRASS, BlockRegister.GRASS, BlockRegister.GRASS, BlockRegister.GRASS,
                BlockRegister.GRASS, BlockRegister.FIRE, BlockRegister.GRASS, BlockRegister.SPIKES, BlockRegister.GRASS,
                BlockRegister.GRASS, BlockRegister.GRASS, BlockRegister.GRASS, BlockRegister.GRASS, BlockRegister.GRASS);
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

    /**
     * @return Nahodny chunk.
     */
    public static Chunk getRandomChunk() {
        return CHUNKS.getRandom();
    }

    /**
     * @return Nahodne otoceny zoznam kociek daneho chunku.
     */
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

    /**
     * S touto metodou som si pomohol zo StackOverFlowu, NEPISAL SOM JU JA.
     * <a href="https://stackoverflow.com/questions/2799755/rotate-array-clockwise">odkaz na diskusiu</a>
     * @param blocks 2D array blockov, ktore budu otocene
     * @return otocena 2D array blockov
     */
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
