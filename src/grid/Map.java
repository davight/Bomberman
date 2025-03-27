package grid;

import grid.blocks.BlockRegister;

import java.io.Serializable;
import java.util.HashMap;

public class Map {

    private static final HashMap<Map, Data> LOADED = new HashMap<>();

    public static Map getMap(String name) {
        for (Map m : LOADED.keySet()) {
            if (m.name.equals(name)) {
                return m;
            }
        }
        return null;
    }

    private final String name;
    private final Data data;

    private Map(String name, Data data) {
        this.name = name;
        this.data = data;
    }

    public BlockRegister[][] getBlocks() {
        return this.data.blocks();
    }

    private record Data(BlockRegister[][] blocks) implements Serializable {

    }


}
