package test.view;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import view.AdventureView;

import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.*;

class AdventureViewTest extends ConsoleViewTestAbstract {

    private static final String MOCK_ROOM_DESCRIPTION = "Mock Room Description";
    private static final String[] MOCK_ROOM_DOORS = {"North", "South", "East", "West"};
    private static final String[] MOCK_ROOM_ITEMS = {"Item 1", "Item 2", "Item 3"};

    private static final String regexForRandomItemLocation = "(in a corner|on the floor|on a table|on a shelf|on a pedestal|in a chest|in a bag carried by a skeleton|laying in a pool of blood|in a pile of bones|hidden in a secret compartment)";

    AdventureView av;

    @BeforeEach
    void setUp() {
        av = new AdventureView(myCustomWriter, myCustomReader);
    }

    @Test
    void printRoomTestNull() {
        av.printRoom(null, null, null);

        assertEquals("You are in a room.\n\n", myMockedOutput.toString());
    }

    @Test
    void printRoomTestDoorsNull() {
        String[] theDoors = null;
        av.printRoom(MOCK_ROOM_DESCRIPTION, theDoors, MOCK_ROOM_ITEMS);

        assertFalse(myMockedOutput.toString().lines().anyMatch(s -> s.contains("There is a door") | s.contains("There are doors")));
    }

    @Test
    void printRoomTestDoorsEmpty() {
        String[] theDoors = {};
        av.printRoom(MOCK_ROOM_DESCRIPTION, theDoors, MOCK_ROOM_ITEMS);

        assertFalse(myMockedOutput.toString().lines().anyMatch(s -> s.contains("There is a door") | s.contains("There are doors")));
    }

    @Test
    void printRoomTestDoors1() {
        String[] theDoors = {"North"};
        av.printRoom(MOCK_ROOM_DESCRIPTION, theDoors, MOCK_ROOM_ITEMS);

        assertTrue(myMockedOutput.toString().lines().anyMatch(s -> s.contains("There is a door to the North.")));
    }

    @Test
    void printRoomTestDoors2() {
        String[] theDoors = {"North", "South"};
        av.printRoom(MOCK_ROOM_DESCRIPTION, theDoors, MOCK_ROOM_ITEMS);

        assertTrue(myMockedOutput.toString().lines().anyMatch(s -> s.contains("There are doors to the North and South.")));
    }

    @Test
    void printRoomTestDoors4() {
        String[] theDoors = {"North", "South", "East", "West"};
        av.printRoom(MOCK_ROOM_DESCRIPTION, theDoors, MOCK_ROOM_ITEMS);

        assertTrue(myMockedOutput.toString().lines().anyMatch(s -> s.contains("There are doors to the North, South, East, and West.")));
    }

    @Test
    void printRoomTestItemsNull() {
        String[] theItems = null;
        av.printRoom(MOCK_ROOM_DESCRIPTION, MOCK_ROOM_DOORS, theItems);

        assertFalse(myMockedOutput.toString().lines().anyMatch(s -> s.contains("There is an Item")));
    }

    @Test

    void printRoomTestItemsEmpty() {
        String[] theItems = {};
        av.printRoom(MOCK_ROOM_DESCRIPTION, MOCK_ROOM_DOORS, theItems);

        assertFalse(myMockedOutput.toString().lines().anyMatch(s -> s.contains("There is an Item")));
    }

    @Test
    void printRoomTestItems1() {
        String[] theItems = {"Item 1"};
        av.printRoom(MOCK_ROOM_DESCRIPTION, MOCK_ROOM_DOORS, theItems);

        assertTrue(myMockedOutput.toString().lines().anyMatch(s -> s.contains("There is an Item 1")));
    }

    @Test
    void printRoomTestItems2() {
        String[] theItems = {"Item 1", "Item 2"};
        av.printRoom(MOCK_ROOM_DESCRIPTION, MOCK_ROOM_DOORS, theItems);

        assertTrue(myMockedOutput.toString().lines().anyMatch(s -> Pattern.matches("There is an Item 1 and an Item 2 " + regexForRandomItemLocation + "\\.", s)));
    }

    @Test
    void printRoomTestItems3() {
        String[] theItems = {"Item 1", "Item 2", "Item 3"};
        av.printRoom(MOCK_ROOM_DESCRIPTION, MOCK_ROOM_DOORS, theItems);

        assertTrue(myMockedOutput.toString().lines().anyMatch(s -> Pattern.matches("There is an Item 1, an Item 2, and an Item 3 " + regexForRandomItemLocation + "\\.", s)));
    }
}