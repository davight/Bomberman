package grid.blocks;

import events.EnemyEnterTileEvent;
import events.PlayerEnterTileEvent;

import java.util.HashMap;

/**
 * Trieda GrassBlock, ktora predstavuje kocku Grass.
 */
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

    /**
     * @return Ci je mozne cez kocku vidiet dalej.
     */
    @Override
    public boolean isSeeThrough() {
        return true;
    }

    /**
     * @return Ci je kocka bezpecna pre spawn hracov a nepriatelov.
     */
    @Override
    public boolean isSpawnable() {
        return true;
    }

    /**
     * @return Ci je mozne aby nepriatel vosiel na tuto kocku.
     */
    @Override
    public boolean canEnemyEnterBlock(EnemyEnterTileEvent e) {
        return true;
    }

    /**
     * @return Ci je mozne aby hrac vosiel na tuto kocku.
     */
    @Override
    public boolean canPlayerEnterBlock(PlayerEnterTileEvent e) {
        return true;
    }
}
