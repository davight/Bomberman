package util;

import java.util.HashMap;
import java.util.Map;

/**
 * Pomocna trieda na ulozenie T prvkov s n pravdepodobnostou vyberu.
 */
public class WeightedRandomness<E> {

    private final HashMap<E, Integer> weights = new HashMap<>();
    private int sumWeights = 0;

    /**
     * Inicializuje novy zoznam prvkov.
     */
    public WeightedRandomness() {
    }

    /**
     * Prida prvok do zoznamu, s pravdepodobnostou vyberu 1.
     * @param e prvok na pridanie
     */
    public void add(E e) {
        this.add(e, 1);
    }

    /**
     * Prida prvok do zoznamu.
     * @param e prvok na pridanie
     * @param weight pravdepodobnost vyberu
     */
    public void add(E e, int weight) {
        if (!this.weights.containsKey(e)) {
            this.weights.put(e, weight);
            this.sumWeights += weight;
        }
    }

    /**
     * S touto metodou som si pomohol zo StackOverFlowu, NEPISAL SOM JU JA.
     * <a href="https://stackoverflow.com/questions/6737283/weighted-randomness-in-java">odkaz na diskusiu</a>
     * @return Nahodne vybrany prvok
     */
    public E getRandom() {
        double r = Util.randomDouble() * this.sumWeights;
        int c = 0;
        for (Map.Entry<E, Integer> e : this.weights.entrySet()) {
            c += e.getValue();
            if (c >= r) {
                return e.getKey();
            }
        }
        return null;
    }
}
