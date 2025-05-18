package entity.enemy;

import util.WeightedRandomness;

import java.util.function.Supplier;

/**
 * Enum typov nepriatelov.
 */
public enum EnemyRegister {

    /**
     * Deadly slime typ nepriatela. Reprezentujuci triedu DeadlySlime.
     * @see DeadlySlime
     */
    DEADLY_SLIME(1, DeadlySlime::new),

    /**
     * Explosive slime typ nepriatela. Reprezentujuci triedu ExplosiveSlime.
     * @see ExplosiveSlime
     */
    EXPLOSIVE_SLIME(2, ExplosiveSlime::new),

    /**
     * Speed slime typ nepriatela. Reprezentujuci triedu SpeedSlime.
     * @see SpeedSlime
     */
    SPEED_SLIME(3, SpeedSlime::new);

    private static WeightedRandomness<EnemyRegister> randomEnemies = new WeightedRandomness<>();
    static {
        for (EnemyRegister e : values()) {
            randomEnemies.add(e, e.weight);
        }
    }

    private final Supplier<AbstractEnemy> supplier;
    private final int weight;

    EnemyRegister(int weight, Supplier<AbstractEnemy> supplier) {
        this.supplier = supplier;
        this.weight = weight;
    }

    /**
     * @return Novu instanciu nepriatela daneho typu.
     */
    public AbstractEnemy getNew() {
        return this.supplier.get();
    }

    /**
     * @return Nahodny typ nepriatela.
     */
    public static EnemyRegister getRandom() {
        return randomEnemies.getRandom();
    }

}
