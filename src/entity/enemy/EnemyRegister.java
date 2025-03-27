package entity.enemy;

import java.util.Random;
import java.util.function.Supplier;

public enum EnemyRegister {

    BLUE_SLIME(BlueSlime::new);

    private static final Random RANDOM = new Random();
    private final Supplier<AbstractEnemy> supplier;

    EnemyRegister(Supplier<AbstractEnemy> supplier) {
        this.supplier = supplier;
    }

    public AbstractEnemy getNew() {
        return this.supplier.get();
    }

    public static AbstractEnemy getRandom() {
        return values()[RANDOM.nextInt(values().length)].getNew();
    }

}
