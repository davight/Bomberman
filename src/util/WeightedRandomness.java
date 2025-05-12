package util;

import java.util.HashMap;
import java.util.Map;

public class WeightedRandomness<T> {

    private final HashMap<T, Integer> weights = new HashMap<>();
    private int sumWeights = 0;

    public WeightedRandomness() {
    }

    public void add(T t) {
        this.add(t, 1);
    }

    public void add(T t, int weight) {
        if (!this.weights.containsKey(t)) {
            this.weights.put(t, weight);
            this.sumWeights += weight;
        }
    }

    public T getRandom() {
        double r = Util.randomDouble() * this.sumWeights;
        int c = 0;
        for (Map.Entry<T, Integer> e : this.weights.entrySet()) {
            c += e.getValue();
            if (c >= r) {
                return e.getKey();
            }
        }
        return null; // shouldnt happen
    }
}
