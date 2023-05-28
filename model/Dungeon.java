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
        throw new UnsupportedOperationException("Method not yet implemented");
    }
    public void move(String theString) {
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
