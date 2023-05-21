package model;

import java.util.*;
import java.util.stream.IntStream;

import static model.Direction.*;
import static model.Direction.WEST;

class DungeonBuilder {
    public static final DungeonBuilder INSTANCE = new DungeonBuilder();

    private final List<Coordinate> myOccupiedSpaces = new ArrayList<>();
    private Random myRandom = new Random();

    private DungeonBuilder() { }


    public Dungeon buildDungeon(final int theNumberOfRooms) {
        final Room entrance = new Room();
        myOccupiedSpaces.add(new Coordinate(0, 0));
        addRoomsRecursively(entrance, null, null, theNumberOfRooms - 1, new Coordinate(0, 0));
        return new Dungeon(entrance);
    }

    private void addRoomsRecursively(
            final Room theRoom,
            final Room theEntryRoom,
            final Direction theEntryDirection,
            final int theNumberRemaining,
            final Coordinate thePosition) {
        // Add door back to entry room.
        if (theEntryRoom != null) {
            theRoom.addDoor(theEntryDirection, theEntryRoom);
        }

        // theNumberRemaining == 0 is the base case
        if (theNumberRemaining == 0) {
            return;
        }

        final List<Direction> availableDirections = new ArrayList<>(List.of(NORTH, EAST, SOUTH, WEST));
        availableDirections.remove(theEntryDirection);

        availableDirections.removeAll(searchAdjacent(thePosition));


        // How many rooms to generate is the minimum of the number of available directions and the number remaining.
        final int numberToGen = Math.min(availableDirections.size(), theNumberRemaining);

        // This code generates the rooms directly adjacent to this room.
        for (int i = numberToGen; i > 0; i--) {
            final Direction nextDirection = availableDirections.get(myRandom.nextInt(availableDirections.size()));
            theRoom.addDoor(nextDirection, new Room());
            myOccupiedSpaces.add(nextDirection.applyToCoordinate(thePosition));
            availableDirections.remove(nextDirection);
        }

        // This code generates rooms for each room this room has a door to (not including entry)
        // It determines the number of rooms to generate by dividing the number remaining by the number of doors.
        // It also keeps track of the number of rooms generated, and won't generate more than the number remaining.

        final Map<Direction, Room> doors = theRoom.getDoors();


        int roomsToGenerate = theNumberRemaining - numberToGen;
        final int idealRoomsPerDoor = (int) Math.ceil((float) roomsToGenerate / doors.size());

        for (final Direction direction : doors.keySet()) {
            if (doors.get(direction) != theEntryRoom) {
                final int branchRooms = Math.min(idealRoomsPerDoor, roomsToGenerate);
                addRoomsRecursively(
                        doors.get(direction),
                        theRoom,
                        Direction.opposite(direction),
                        branchRooms,
                        direction.applyToCoordinate(thePosition)
                );
                roomsToGenerate -= branchRooms;
            }
        }
    }

    private List<Direction> searchAdjacent(final Coordinate thePosition) {
        final Map<Coordinate, Direction> adjacentSpaces = Map.of(
                NORTH.applyToCoordinate(thePosition), NORTH,
                SOUTH.applyToCoordinate(thePosition), SOUTH,
                WEST.applyToCoordinate(thePosition), WEST,
                EAST.applyToCoordinate(thePosition), EAST
        );

        final ArrayList<Direction> result = new ArrayList<>();

        adjacentSpaces.forEach((final Coordinate coordinate, final Direction direction) -> {
            if (myOccupiedSpaces.contains(coordinate)) {
                result.add(direction);
            }
        });

        return result;
    }

    void setRandom(final Random theRandom) {
        myRandom = theRandom;
    }
}
