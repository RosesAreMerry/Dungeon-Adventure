package view;

import model.Item;
import model.Room;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class RoomData {
    private final String myFlavorText;
    private final String[] myDoors;
    private String[] myItems;
    private String[] myMonsters;

    private final boolean myIsPit;

    private final boolean myIsExit;

    public RoomData(final String theFlavorText,
                    final String[] theDoors,
                    final String[] theItems,
                    final String[] theMonsters,
                    final boolean theIsPit,
                    final boolean theIsExit) {
        myFlavorText = theFlavorText;
        myDoors = theDoors;
        myItems = theItems;
        myMonsters = theMonsters;
        myIsPit = theIsPit;
        myIsExit = theIsExit;
    }

    public RoomData(final String[] theDoors,
                    final String[] theItems,
                    final String[] theMonsters,
                    final boolean theIsPit,
                    final boolean theIsExit) {
        this("You are in a room.", theDoors, theItems, theMonsters, theIsPit, theIsExit);
    }


    public RoomData(final Room theRoom) {
        // TODO: Implement this constructor
        throw new UnsupportedOperationException("Method not yet implemented");
    }

    public String[] getDoors() {
        return myDoors;
    }

    public String[] getItems() {
        return myItems;
    }

    public String getFlavor() {
        return myFlavorText;
    }

    public String[] getMonsters() {
        return myMonsters;
    }

    public void setMonsters(final String[] theMonsters) {
        myMonsters = theMonsters;
    }

    public void setItems(final String[] theItems) {
        myItems = theItems;
    }

    public boolean isPit() {
        return myIsPit;
    }

    public boolean isExit() {
        return myIsExit;
    }

    public void removeMonsterFromRoom(final String theMonster) {
        final String[] monsters = getMonsters();
        for (int i = 0; i < monsters.length; i++) {
            if (monsters[i].equals(theMonster)) {
                monsters[i] = null;
                setMonsters(removeNullValues(monsters));
            }
        }
    }

    public void removeItemFromRoom(final ArrayList<Item> theItems, String[] itemsArray) {
        for (final Item item: theItems) {
            final String itemToRemove = item.getName();
            for (int i = 0; i < itemsArray.length; i++) {
                if (itemsArray[i] != null && itemsArray[i].equals(itemToRemove)) {
                    itemsArray[i] = null;
                    setItems(removeNullValues(itemsArray));
                }
            }
        }
    }

    private String[] removeNullValues(final String[] theObject) {
        return Arrays.stream(theObject).filter(Objects::nonNull).toArray(String[]::new);
    }
}
