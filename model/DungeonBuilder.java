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


    public Dungeon buildDungeon(final int theNumberOfRooms) throws Exception {
        Room entrance = new Room();
        int generatedRooms = 0;
        // This loop will keep trying to generate a dungeon until it succeeds.
        // This is a brute force method, but it is guaranteed to work eventually.
        // It sucks, but it does work, and reworking it to work better would take a lot of time.
        while (generatedRooms < theNumberOfRooms - 1) {
            myOccupiedSpaces.clear();
            entrance = new Room();
            myOccupiedSpaces.add(new Coordinate(0, 0));
            generatedRooms = addRoomsRecursively(entrance, null, null, theNumberOfRooms - 1, new Coordinate(0, 0));
        }
        return new Dungeon(entrance);
    }

    private int addRoomsRecursively(
            final Room theRoom,
            final Room theEntryRoom,
            final Direction theEntryDirection,
            final int theNumberRemaining,
            final Coordinate thePosition) throws Exception {
        // Add door back to entry room.
        if (theEntryRoom != null) {
            theRoom.addDoor(theEntryDirection, theEntryRoom);
        }

        // theNumberRemaining == 0 is the base case
        if (theNumberRemaining <= 0) {
            return 0;
        }

        final List<Direction> availableDirections = new ArrayList<>(List.of(NORTH, EAST, SOUTH, WEST));
        availableDirections.remove(theEntryDirection);

        availableDirections.removeAll(searchAdjacent(thePosition));


        // How many rooms to generate is the minimum of the number of available directions and the number remaining.
        final int maxNumberToGen = Math.min(availableDirections.size(), theNumberRemaining);

        if (maxNumberToGen == 0) {
            return 0;
        }

        final int numberToGen;

        // This code determines how many rooms to generate based on the number remaining.
        // If the number remaining is greater than 20, it will only generate 1 room.
        // Combined with the fact that the first new room will be in the opposite direction of the entry,
        // this makes hallways away from the center of the dungeon.
        // this is to reduce the number of rooms lost to branches enclosing the dungeon.

        // If the entry direction is null, it will generate 4 rooms, this is so the first room will have 4 doors.

        // Otherwise, it will generate a random number of rooms between 1 and the max number to generate.

        if (theEntryDirection == null) {
            numberToGen = 4;
        } else if (theNumberRemaining > 20) {
            numberToGen = 1;
        } else {
            numberToGen = myRandom.nextInt(maxNumberToGen) + 1;
        }

        // This code generates the rooms directly adjacent to this room.
        for (int i = numberToGen; i > 0; i--) {
            final Direction nextDirection;
            if (theEntryDirection != null && availableDirections.contains(Direction.opposite(theEntryDirection))) {
                nextDirection = Direction.opposite(theEntryDirection);
            } else {
                nextDirection = availableDirections.get(myRandom.nextInt(availableDirections.size()));
            }
            theRoom.addDoor(nextDirection, new Room());
            myOccupiedSpaces.add(nextDirection.applyToCoordinate(thePosition));
            availableDirections.remove(nextDirection);
        }

        // This code generates rooms for each room this room has a door to (not including entry)
        // It determines the number of rooms to generate by dividing the number remaining by the number of doors.
        // It also keeps track of the number of rooms generated, and won't generate more than the number remaining.

        final Map<Direction, Room> doors = theRoom.getDoors();


        int roomsToDistribute = theNumberRemaining - numberToGen;
        int doorsLeft = theEntryDirection != null ? doors.size() - 1 : doors.size();


        for (final Direction direction : doors.keySet()) {
            if (doors.get(direction) != theEntryRoom) {
                final int idealRoomsPerDoor = (int) Math.ceil((float) roomsToDistribute / doorsLeft) + 1;

                final int branchRooms = Math.min(idealRoomsPerDoor, roomsToDistribute);

                final int generatedRooms = addRoomsRecursively(
                        doors.get(direction),
                        theRoom,
                        Direction.opposite(direction),
                        branchRooms,
                        direction.applyToCoordinate(thePosition)
                );
                roomsToDistribute -= generatedRooms;
                doorsLeft--;
            }
        }

        // rooms to generate is now the number of rooms that were not generated.
        return theNumberRemaining - roomsToDistribute;
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
