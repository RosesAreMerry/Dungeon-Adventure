package model;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    public Map<String, Room> getNeighbors() {
        final Coordinate current = myRooms.entrySet().stream()
                .filter(entry -> entry.getValue().equals(myHeroLocation)).findFirst().get().getKey();
        return myRooms.keySet().stream().filter(key -> key.isNeighbor(current)).collect(Collectors.toMap(current::getDirection, myRooms::get));
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
