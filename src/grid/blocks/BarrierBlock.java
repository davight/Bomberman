package grid.blocks;

import events.EnemyEnterTileEvent;
import events.PlayerEnterTileEvent;

import java.util.HashMap;

/**
 * Trieda BarrierBlock, ktora predstavuje kocku Barriery.
 */
public class BarrierBlock extends AbstractBlock {

    private static HashMap<String, Integer> textures = new HashMap<>();
    static {
        textures.put("barrier1", 5);
        textures.put("barrier2", 5);
        textures.put("barrier5", 10);
        textures.put("barrier6", 10);
        textures.put("barrier7", 10);
        textures.put("barrier8", 10);
        textures.put("barrier9", 10);
        textures.put("barrier10", 10);
        textures.put("barrier11", 10);
        textures.put("barrier12", 10);
        textures.put("barrier13", 10);
        textures.put("barrier14", 10);
        textures.put("barrier15", 10);
        textures.put("barrier16", 10);
        textures.put("barrier17", 10);
        textures.put("barrier18", 10);
        textures.put("barrier19", 1);
    }

    protected BarrierBlock() {
        super(textures);
    }

    /**
     * @return Ci je mozne cez kocku vidiet dalej.
     */
    @Override
    public boolean isSeeThrough() {
        return false;
    }

    /**
     * @return Ci je kocka bezpecna pre spawn hracov a nepriatelov.
     */
    @Override
    public boolean isSpawnable() {
        return false;
    }

    /**
     * @return Ci je mozne aby nepriatel vosiel na tuto kocku.
     */
    @Override
    public boolean canEnemyEnterBlock(EnemyEnterTileEvent e) {
        return false;
    }

    /**
     * @return Ci je mozne aby hrac vosiel na tuto kocku.
     */
    @Override
    public boolean canPlayerEnterBlock(PlayerEnterTileEvent e) {
        return false;
    }
}
