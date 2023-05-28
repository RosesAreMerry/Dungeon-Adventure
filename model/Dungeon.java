package model;

import java.util.Collection;
import java.util.Map;

public class Dungeon {

    private Map<Coordinate, Room> myRooms;
    private Room myHeroLocation;

    Dungeon(final Room theEntrance, final Map<Coordinate, Room> theRooms) {
        myHeroLocation = theEntrance;
        myRooms = theRooms;
    }
    public Room getCurrentRoom() {
        return myHeroLocation;
    }

    public void move(final Direction theDirection) {
        if (myHeroLocation.getDoor(theDirection) != null) {
            myHeroLocation = myHeroLocation.getDoor(theDirection);
        } else {
            throw new IllegalArgumentException("There is no door in that direction");
        }
    }

    /**
     * This method returns a collection of all the rooms in the dungeon.
     * It is used for testing purposes.
     *
     * @return a collection of all the rooms in the dungeon.
     * */
    Map<Coordinate, Room> getAllRooms() {
        return myRooms;
    }

}
