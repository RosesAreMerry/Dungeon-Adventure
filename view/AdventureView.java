package view;

import java.util.function.Consumer;
import java.util.function.Supplier;

import static view.StringHelper.getIA;
import static view.StringHelper.getList;

public class AdventureView extends ConsoleView {

    private static final String[] RANDOM_ITEM_LOCATIONS = {
            "in a corner",
            "on the floor",
            "on a table",
            "on a shelf",
            "on a pedestal",
            "in a chest",
            "in a bag carried by a skeleton",
            "laying in a pool of blood",
            "in a pile of bones",
            "hidden in a secret compartment"
    };

    public AdventureView() {
        super();
    }

    /**
     * A constructor that allows for custom input and output.
     * This is useful for testing.
     *
     * @param theCustomWriter A custom output method.
     * @param theCustomReader A custom input method.
     * */
    public AdventureView(Consumer<String> theCustomWriter, Supplier<String> theCustomReader) {
        super(theCustomWriter, theCustomReader);
    }

    /**
     * This view will show what is in the room, including doors, items, monsters, traps, etc.
     *
     * @param theRoomFlavor The flavor text for the room. This is the description of the room that does not communicate game information.
     *                      It is meant to be atmospheric.
     *                      Example: "You enter a dark room. The smell of rotting flesh fills your nostrils."
     *
     * @param theDoors      An array of Strings that represent the doors in the room.
     *                      The doors are in the order: North, East, South, West.
     *
     * @param theItems      An array of Strings that represent the items in the room.
     * */
    public void printRoom(final String theRoomFlavor, final String[] theDoors, final String[] theItems) {
        StringBuilder sb = new StringBuilder();


        if (theRoomFlavor == null || theRoomFlavor.isEmpty()) {
            sb.append("You are in a room.\n");
        } else {
            sb.append(theRoomFlavor).append("\n");
        }

        addDoors(sb, theDoors);
        addItems(sb, theItems);

        writeLine(sb.toString());
    }

    private void addDoors(StringBuilder theSB, String[] theDoors) {
        if (theDoors == null || theDoors.length == 0) {
            return;
        }

        if (theDoors.length == 1) {
            theSB.append("There is a door to the ");
        } else {
            theSB.append("There are doors to the ");
        }

        theSB.append(getList(theDoors, false));

        theSB.append(".\n");
    }

    private void addItems(StringBuilder theSB, String[] theItems) {
        if (theItems == null || theItems.length == 0) {
            return;
        }

        String randomItemLocation = RANDOM_ITEM_LOCATIONS[(int) (Math.random() * RANDOM_ITEM_LOCATIONS.length)];

        theSB.append("There is ");

        theSB.append(getList(theItems, true));

        theSB.append(' ').append(randomItemLocation).append(".\n");
    }

}
