package view;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Objects;
import model.Direction;
import model.Item;
import model.Monster;
import model.Room;

import java.util.*;

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
        myFlavorText = theRoom.getMyFlavorText();
        myDoors = extractDoorDirections(theRoom);
        myItems = extractItems(theRoom.getItems());
        myMonsters = extractMonsters(theRoom.getMonster());
        myIsPit = theRoom.hasPit();
        myIsExit = theRoom.isExit();;
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

    private String[] extractDoorDirections(final Room theRoom) {
        final Direction[] doors = (theRoom.getDoors()).keySet().toArray(new Direction[0]);
        return Arrays.stream(doors)
                .map(direction -> direction.toString().substring(0, 1).toUpperCase()
                        + direction.toString().substring(1).toLowerCase())
                .toArray(String[]::new);
    }

    private String[] extractItems(ArrayList<Item> items) {
        List<String> itemNames = new ArrayList<>();
        for (Item item: items) {
            itemNames.add(item.getName());
        }
        return itemNames.toArray(new String[0]);
    }

    private String[] extractMonsters(Monster monster) {
        List<String> monsterNames = new ArrayList<>();
        if (monster != null) {
            monsterNames.add(monster.getName());
        }
        return monsterNames.toArray(new String[0]);
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

    private String[] removeNullValues(final String[] theObject) {
        return Arrays.stream(theObject).filter(Objects::nonNull).toArray(String[]::new);
    }

}
