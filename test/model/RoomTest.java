//package model;
//
//import org.junit.jupiter.api.Test;
//
//import java.util.LinkedList;
//import java.util.List;
//
//import static model.Direction.*;
//import static org.junit.jupiter.api.Assertions.*;
//
//public class RoomTest {
//
//    // 1101 Monster
//    // 0011 Both Potions
//    // 1110 Pit
//
//    private static final Double UNDER = 0.09;
//    private static final Double OVER = 0.1;
//
//    @Test
//    void testMonsterGen() {
//        final RandomMock rm = new RandomMock();
//        rm.setMockDoubleValues(new LinkedList<>(List.of(OVER, OVER, UNDER, OVER)));
//        final Room room = new Room(rm);
//        assertEquals("***\n* *\n***\n", room.toString());
//    }
//
//    @Test
//    void testHealthPotionGen() {
//        final RandomMock rm = new RandomMock();
//        rm.setMockDoubleValues(new LinkedList<>(List.of(UNDER, OVER, OVER, OVER)));
//        final Room room = new Room(rm);
//        assertEquals("***\n*H*\n***\n", room.toString());
//    }
//
//    @Test
//    void testVisionPotionGen() {
//        final RandomMock rm = new RandomMock();
//        rm.setMockDoubleValues(new LinkedList<>(List.of(OVER, UNDER, OVER, OVER)));
//        final Room room = new Room(rm);
//        assertEquals("***\n*V*\n***\n", room.toString());
//    }
//    @Test
//    void testBothPotionGen() {
//        final RandomMock rm = new RandomMock();
//        rm.setMockDoubleValues(new LinkedList<>(List.of(UNDER, UNDER, OVER, OVER)));
//        final Room room = new Room(rm);
//        assertEquals("***\n*M*\n***\n", room.toString());
//    }
//
//
//
//    @Test
//    void testPitGen() {
//        final RandomMock rm = new RandomMock();
//        rm.setMockDoubleValues(new LinkedList<>(List.of(OVER, OVER, OVER, UNDER)));
//        final Room room = new Room(rm);
//        assertEquals("***\n*X*\n***\n", room.toString());
//    }
//
//    @Test
//    void testCorrectToStringWithPillar() {
//        final RandomMock rm = new RandomMock();
//        rm.setMockDoubleValues(new LinkedList<>(List.of(OVER, OVER, OVER, OVER)));
//        final Room room = new Room(rm);
//        room.getItems().add(new PillarOfOO("Polymorphism"));
//        assertEquals("***\n*P*\n***\n", room.toString());
//    }
//
//    @Test
//    void testCorrectToStringWithExit() {
//        final Room room = new Room();
//        room.setExit();
//        assertEquals("***\n*O*\n***\n", room.toString());
//    }
//
//    @Test
//    void testCorrectToStringWithEntrance() {
//        final Room room = new Room(true);
//        assertEquals("***\n*i*\n***\n", room.toString());
//    }
//
//    @Test
//    void testCorrectToStringWithDoors() {
//        final RandomMock rm = new RandomMock();
//        rm.setMockDoubleValue(OVER);
//        final Room room = new Room(rm);
//        room.addDoor(NORTH, room);
//        room.addDoor(EAST, room);
//        room.addDoor(SOUTH, room);
//        room.addDoor(WEST, room);
//        assertEquals("*-*\n| |\n*-*\n", room.toString());
//    }
//
//    @Test
//    void testCorrectToStringDoorDirection() {
//        final RandomMock rm = new RandomMock();
//        rm.setMockDoubleValue(OVER);
//        final Room room = new Room(rm);
//        room.addDoor(NORTH, room);
//        room.addDoor(EAST, room);
//        assertEquals("*-*\n* |\n***\n", room.toString());
//    }
//
//    @Test
//    void testExitIsEmpty() {
//        final RandomMock rm = new RandomMock();
//        rm.setMockDoubleValue(UNDER);
//        final Room room = new Room(rm);
//        room.setExit();
//        roomIsEmpty(room);
//        assertFalse(room.isEntrance());
//    }
//
//    @Test
//    void testEntranceIsEmpty() {
//        final Room room = new Room(true);
//        roomIsEmpty(room);
//        assertFalse(room.isExit());
//    }
//
//    void roomIsEmpty(final Room theRoom) {
//        assertEquals(0, theRoom.getItems().size());
//        assertNull(theRoom.getMonster());
//        assertFalse(theRoom.hasPit());
//    }
//
//}
