package grid.blocks;

import events.EnemyEnterTileEvent;
import events.EventManager;
import events.PlayerEnterTileEvent;
import events.PlayerStepOnBlockEvent;

import java.util.HashMap;

public class WaterBlock extends AbstractBlock {

    private static HashMap<String, Integer> textures = new HashMap<>();

    static {
        textures.put("water1", 1);
        textures.put("water2", 4);
        textures.put("water3", 4);
        textures.put("water4", 3);
        textures.put("water5", 5);
    }

    protected WaterBlock() {
        super(textures);

        EventManager.registerHandler(PlayerStepOnBlockEvent.class, (event) -> {
            if (event.block() == this) {
                event.player().kill();
            }
        });
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
        return true;
    }
}
