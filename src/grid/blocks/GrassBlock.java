package grid.blocks;

import events.EnemyEnterTileEvent;
import events.PlayerEnterTileEvent;

import java.util.HashMap;

public class GrassBlock extends AbstractBlock {

    private static HashMap<String, Integer> textures = new HashMap<>();

    static {
        textures.put("grass2", 30);
        textures.put("grass1", 5);
        textures.put("grass3", 2);
        textures.put("grass4", 2);
        textures.put("grass5", 1);
    }

    protected GrassBlock() {
        super(textures);
    }

    @Override
    public boolean isSeeThrough() {
        return true;
    }

    @Override
    public boolean isSpawnable() {
        return true;
    }

    @Override
    public boolean canEnemyEnterBlock(EnemyEnterTileEvent e) {
        return true;
    }

    @Override
    public boolean canPlayerEnterBlock(PlayerEnterTileEvent e) {
        return true;
    }
}
