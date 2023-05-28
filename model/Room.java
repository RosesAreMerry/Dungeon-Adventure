package model;

import java.awt.*;
import java.util.*;
import java.util.List;

import static model.Direction.*;

public class Room {

    private Item[] myItems;
    private Monster[] myMonsters;
    private boolean myIsExit;
    private boolean myIsEntrance;
    private boolean myHasPit;
    private Map<Direction, Room> myDoors;
    private Random myRandom;

    private Coordinate myPosition;

    Room() {
        myRandom = new Random();
        myDoors = new HashMap<>();
    }

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
    public Room getDoor(final Direction theDirection) {
        return myDoors.get(theDirection);
    }
    public Map<Direction, Room> getDoors() {
        return myDoors;
    }
    void addDoor(final Direction theDirection, final Room theRoom) {
        myDoors.put(theDirection, theRoom);
    }
    @Override
    public String toString() {
        return toString(null);
    }

    private String toString(final Direction[] theDirections) {
        final StringBuilder sb = new StringBuilder();
        if (theDirections != null) {
            for (final Direction direction : theDirections) {
                sb.append(direction.getDirChar());
            }
        }

        sb.append('\n');
        myDoors.forEach((final Direction direction, final Room room) -> {
            if (room != null && (theDirections == null || direction != Direction.opposite(theDirections[theDirections.length-1]))) {
                final Direction[] newDirections;
                if (theDirections != null) {
                    newDirections = new Direction[theDirections.length + 1];
                    System.arraycopy(theDirections, 0, newDirections, 0, theDirections.length);
                } else {
                    newDirections = new Direction[1];
                }

                newDirections[newDirections.length - 1] = direction;

                sb.append(room.toString(newDirections));
            }
        });

        return sb.toString();
    }

}
