package game.canvas;

public class MovementHelper {

    public static final MovementHelper UP = new MovementHelper(0, -1);
    public static final MovementHelper DOWN = new MovementHelper(0, 1);
    public static final MovementHelper LEFT = new MovementHelper(-1, 0);
    public static final MovementHelper RIGHT = new MovementHelper(1, 0);

    private final int x;
    private final int y;

    private MovementHelper(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int[] calculateNthPosition(Tile startTile, int n, int max) {
        int[] xy = new int[2];
        xy[0] = (startTile.getBoardX() * Tile.TILE_SIZE) + (n * ((Tile.TILE_SIZE / n) * this.x));
        xy[1] = (startTile.getBoardY() * Tile.TILE_SIZE) + (n * ((Tile.TILE_SIZE / n) * this.y));
        return xy;
    }

    public int[] calculateLastPosition(Tile startTile, int max) {
        return this.calculateNthPosition(startTile, max, max);
    }

}
