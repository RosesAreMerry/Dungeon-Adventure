package view;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Objects;
import model.Direction;
import model.Item;
import model.Monster;
import model.Room;

import java.util.Arrays;
import java.util.Objects;

public class RoomData implements Serializable {
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
        this(theRoom.getFlavorText(),
                theRoom.getDoors().keySet().stream().sorted().map(Objects::toString).toArray(String[]::new),
                theRoom.getItems().stream().map(Item::getName).toArray(String[]::new),
                theRoom.getMonster() == null ? new String[0] : new String[]{theRoom.getMonster().getName()},
                theRoom.hasPit(),
                theRoom.isExit());
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

    private String[] removeNullValues(final String[] theObject) {
        return Arrays.stream(theObject).filter(Objects::nonNull).toArray(String[]::new);
    }

}
