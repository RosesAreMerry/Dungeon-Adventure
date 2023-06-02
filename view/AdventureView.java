package view;
import java.io.IOException;
import java.util.Locale;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Supplier;

import static view.StringHelper.getList;

/**
 * Main view for the program that describes each room and will handle most choices for setup and navigating the dungeon.
 *
 * @author Rosemary
 * @version May 7th 2023
 * @see view.ConsoleView
 * */
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
    public AdventureView(final Consumer<String> theCustomWriter, final Supplier<String> theCustomReader) {
        super(theCustomWriter, theCustomReader);
    }

    /**
     * This view will show what is in the room, including doors, items, monsters, traps, etc.
     *
     * @param theRoom All the data required to print a room.
     *
     * @param theAdjacentRooms Provided if the player has used a vision potion. Provides data for adjacent rooms.
     * */
    public void printRoom(final RoomData theRoom, final Map<String, RoomData> theAdjacentRooms) {
        final StringBuilder sb = new StringBuilder();

        sb.append("----------------------------------------\n");

        sb.append(theRoom.getFlavor()).append("\n\n");

        buildList(sb,
                theRoom.getDoors(),
                "There is a door to the ",
                "There are doors to the ",
                ".",
                false);

        final String randomItemLocation = ' ' + RANDOM_ITEM_LOCATIONS[(int) (Math.random() * RANDOM_ITEM_LOCATIONS.length)] + '.';

        buildList(sb,
                theRoom.getItems(),
                "There is ",
                null,
                randomItemLocation,
                true
                );

        buildList(sb,
                theRoom.getMonsters(),
                "You see a creature in the room. It is ",
                "You see creatures in this room. There is ",
                "!",
                true);

        if (theRoom.isExit()) {
            sb.append("Your eyes focus on something strange, natural light! It's an exit to the dungeon!\n");
        }

        if (theRoom.isPit()) {
            sb.append("Something bothers you about the floor of this room...\n");
        }

        if (theAdjacentRooms != null && theAdjacentRooms.size() > 0) {

            sb.append("Your vision potion gives you the ability to see through the walls.\n");

            theAdjacentRooms.forEach((final String s, final RoomData rd) -> {
                sb.append("To the ").append(s.toUpperCase(Locale.ROOT)).append(", you see a room that contains the following:\n");
                conciseRoom(sb, rd);
            });
        }

        writeLine(sb.toString());
    }

    void conciseRoom(final StringBuilder theSB, final RoomData theRoom) {
        theSB.append("Doors: ");
        theSB.append(getList(theRoom.getDoors(), false));
        theSB.append('\n');

        theSB.append("Items: ");
        theSB.append(getList(theRoom.getItems(), false));
        theSB.append('\n');

        theSB.append("Monsters: ");
        theSB.append(getList(theRoom.getMonsters(), false));
        theSB.append('\n');

        theSB.append("Exit: ");
        theSB.append(theRoom.isExit());
        theSB.append('\n');

        theSB.append("Pit: ");
        theSB.append(theRoom.isPit());
        theSB.append('\n');
    }

    void buildList(final StringBuilder theSB,
                   final String[] theItems,
                   final String thePrefix,
                   final String thePrefixPlural,
                   final String thePostfix,
                   final boolean theUseIA) {
        if (theItems == null || theItems.length == 0) {
            return;
        }

        if (thePrefixPlural != null && !thePrefixPlural.equals("") && theItems.length > 1) {
            theSB.append(thePrefixPlural);
        } else {
            theSB.append(thePrefix);
        }

        theSB.append(getList(theItems, theUseIA));

        theSB.append(thePostfix).append("\n");
    }

}
