package grid.blocks;

import entity.Player;
import events.EntityEnterBlockEvent;
import events.PlayerEnterBlockEvent;
import main.GameManager;

import java.util.Optional;

public class FireBlock extends AbstractBlock {

    private static final int DELAY_BETWEEN_HITS = 3000; // in ms

    private boolean isOnFire = false;
    private long lastDamage;
    private Player player;

    protected FireBlock() {
        super(0, "fire_block_passive", "fire_block_active");
    }

    public void tick() {
        if (this.player == null || this.player.getTile().getBlockT() != this) {
            GameManager.getInstance().stopManagingObjects(this);
            return;
        }
        if (this.lastDamage + DELAY_BETWEEN_HITS < System.currentTimeMillis()) {
            return;
        }
        this.lastDamage = System.currentTimeMillis();
        this.player.takeDamage(1);
    }

    @Override
    public boolean isSeeThrough() {
        return !this.isOnFire;
    }

    @Override
    public Optional<BlockRegister> afterBlockExplosionEvent() {
        if (!this.isOnFire) {
            this.isOnFire = true;
            this.setTexture(1);
        }
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

    @Override
    public void afterPlayerEnterBlockEvent(PlayerEnterBlockEvent e) {
        if (this.isOnFire) {
            GameManager.getInstance().manageObjects(this);
            this.lastDamage = System.currentTimeMillis();
            this.player = e.player();
            e.player().takeDamage(1);
        }
    }
}
