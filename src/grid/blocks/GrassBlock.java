package grid.blocks;

import events.EntityEnterBlockEvent;
import events.PlayerEnterBlockEvent;

import java.util.HashMap;
import java.util.Optional;

public class GrassBlock extends AbstractBlock {

    private static HashMap<String, Integer> textures = new HashMap<>();

    static {
        //textures.put("grass", 10);
        //textures.put("grass2", 10);
        textures.put("grass3", 30);
        textures.put("grass_with_rocks", 1);
        textures.put("grass_with_rocks2", 1);
        textures.put("grass_with_rocks3", 1);
        textures.put("grass_with_rocks4", 1);
        textures.put("grass_with_rocks5", 1);
        textures.put("grass_with_rocks6", 1);
        textures.put("grass_with_rocks7", 1);
        textures.put("grass_with_rocks8", 1);
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
    public Optional<BlockRegister> afterBlockExplosionEvent() {
        return Optional.empty();
    }

    @Override
    public boolean onEntityEnterBlock(EntityEnterBlockEvent e) {
        return true;
    }

    @Override
    public boolean onPlayerEnterBlock(PlayerEnterBlockEvent e) {
        return true;
    }
}
