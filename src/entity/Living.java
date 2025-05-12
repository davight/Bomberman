package entity;

public interface Living {

    boolean isAlive();

    void die();

    int getHealth();

    void hurt(int damage);

}
