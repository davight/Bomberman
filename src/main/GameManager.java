package main;

import fri.shapesge.Manager;

import java.util.HashSet;

public class GameManager {

    private static final Manager MANAGER = new Manager();
    private static HashSet<Object> managedObjects = new HashSet<>();
    private static GameManager instance;

    public static GameManager getInstance() {
        if (instance == null) {
            instance = new GameManager();
        }
        return instance;
    }

    protected GameManager() {
        for (Object o : managedObjects) {
            MANAGER.stopManagingObject(o);
        }
        managedObjects = new HashSet<>();
    }

    public void manageObjects(Object... oArr) {
        for (Object o : oArr) {
            if (managedObjects.add(o)) {
                MANAGER.manageObject(o);
            }
        }
    }

    public void stopManagingObjects(Object... oArr) {
        for (Object o : oArr) {
            if (managedObjects.remove(o)) {
                MANAGER.stopManagingObject(o);
            }
        }
    }

}
