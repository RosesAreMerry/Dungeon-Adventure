package view;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.*;

class AdventureViewTest extends ConsoleViewTestAbstract {
    private static final RoomData MOCK_ROOM_MULTIPLE = new RoomData(
            "This is a test Room, how did you get here?",
            new String[]{"North", "South", "East"},
            new String[]{"Potion", "Sword", "Apple", "Shield"},
            new String[]{"Ogre", "Goblin"},
            true,
            true
    );

    private static final RoomData MOCK_ROOM_SINGLE = new RoomData(
            "This is a test Room, how did you get here?",
            new String[]{"East"},
            new String[]{"Shield"},
            new String[]{"Ogre"},
            true,
            true
    );

    AdventureView av;

    @BeforeEach
    void setUp() {
        av = new AdventureView(myCustomWriter, myCustomReader);
    }

    @Test
    void testPrintRoomMultiple() {
        av.printRoom(MOCK_ROOM_MULTIPLE, null);

        assertTrue(Pattern.matches("""
            This is a test Room, how did you get here\\?

            There are doors to the North, South, and East\\.
            There is a Potion, a Sword, an Apple, and a Shield (in a corner|on the floor|on a table|on a shelf|on a pedestal|in a chest|in a bag carried by a skeleton|laying in a pool of blood|in a pile of bones|hidden in a secret compartment)\\.
            You see creatures in this room\\. There is an Ogre and a Goblin!
            Your eyes focus on something strange, natural light! It's an exit to the dungeon!
            Something bothers you about the floor of this room\\.\\.\\.

            """, myMockedOutput.toString()));
    }

    @Test
    void testPrintRoomSingle() {
        av.printRoom(MOCK_ROOM_SINGLE, null);

        assertTrue(Pattern.matches("""
            This is a test Room, how did you get here\\?

            There is a door to the East\\.
            There is a Shield (in a corner|on the floor|on a table|on a shelf|on a pedestal|in a chest|in a bag carried by a skeleton|laying in a pool of blood|in a pile of bones|hidden in a secret compartment)\\.
            You see a creature in the room\\. It is an Ogre!
            Your eyes focus on something strange, natural light! It's an exit to the dungeon!
            Something bothers you about the floor of this room\\.\\.\\.

            """, myMockedOutput.toString()));
    }

    @Test
    void testPrintRoomPit() {
        av.printRoom(new RoomData(null, null, null, true, false), null);

        assertEquals("""
                You are in a room.

                Something bothers you about the floor of this room...

                """, myMockedOutput.toString());
    }

    @Test
    void testPrintRoomExit() {
        av.printRoom(new RoomData(null, null, null, false, true), null);

        assertEquals("""
                You are in a room.

                Your eyes focus on something strange, natural light! It's an exit to the dungeon!

                """, myMockedOutput.toString());
    }

    @Test
    void testListMulti() {
        final StringBuilder sb = new StringBuilder();
        av.buildList(sb, new String[]{"A", "B", "C"}, "Prefix: ", "Prefix Plural: ", " Postfix!", true);

        assertEquals("Prefix Plural: an A, a B, and a C Postfix!\n", sb.toString());
    }

    @Test
    void testListSingle() {
        final StringBuilder sb = new StringBuilder();
        av.buildList(sb, new String[]{"A"}, "Prefix: ", "Prefix Plural: ", " Postfix!", true);

        assertEquals("Prefix: an A Postfix!\n", sb.toString());
    }

    @Test
    void testListNoIA() {
        final StringBuilder sb = new StringBuilder();
        av.buildList(sb, new String[]{"A", "B", "C"}, "Prefix: ", "Prefix Plural: ", " Postfix!", false);

        assertEquals("Prefix Plural: A, B, and C Postfix!\n", sb.toString());
    }

    @Test
    void testConciseRoomMulti() {
        final StringBuilder sb = new StringBuilder();
        av.conciseRoom(sb, MOCK_ROOM_MULTIPLE);

        assertEquals("""
                Doors: North, South, and East
                Items: Potion, Sword, Apple, and Shield
                Monsters: Ogre and Goblin
                Exit: true
                Pit: true
                """, sb.toString());
    }

    @Test
    void testConciseRoomSingle() {
        final StringBuilder sb = new StringBuilder();
        av.conciseRoom(sb, MOCK_ROOM_SINGLE);

        assertEquals("""
                Doors: East
                Items: Shield
                Monsters: Ogre
                Exit: true
                Pit: true
                """, sb.toString());
    }

}