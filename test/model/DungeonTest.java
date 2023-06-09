package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class DungeonTest {

    // Basically just tests the toString method. Dungeon structure is tested in DungeonBuilderTest
    // and the individual rooms are tested in RoomTest.

    private Dungeon myDungeon;
    private Room myEntrance;

    @BeforeEach
    void setUp() {
        Map<Coordinate, Room> theRooms = new HashMap<>();
        myEntrance = new Room(true);
        Room roomN = new Room(0.0, 0.0);
        Room roomS = new Room(0.0, 0.0);
        Room roomE = new Room(0.0, 0.0);
        Room roomW = new Room(0.0, 0.0);
        Room roomNE = new Room(0.0, 0.0);

        theRooms.put(Coordinate.of(0, 0), myEntrance);
        theRooms.put(Coordinate.of(0, 1), roomN);
        theRooms.put(Coordinate.of(0, -1), roomS);
        theRooms.put(Coordinate.of(1, 0), roomE);
        theRooms.put(Coordinate.of(-1, 0), roomW);
        theRooms.put(Coordinate.of(1, 1), roomNE);

        myEntrance.addDoor(Direction.NORTH, roomN);
        roomN.addDoor(Direction.SOUTH, myEntrance);

        myEntrance.addDoor(Direction.SOUTH, roomS);
        roomS.addDoor(Direction.NORTH, myEntrance);

        myEntrance.addDoor(Direction.EAST, roomE);
        roomE.addDoor(Direction.WEST, myEntrance);

        myEntrance.addDoor(Direction.WEST, roomW);
        roomW.addDoor(Direction.EAST, myEntrance);

        roomN.addDoor(Direction.EAST, roomNE);
        roomNE.addDoor(Direction.WEST, roomN);

        myDungeon = new Dungeon(myEntrance, theRooms);
    }

    @Test
    void testEmptyDungeon() {
        assertEquals("""
                  *****
                  * | *
                ***-***
                * |■| *
                ***-***
                  * * \s
                  *** \s
                """, myDungeon.toString());
    }

    @Test
    void testDungeonMove() {
        myDungeon.move(Direction.NORTH);
        assertEquals("""
                  *****
                  *■| *
                ***-***
                * |i| *
                ***-***
                  * * \s
                  *** \s
                """, myDungeon.toString());
    }

    @Test
    void testMoveFail() {
        myDungeon.move(Direction.EAST);
        assertThrows(IllegalArgumentException.class, () -> myDungeon.move(Direction.EAST));
    }

    @Test
    void testRoomStringCorrect() {
        myDungeon.getCurrentRoom().getDoors().get(Direction.NORTH).setExit();

        assertEquals("""
                  *****
                  *O| *
                ***-***
                * |■| *
                ***-***
                  * * \s
                  *** \s
                """, myDungeon.toString());
    }

    @Test
    void testGetNeighbors() {
        Map<String, Room> neighbors = myDungeon.getNeighbors();
        assertEquals(5, neighbors.size());
        assertEquals(myEntrance.getDoor(Direction.NORTH), neighbors.get("North"));
        assertEquals(myEntrance.getDoor(Direction.SOUTH), neighbors.get("South"));
        assertEquals(myEntrance.getDoor(Direction.EAST), neighbors.get("East"));
        assertEquals(myEntrance.getDoor(Direction.WEST), neighbors.get("West"));
        assertEquals(myEntrance.getDoor(Direction.NORTH).getDoor(Direction.EAST), neighbors.get("North East"));
    }



}
