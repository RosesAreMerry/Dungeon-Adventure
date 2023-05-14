package model;

import java.util.*;

import static model.Direction.*;

public class Room {

    private Item[] myItems;
    private Monster[] myMonsters;
    private boolean myIsExit;
    private boolean myIsEntrance;
    private boolean myHasPit;
    private Map<Direction, Room> myDoors;
    private Random myRandom;

    public Room(final int theNumberOfRooms) {
        this();
        this.addRooms(null,
                null,
                theNumberOfRooms,
                new ArrayList<>(),
                new Coordinate(0, 0));
    }

    private Room() {
        myRandom = new Random();
        myDoors = new HashMap<>();
    }

    int addRooms(final Room theEntryRoom,
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

        final int numberToGen = Math.min(3, theNumberRemaining);

        for (int i = numberToGen; i > 0; i--) {
            final Direction nextDirection = availableDirections.get(myRandom.nextInt(availableDirections.size()));
            myDoors.put(nextDirection, new Room());
            theOccupiedSpaces.add(nextDirection.applyToCoordinate(thePosition));
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
    public Room getRoom(String theRoom) {
        throw new UnsupportedOperationException("Method not yet implemented");
    }
    @Override
    public String toString() {
        throw new UnsupportedOperationException("Method not yet implemented");
    }
}
