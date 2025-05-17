package grid.blocks;

import events.EnemyEnterTileEvent;
import events.PlayerEnterTileEvent;

import java.util.HashMap;

public class BarrierBlock extends AbstractBlock {

    private static HashMap<String, Integer> textures = new HashMap<>();
    static {
        textures.put("barrier1", 1);
        textures.put("barrier2", 1);
        textures.put("barrier5", 2);
        textures.put("barrier6", 2);
        textures.put("barrier7", 2);
        textures.put("barrier8", 2);
        textures.put("barrier9", 2);
        textures.put("barrier10", 2);
        textures.put("barrier11", 2);
        textures.put("barrier12", 2);
        textures.put("barrier13", 2);
        textures.put("barrier14", 2);
        textures.put("barrier15", 2);
    }


    protected BarrierBlock() {
        super(textures);
    }

    @Override
    public boolean isSeeThrough() {
        return false;
    }

    @Override
    public boolean isSpawnable() {
        return false;
    }

    @Override
    public boolean canEnemyEnterBlock(EnemyEnterTileEvent e) {
        return false;
    }

    @Override
    public boolean canPlayerEnterBlock(PlayerEnterTileEvent e) {
        return false;
    }
}
