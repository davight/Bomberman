package grid.blocks;

import entity.player.AbstractPlayer;
import events.EnemyEnterTileEvent;
import events.EventManager;
import events.PlayerEnterTileEvent;
import events.PlayerStepOnBlockEvent;
import grid.map.Tile;
import util.Util;
import util.Waiter;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Optional;

/**
 * Trieda FireBlock, ktora predstavuje kocku Fire.
 */
public class FireBlock extends AbstractBlock implements Explodable {

    private static final int DELAY_BETWEEN_HITS = 2000; // in ms

    private static HashSet<Tile> burningTiles = new HashSet<>();
    private static HashMap<AbstractPlayer, Waiter> fireDamagers = new HashMap<>();
    static {
        EventManager.registerHandler(PlayerStepOnBlockEvent.class, (event) -> {
            Waiter damager = fireDamagers.computeIfAbsent(event.player(), (p) -> {
                return new Waiter(DELAY_BETWEEN_HITS, (_) -> p.hurt(1));
            });
            if (event.block() instanceof FireBlock fireBlock && fireBlock.isOnFire && event.player().isAlive()) {
                event.player().hurt(1);
                if (!damager.isWaiting()) {
                    damager.waitAndRepeat();
                }
            } else if (damager.isWaiting()) {
                damager.cancelWait();
            }
        });

        new Waiter(200, (w) -> {
            for (Tile t : new HashSet<>(burningTiles)) {
                if (t.getBlock() instanceof FireBlock block && block.isOnFire) {
                    block.setTexture(Util.randomInt(2, 4));
                    t.update();
                } else {
                    burningTiles.remove(t);
                }
            }
        }).waitAndRepeat();
    }

    private boolean isOnFire = false;

    protected FireBlock() {
        super(Util.randomInt(0, 1),
                "fire_block_passive", "fire_block_passive2",
                "fire_block_active1", "fire_block_active2", "fire_block_active3");
    }

    /**
     * @return Ci je mozne cez kocku vidiet dalej.
     */
    @Override
    public boolean isSeeThrough() {
        return !this.isOnFire;
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
        return !this.isOnFire;
    }

    /**
     * @return Ci je mozne aby hrac vosiel na tuto kocku.
     */
    @Override
    public boolean canPlayerEnterBlock(PlayerEnterTileEvent e) {
        return true;
    }

    /**
     * @return Volitelnu kocku, ktora sa po vybuchu starej zobrazi na jej mieste.
     */
    @Override
    public Optional<BlockRegister> newBlock() {
        return Optional.empty();
    }

    /**
     * Spusti po vybuchnu na danom mieste.
     * @param at miesto vybuchu
     */
    @Override
    public void onExplosion(Tile at) {
        burningTiles.add(at);
        this.isOnFire = true;
        super.setTexture(1);
    }
}
