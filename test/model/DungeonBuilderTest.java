package model;

import org.junit.jupiter.api.Test;

import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

public class DungeonBuilderTest {

    // I would love to add more tests than this, but honestly, there's really only one thing that the builder asserts
    // that is not inherent in the structure of the builder which is that a certain number of rooms will be generated.
    // I would test traversal as well, but the structure means there is no way to have an un traversable dungeon.
    // The fact that every room is added to a map with Coordinates as keys means that there is no way to have a room
    // intersect with another room

    @Test
    public void testDungeonHasCorrectNumberOfRooms() {
        final DungeonBuilder dungeonBuilder = DungeonBuilder.INSTANCE;
        try {
            final Dungeon dungeon = dungeonBuilder.buildDungeon(1000);
            assertEquals(1000, countNumberOfRooms(dungeon));
        } catch (final Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void testDungeonHasTwoWayDoors() {
        final DungeonBuilder dungeonBuilder = DungeonBuilder.INSTANCE;
        try {
            final Dungeon dungeon = dungeonBuilder.buildDungeon(1000);
            for (final Room room : dungeon.getAllRooms().values()) {
                for (final Direction direction : Direction.values()) {
                    if (room.getDoor(direction) != null) {
                        assertSame(room.getDoor(direction).getDoor(direction.opposite()), room);
                    }
                }
            }
        } catch (final Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void testDungeonGenDoesNotStall() {
        final long currentTime = System.currentTimeMillis();
        final DungeonBuilder dungeonBuilder = DungeonBuilder.INSTANCE;
        try {
            dungeonBuilder.buildDungeon(10000);
            final long timeTaken = System.currentTimeMillis() - currentTime;
            assertTrue(timeTaken < 1000);
        } catch (final Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void testDungeonHasCorrectBranchingFactor() {
        final DungeonBuilder dungeonBuilder = DungeonBuilder.INSTANCE;
        try {
            final double overallBranching = IntStream.range(10, 1000)
                    .mapToObj(dungeonBuilder::buildDungeon)
                    .map(this::getBranchingFactor)
                    .reduce(0.0, Double::sum) / 990.0;
            assertTrue(overallBranching > 0.3);
        } catch (final Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    /**
     * Branching factor is the number of branches divided by the number of rooms.
     * Therefore, a dungeon that does not branch at all will have a branching factor of 0, and a dungeon that has no
     * straight lines will have a branching factor of 1 (however, since the first 5 rooms are not counted as branches this is impossible).
     * This is a measure of how much the dungeon branches out, and is a good measure of how interesting the dungeon is.
     * Currently, the generation does not assert a specific branching factor, but over a large number of tests, it should
     * average out to a certain number.
     *
     * @param theDungeon the dungeon to get the branching factor of
     *
     * @return the branching factor of the dungeon
     * */
    private double getBranchingFactor(final Dungeon theDungeon) {
        return (double) getBranchNumber(theDungeon) / countNumberOfRooms(theDungeon);
    }
    private int getBranchNumber(final Dungeon theDungeon) {
        if (theDungeon.getCurrentRoom() == null) {
            return 0;
        }

        return getBranchNumber(theDungeon.getCurrentRoom(), null);
    }

    private int getBranchNumber(final Room theRoom, final Direction theEntryDirection) {
        int count = 0;
        for (final Direction direction : Direction.values()) {
            if (direction == theEntryDirection) {
                continue;
            }
            if (theRoom.getDoor(direction) != null) {
                if (theEntryDirection != null && direction != theEntryDirection.opposite()) {
                    count++;
                }
                count += getBranchNumber(theRoom.getDoor(direction), direction.opposite());
            }
        }
        return count;
    }

    private int countNumberOfRooms(final Dungeon theDungeon) {
        if (theDungeon.getCurrentRoom() == null) {
            return 0;
        }

        return countNumberOfRooms(theDungeon.getCurrentRoom(), null);
    }

    private int countNumberOfRooms(final Room theRoom, final Direction theEntryDirection) {
        int count = 1;
        for (final Direction direction : Direction.values()) {
            if (direction == theEntryDirection) {
                continue;
            }
            if (theRoom.getDoor(direction) != null) {
                count += countNumberOfRooms(theRoom.getDoor(direction), direction.opposite());
            }
        }
        return count;
    }

}
