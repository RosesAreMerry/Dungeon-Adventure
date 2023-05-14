package view;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.*;

class AdventureViewTest extends ConsoleViewTestAbstract {

    private static final String MOCK_ROOM_DESCRIPTION = "Mock Room Description";
    private static final String[] MOCK_ROOM_DOORS = {"North", "South", "East", "West"};
    private static final String[] MOCK_ROOM_ITEMS = {"Item 1", "Item 2", "Item 3"};
    private static final String REGEX_FOR_RANDOM_ITEM_LOCATION = "(in a corner|on the floor|on a table|on a shelf|on a pedestal|in a chest|in a bag carried by a skeleton|laying in a pool of blood|in a pile of bones|hidden in a secret compartment)";
    AdventureView myAdventureView;

    @BeforeEach
    void setUp() {
        myAdventureView = new AdventureView(myCustomWriter, myCustomReader);
    }

    @Test
    void printRoomTestNull() {
        myAdventureView.printRoom(null, null, null);

        assertEquals("You are in a room.\n\n", myMockedOutput.toString());
    }

    @Test
    void printRoomTestDoorsNull() {
        final String[] theDoors = null;
        myAdventureView.printRoom(MOCK_ROOM_DESCRIPTION, theDoors, MOCK_ROOM_ITEMS);

        assertFalse(myMockedOutput.toString().lines().anyMatch(s -> s.contains("There is a door") | s.contains("There are doors")));
    }

    @Test
    void printRoomTestDoorsEmpty() {
        final String[] theDoors = {};
        myAdventureView.printRoom(MOCK_ROOM_DESCRIPTION, theDoors, MOCK_ROOM_ITEMS);

        assertFalse(myMockedOutput.toString().lines().anyMatch(s -> s.contains("There is a door") | s.contains("There are doors")));
    }

    @Test
    void printRoomTestDoors1() {
        final String[] theDoors = {"North"};
        myAdventureView.printRoom(MOCK_ROOM_DESCRIPTION, theDoors, MOCK_ROOM_ITEMS);

        assertTrue(myMockedOutput.toString().lines().anyMatch(s -> s.contains("There is a door to the North.")));
    }

    @Test
    void printRoomTestDoors2() {
        final String[] theDoors = {"North", "South"};
        myAdventureView.printRoom(MOCK_ROOM_DESCRIPTION, theDoors, MOCK_ROOM_ITEMS);

        assertTrue(myMockedOutput.toString().lines().anyMatch(s -> s.contains("There are doors to the North and South.")));
    }

    @Test
    void printRoomTestDoors4() {
        final String[] theDoors = {"North", "South", "East", "West"};
        myAdventureView.printRoom(MOCK_ROOM_DESCRIPTION, theDoors, MOCK_ROOM_ITEMS);

        assertTrue(myMockedOutput.toString().lines().anyMatch(s -> s.contains("There are doors to the North, South, East, and West.")));
    }

    @Test
    void printRoomTestItemsNull() {
        final String[] theItems = null;
        myAdventureView.printRoom(MOCK_ROOM_DESCRIPTION, MOCK_ROOM_DOORS, theItems);

        assertFalse(myMockedOutput.toString().lines().anyMatch(s -> s.contains("There is an Item")));
    }

    @Test

    void printRoomTestItemsEmpty() {
        final String[] theItems = {};
        myAdventureView.printRoom(MOCK_ROOM_DESCRIPTION, MOCK_ROOM_DOORS, theItems);

        assertFalse(myMockedOutput.toString().lines().anyMatch(s -> s.contains("There is an Item")));
    }

    @Test
    void printRoomTestItems1() {
        final String[] theItems = {"Item 1"};
        myAdventureView.printRoom(MOCK_ROOM_DESCRIPTION, MOCK_ROOM_DOORS, theItems);

        assertTrue(myMockedOutput.toString().lines().anyMatch(s -> s.contains("There is an Item 1")));
    }

    @Test
    void printRoomTestItems2() {
        final String[] theItems = {"Item 1", "Item 2"};
        myAdventureView.printRoom(MOCK_ROOM_DESCRIPTION, MOCK_ROOM_DOORS, theItems);

        assertTrue(myMockedOutput.toString().lines().anyMatch(s -> Pattern.matches("There is an Item 1 and an Item 2 " + REGEX_FOR_RANDOM_ITEM_LOCATION + "\\.", s)));
    }

    @Test
    void printRoomTestItems3() {
        final String[] theItems = {"Item 1", "Item 2", "Item 3"};
        myAdventureView.printRoom(MOCK_ROOM_DESCRIPTION, MOCK_ROOM_DOORS, theItems);

        assertTrue(myMockedOutput.toString().lines().anyMatch(s -> Pattern.matches("There is an Item 1, an Item 2, and an Item 3 " + REGEX_FOR_RANDOM_ITEM_LOCATION + "\\.", s)));
    }
}