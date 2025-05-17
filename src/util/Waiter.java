package util;

import game.Game;

import java.util.function.Consumer;

public class Waiter {

    private final Consumer<Waiter> runnable;
    private final long waitDuration;

    private Waiter thenRun = null;
    private boolean waiting = false;
    private boolean repeat = false;
    private long startTime;

    public Waiter(long waitDuration, Consumer<Waiter> runnable) {
        this.waitDuration = waitDuration;
        this.runnable = runnable;
    }

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

    public void waitAndRepeat() {
        this.repeat = true;
        this.waitAndRun();
    }

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
            } else if (this.thenRun != null) { // cant run other waiter when its set to repeat
                this.thenRun.waitAndRun();
            }
            Game.stopManagingObject(this);
        }
    }

    public void cancelWait() {
        this.waiting = false;
    }

    public boolean isWaiting() {
        return this.waiting;
    }
}
