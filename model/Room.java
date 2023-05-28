package model;

import java.io.Serializable;

public class Room implements Serializable {
    private Item[] myItems;
    private Monster[] myMonsters;
    private boolean myIsExit;
    private boolean myIsEntrance;
    private boolean myHasPit;
    private Room myNorth;
    private Room myEast;
    private Room mySouth;
    private Room myWest;

    public Monster[] getMyMonsters() {
        return myMonsters;
    }
    public Item[] getItems() {
        return myItems;
    }
    public boolean isEntrance() {
        throw new UnsupportedOperationException("Method not yet implemented");
    }
    public boolean isExit() {
        throw new UnsupportedOperationException("Method not yet implemented");
    }
    public boolean hasPit() {
        throw new UnsupportedOperationException("Method not yet implemented");
    }
    public Room getRoom(String theRoom) {
        throw new UnsupportedOperationException("Method not yet implemented");
    }
    @Override
    public String toString() {
        throw new UnsupportedOperationException("Method not yet implemented");
    }
}
