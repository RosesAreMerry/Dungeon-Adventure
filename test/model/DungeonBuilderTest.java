package model;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.function.Function;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

public class DungeonBuilderTest {

    @Test
    public void testDungeonHasCorrectNumberOfRooms() {
        final DungeonBuilder dungeonBuilder = DungeonBuilder.INSTANCE;
        try {
            final Dungeon dungeon = dungeonBuilder.buildDungeon(100);
            assertEquals(100, countNumberOfRooms(dungeon));
        } catch (final Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void testDungeonHasTwoWayDoors() {
        final DungeonBuilder dungeonBuilder = DungeonBuilder.INSTANCE;
        try {
            final Dungeon dungeon = dungeonBuilder.buildDungeon(100);
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
            dungeonBuilder.buildDungeon(100);
            final long timeTaken = System.currentTimeMillis() - currentTime;
            assertTrue(timeTaken < 1000);
        } catch (final Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void testDungeonContainsEntrance() {
        final DungeonBuilder dungeonBuilder = DungeonBuilder.INSTANCE;
        try {
            final Dungeon dungeon = dungeonBuilder.buildDungeon(100);
            assertTrue(dungeon.getAllRooms().values().stream().anyMatch(Room::isEntrance));
        } catch (final Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void testDungeonContainsOneEntrance() {
        final DungeonBuilder dungeonBuilder = DungeonBuilder.INSTANCE;
        try {
            final Dungeon dungeon = dungeonBuilder.buildDungeon(100);
            assertEquals(1, dungeon.getAllRooms().values().stream().filter(Room::isEntrance).count());
        } catch (final Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void testDungeonContainsExit() {
        final DungeonBuilder dungeonBuilder = DungeonBuilder.INSTANCE;
        try {
            final Dungeon dungeon = dungeonBuilder.buildDungeon(100);
            assertTrue(dungeon.getAllRooms().values().stream().anyMatch(Room::isExit));
        } catch (final Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void testDungeonContainsOneExit() {
        final DungeonBuilder dungeonBuilder = DungeonBuilder.INSTANCE;
        try {
            final Dungeon dungeon = dungeonBuilder.buildDungeon(100);
            assertEquals(1, dungeon.getAllRooms().values().stream().filter(Room::isExit).count());
        } catch (final Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void testDungeonContainsOneEachPillar() {
        final DungeonBuilder dungeonBuilder = DungeonBuilder.INSTANCE;
        try {
            final Dungeon dungeon = dungeonBuilder.buildDungeon(100);

            final Function<String, Boolean> containsPillar = (name) -> dungeon
                    .getAllRooms().values().stream()
                    .filter(room -> room.getItems().stream().anyMatch(item -> item.getName().equals(name)))
                    .count() == 1;


            assertTrue(containsPillar.apply("Abstraction"));
            assertTrue(containsPillar.apply("Encapsulation"));
            assertTrue(containsPillar.apply("Inheritance"));
            assertTrue(containsPillar.apply("Polymorphism"));
        } catch (final Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void testDungeonHasCorrectBranchingFactor() {
        final DungeonBuilder dungeonBuilder = DungeonBuilder.INSTANCE;
        try {
            final double overallBranching = IntStream.range(10, 100)
                    .mapToObj(dungeonBuilder::buildDungeon)
                    .map(this::getBranchingFactor)
                    .reduce(0.0, Double::sum) / 90.0;
            assertTrue(overallBranching > 0.5);
            System.out.printf("Overall Branching: %2.2f%%", overallBranching * 100);
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
     * average out to a certain number, currently 0.65, but I only make sure its above 0.3.
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

        return getBranchNumber(theDungeon.getCurrentRoom(), null, new ArrayList<>());
    }

    private int getBranchNumber(final Room theRoom, final Direction theEntryDirection, final ArrayList<Room> theVisitedRooms) {
        int count = 0;
        if (theVisitedRooms.contains(theRoom)) {
            return 0;
        }
        theVisitedRooms.add(theRoom);
        for (final Direction direction : Direction.values()) {
            if (direction == theEntryDirection) {
                continue;
            }
            if (theRoom.getDoor(direction) != null) {
                if (theEntryDirection != null && direction != theEntryDirection.opposite()) {
                    count++;
                }
                count += getBranchNumber(theRoom.getDoor(direction), direction.opposite(), theVisitedRooms);
            }
        }
        return count;
    }

    private int countNumberOfRooms(final Dungeon theDungeon) {
        return theDungeon.getAllRooms().size();
    }

}
