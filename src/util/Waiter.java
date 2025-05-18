package util;

import game.Game;

import java.util.function.Consumer;

/**
 * Pomocka na spustanie nejakych akcii az o nejaku dobu.
 */
public class Waiter {

    private final Consumer<Waiter> runnable;
    private final long waitDuration;

    private Waiter thenRun = null;
    private boolean waiting = false;
    private boolean repeat = false;
    private long startTime;

    /**
     * Inicializuje noveho cakaca.
     * @param waitDuration cas v milisekundach, ktory cakac musi pockat pred vykonanim akcie
     * @param runnable
     */
    public Waiter(long waitDuration, Consumer<Waiter> runnable) {
        this.waitDuration = waitDuration;
        this.runnable = runnable;
    }

    /**
     * Prida dalsieho cakaca, ktory sa spusti po ukonceni tohto.
     * Nemozno pridavat dalsieho cakaca pokial je tento nastaveny na repeat alebo pokial uz caka na spustenie akcie!
     * @param thenRun dalsi cakac
     * @return Aktualny cakac
     */
    public Waiter andThen(Waiter thenRun) {
        if (this.waiting) {
            throw new IllegalStateException("Waiter already running");
        }
        if (this.repeat) {
            throw new IllegalStateException("Cant stack waiter when set to repeat!");
        }
        this.thenRun = thenRun;
        return thenRun;
    }

    /**
     * Spusti cakac a nastavi ho na opakovanie.
     */
    public void waitAndRepeat() {
        this.repeat = true;
        this.waitAndRun();
    }

    /**
     * Spusti cakac.
     */
    public void waitAndRun() {
        Game.manageObject(this);
        if (this.waiting) {
            throw new IllegalStateException("Waiting already!");
        }
        this.waiting = true;
        this.startTime = System.currentTimeMillis();
    }

    @ShapesGeListener
    public void tick() {
        if (this.waiting && System.currentTimeMillis() - this.startTime >= this.waitDuration) {
            this.waiting = false;
            this.runnable.accept(this);
            if (this.repeat) {
                this.waitAndRepeat();
            } else if (this.thenRun != null) {
                this.thenRun.waitAndRun();
            }
            Game.stopManagingObject(this);
        }
    }

    /**
     * Zrusi aktualne cakanie na vykonanie akcie.
     */
    public void cancelWait() {
        this.waiting = false;
    }

    /**
     * @return Ci cakac caka na vykonanie akcie.
     */
    public boolean isWaiting() {
        return this.waiting;
    }
}
