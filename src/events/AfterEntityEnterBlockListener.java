package events;

public interface AfterEntityEnterBlockListener {

    /**
     * Fires after moving to given block. Do nothing as default implementation.
     */
    void afterEnemyEnterBlock(EntityEnterBlockEvent e);

    /**
     * Fires after moving to given block. Do nothing as default implementation.
     */
    void afterPlayerEnterBlock(PlayerEnterBlockEvent e);

}
