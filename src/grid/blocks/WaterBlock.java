package grid.blocks;

import events.AfterEntityEnterBlockListener;
import events.EntityEnterBlockEvent;
import events.PlayerEnterBlockEvent;

import java.util.HashMap;
import java.util.Optional;

public class WaterBlock extends AbstractBlock implements AfterEntityEnterBlockListener {

    private static HashMap<String, Integer> textures = new HashMap<>();

    static {
        //textures.put("water_empty1", 3);
        //textures.put("water_empty2", 3);
        textures.put("water_with_ducks", 1);
        //textures.put("water_small1", 4);
        //textures.put("water_small2", 4);
        textures.put("water_small3", 4);
        textures.put("water_small4", 4);
        textures.put("water_big1", 2);
    }

    protected WaterBlock() {
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
    public Optional<BlockRegister> afterBlockExplosionEvent() {
        return Optional.empty();
    }

    @Override
    public boolean onEntityEnterBlock(EntityEnterBlockEvent e) {
        return false;
    }

    @Override
    public void afterEnemyEnterBlock(EntityEnterBlockEvent e) {
    }

    @Override
    public boolean onPlayerEnterBlock(PlayerEnterBlockEvent e) {
        return true;
    }

    @Override
    public void afterPlayerEnterBlock(PlayerEnterBlockEvent e) {
        e.player().kill();
    }
}
