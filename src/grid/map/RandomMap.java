package grid.map;

import entity.movement.Direction;
import util.Util;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * Trieda RandomMap sa stara o vytvaranie nahodnych map.
 */
public class RandomMap {

    private static final int MINIMUM_CHUNKS = 4;
    private static final int MAXIMUM_CHUNKS = Map.WIDTH_CHUNKS * Map.HEIGHT_CHUNKS;

    private final HashSet<ChunkPosition> usedPositions = new HashSet<>();
    private final ArrayList<ChunkPosition> adjancedPositions = new ArrayList<>();
    private final Chunk[][] mapChunks;

    /**
     * Inicializuje a vytvori novu nahodnu mapu.
     */
    public RandomMap() {
        Chunk[][] chunks = new Chunk[Map.HEIGHT_CHUNKS][Map.WIDTH_CHUNKS];
        for (int y = 0; y < Map.HEIGHT_CHUNKS; y++) {
            for (int x = 0; x < Map.WIDTH_CHUNKS; x++) {
                chunks[y][x] = Chunk.EMPTY_CHUNK;
            }
        }

        int numberOfChunks = Util.randomInt(MINIMUM_CHUNKS, MAXIMUM_CHUNKS);
        ArrayList<Chunk> randomChunks = new ArrayList<>(numberOfChunks);
        for (int i = 0; i < numberOfChunks; i++) {
            randomChunks.add(Chunk.getRandomChunk());
        }

        int startRow = Util.randomInt(0, Map.HEIGHT_CHUNKS - 1);
        int startCol = Util.randomInt(0, Map.WIDTH_CHUNKS - 1);

        ChunkPosition startPos = new ChunkPosition(startRow, startCol);
        chunks[startPos.row()][startPos.column()] = randomChunks.removeFirst();
        this.usedPositions.add(startPos);
        this.addNeighbourChunks(startPos);

        while (!randomChunks.isEmpty() && !this.adjancedPositions.isEmpty()) {
            ChunkPosition random = this.adjancedPositions.get(Util.randomInt(0, this.adjancedPositions.size() - 1));
            chunks[random.row()][random.column()] = randomChunks.removeFirst();
            this.usedPositions.add(random);

            this.addNeighbourChunks(random);
        }

        this.mapChunks = chunks;
    }

    private void addNeighbourChunks(ChunkPosition pos) {
        for (Direction d : Direction.toShuffledArray()) {
            int newRow = pos.row() + d.getX();
            int newCol = pos.column() + d.getY();
            if (newRow >= 0 && newRow < Map.HEIGHT_CHUNKS && newCol >= 0 && newCol < Map.WIDTH_CHUNKS) {
                ChunkPosition newPos = new ChunkPosition(newRow, newCol);
                if (!this.usedPositions.contains(newPos) && !this.adjancedPositions.contains(newPos)) {
                    this.adjancedPositions.add(newPos);
                }
            }
        }
    }

    /**
     * @return 2D zoznam chunkov, ktore tvoria tuto mape.
     */
    public Chunk[][] getMapChunks() {
        return this.mapChunks;
    }

    private record ChunkPosition(int row, int column) {
    }
}
