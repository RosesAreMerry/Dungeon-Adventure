package view;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Objects;

public class RoomData implements Serializable {
    private final String myFlavorText;
    private final String[] myDoors;
    private final String[] myItems;
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

    private String[] removeNullValues(final String[] theMonsters) {
        return Arrays.stream(theMonsters).filter(Objects::nonNull).toArray(String[]::new);
    }

}
