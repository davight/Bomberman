package grid.blocks;

import entity.player.AbstractPlayer;
import events.AfterEntityEnterBlockListener;
import events.EntityEnterBlockEvent;
import events.PlayerEnterBlockEvent;
import util.Waiter;

import java.util.HashMap;
import java.util.Optional;

public class FireBlock extends AbstractBlock implements AfterEntityEnterBlockListener {

    private static final int DELAY_BETWEEN_HITS = 3000; // in ms

    private final HashMap<AbstractPlayer, Waiter> waiters = new HashMap<>();

    private boolean isOnFire = false;

    protected FireBlock() {
        super(0, "fire_block_passive", "fire_block_active");
    }

    @Override
    public boolean isSeeThrough() {
        return !this.isOnFire;
    }

    @Override
    public boolean isSpawnable() {
        return false;
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
    public void afterEnemyEnterBlock(EntityEnterBlockEvent e) {
    }

    @Override
    public boolean onPlayerEnterBlock(PlayerEnterBlockEvent e) {
        return true;
    }

    @Override
    public void afterPlayerEnterBlock(PlayerEnterBlockEvent e) {
        if (!this.isOnFire) {
            return;
        }
        Waiter damager = this.waiters.computeIfAbsent(e.player(), (p) -> {
            return new Waiter(DELAY_BETWEEN_HITS, (w) -> {
                if (p.getTile().getBlock() == this) {
                    p.hurt(1);
                } else {
                    w.cancelWait();
                }
            });
        });
        if (!damager.isWaiting()) {
            damager.waitAndRepeat();
        }
    }
}
