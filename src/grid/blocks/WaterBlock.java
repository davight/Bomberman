package grid.blocks;

import events.EnemyEnterTileEvent;
import events.EventManager;
import events.PlayerEnterTileEvent;
import events.PlayerStepOnBlockEvent;

import java.util.HashMap;

/**
 * Trieda WaterBlock, ktora predstavuje kocku Water.
 */
public class WaterBlock extends AbstractBlock {

    private static HashMap<String, Integer> textures = new HashMap<>();

    static {
        textures.put("water1", 1);
        textures.put("water2", 4);
        textures.put("water3", 4);
        textures.put("water4", 3);
        textures.put("water5", 5);

        EventManager.registerHandler(PlayerStepOnBlockEvent.class, (event) -> {
            if (event.block() instanceof WaterBlock) {
                event.player().kill();
            }
        });
    }

    protected WaterBlock() {
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
        return true;
    }
}
