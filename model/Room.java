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

    public Room(final int theNumberOfRooms) {
        this(new Coordinate(0, 0));
        this.addRooms(null,
                null,
                theNumberOfRooms,
                new ArrayList<>(),
                new Coordinate(0, 0));
    }

    private Room(Coordinate thePosition) {
        myRandom = new Random();
        myDoors = new HashMap<>();
        myPosition = thePosition;
    }

    int addRooms(
            final Room theEntryRoom,
            final Direction theEntryDirection,
            final int theNumberRemaining,
            final List<Coordinate> theOccupiedSpaces,
            final Coordinate thePosition) {
        if (theNumberRemaining == 0) {
            return 0;
        }

        final List<Direction> availableDirections = new ArrayList<>(List.of(NORTH, EAST, SOUTH, WEST));
        availableDirections.remove(theEntryDirection);

        availableDirections.removeAll(searchAdjacent(theOccupiedSpaces, thePosition));

        final int numberToGen = Math.min(availableDirections.size(), theNumberRemaining);

        for (int i = numberToGen; i > 0; i--) {
            final Direction nextDirection = availableDirections.get(myRandom.nextInt(availableDirections.size()));
            myDoors.put(nextDirection, new Room(nextDirection.applyToCoordinate(thePosition)));
            theOccupiedSpaces.add(nextDirection.applyToCoordinate(thePosition));
            availableDirections.remove(nextDirection);
        }

        int numberGenerated = numberToGen;

        // This code generates rooms for each room this room has a door to (not including entry)
        // It then takes the int result (number of rooms generated) and reduces them and adds them to the number here.
        numberGenerated += myDoors.entrySet().stream().map((final Map.Entry<Direction, Room> theDoor) -> {
            final Room room = theDoor.getValue();
            final Direction direction = theDoor.getKey();
            final float numberRemainingFloat = (float) theNumberRemaining /numberToGen;
            int numberRemaining = Math.round(numberRemainingFloat);
            if(theNumberRemaining == 1) {
                numberRemaining = 0;
            }
            return room.addRooms(
                    this,
                    Direction.opposite(direction),
                    numberRemaining,
                    theOccupiedSpaces,
                    direction.applyToCoordinate(thePosition));
        }).reduce(0, Integer::sum);

        myDoors.put(theEntryDirection, theEntryRoom);

        return numberGenerated;

    }

    private List<Direction> searchAdjacent(final List<Coordinate> theOccupiedSpaces, final Coordinate thePosition) {
        final Map<Coordinate, Direction> adjacentSpaces = Map.of(
                NORTH.applyToCoordinate(thePosition), NORTH,
                SOUTH.applyToCoordinate(thePosition), SOUTH,
                WEST.applyToCoordinate(thePosition), WEST,
                EAST.applyToCoordinate(thePosition), EAST
                );

        final ArrayList<Direction> result = new ArrayList<>();

        adjacentSpaces.forEach((final Coordinate coordinate, final Direction direction) -> {
            if (theOccupiedSpaces.contains(coordinate)) {
                result.add(direction);
            }
        });

        return result;
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
    @Override
    public String toString() {
        return toString(null);
    }

    private String toString(final Direction[] theDirections) {
        final StringBuilder sb = new StringBuilder();
        if (theDirections == null) {
            sb.append("First Room");
        } else {
            sb.append("Room ");
            for (final Direction direction : theDirections) {
                sb.append(direction.getDirChar());
            }
        }
        sb.append("\nDoors: \n");
        myDoors.forEach((final Direction direction, final Room _room) -> {
            if (direction != null) {
                sb.append(direction.getDirChar()).append(" ");
            }
        });
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
        sb.append('\n');

        return sb.toString();
    }

}
