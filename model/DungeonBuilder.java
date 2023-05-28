package model;

import java.util.*;

import static model.Direction.*;

class DungeonBuilder {
    public static final DungeonBuilder INSTANCE = new DungeonBuilder();

    /** This is a constant that means that it will generate a hallway until there is this number
     *  rooms left in the current branch, when it will start normal gen. Decreasing this increases the length of hallways.*/

    private final Map<Coordinate, Room> coordinateRoomMap = new HashMap<>();
    private Random myRandom = new Random();

    private DungeonBuilder() { }

    /**
     * Main builder function. This is the only function that should be called from outside this class.
     * This will generate a dungeon with the specified number of rooms, or keep trying until it succeeds.
     * It shouldn't take too long, but it could.
     *
     * @param theNumberOfRooms the number of rooms to generate.
     *
     * @return a dungeon with the specified number of rooms.
     * */
    public Dungeon buildDungeon(final int theNumberOfRooms) {
        Room entrance = new Room();
        int generatedRooms = 0;
        // This loop will keep trying to generate a dungeon until it succeeds.
        // This is a brute force method, but it is guaranteed to work eventually.
        // It sucks, but it does work, and reworking it to work better would take a lot of time.
        while (generatedRooms < theNumberOfRooms - 1) {
            coordinateRoomMap.clear();
            entrance = new Room();
            coordinateRoomMap.put(new Coordinate(0, 0), entrance);
            generatedRooms = addRoomsRecursively(entrance, null, null, theNumberOfRooms - 1, new Coordinate(0, 0));
        }
        return new Dungeon(entrance, coordinateRoomMap);
    }

    /**
     * Main recursive method for generating the dungeon.
     *
     * @param theRoom the room to add rooms to.
     *                This is the room that the recursive call is currently adding rooms to.
     *
     * @param theEntryRoom the room that the recursive call came from.
     *
     * @param theEntryDirection the direction that the recursive call came from.
     *                          This is the direction that the recursive call is currently adding rooms to.
     *                          This is null if the recursive call is the first call.
     *
     * @param theNumberRemaining the number of rooms left to generate.
     *                           This is the number of rooms left to generate in the current branch.
     *
     * @param thePosition the position of theRoom.
     *
     * @return the number of rooms generated (should be the number requested, but weird edge cases exist).
     * */
    private int addRoomsRecursively(
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

        if (theEntryDirection == null) {
            numberToGen = values().length;
        } else {
            numberToGen = myRandom.nextInt(maxNumberToGen) + 1;
        }

        // This code generates the rooms directly adjacent to this room.
        for (int i = numberToGen; i > 0; i--) {
            final Direction nextDirection;
            nextDirection = availableDirections.get(myRandom.nextInt(availableDirections.size()));
            final Room nextRoom = new Room();
            theRoom.addDoor(nextDirection, nextRoom);
            coordinateRoomMap.put(nextDirection.applyToCoordinate(thePosition), nextRoom);
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


    /**
     * Search through adjacent spaces to see if there is a room there.
     *
     * @param thePosition The position to search around.
     * @return A list of directions that have rooms adjacent to the position.
     * */
    private List<Direction> searchAdjacent(final Coordinate thePosition) {
        final Map<Coordinate, Direction> adjacentSpaces = Map.of(
                NORTH.applyToCoordinate(thePosition), NORTH,
                SOUTH.applyToCoordinate(thePosition), SOUTH,
                WEST.applyToCoordinate(thePosition), WEST,
                EAST.applyToCoordinate(thePosition), EAST
        );

        final ArrayList<Direction> result = new ArrayList<>();

        adjacentSpaces.forEach((final Coordinate coordinate, final Direction direction) -> {
            if (coordinateRoomMap.containsKey(coordinate)) {
                result.add(direction);
            }
        });

        return result;
    }
}
