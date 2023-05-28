package model;

import java.util.*;
import java.util.List;

public class Room {

    private static final double PIT_PROBABILITY = 0.1;
    private static final double MONSTER_PROBABILITY = 0.1;
    private static final double HEALTH_POTION_PROBABILITY = 0.1;
    private static final double VISION_POTION_PROBABILITY = 0.1;

    private String myFlavorText;
    private final ArrayList<Item> myItems;
    private Monster myMonster;
    private boolean myIsExit;
    private boolean myIsEntrance;
    private boolean myHasPit;
    private final Map<Direction, Room> myDoors;
    private final Random myRandom;

    Room() {
        myRandom = new Random();
        myDoors = new HashMap<>();
        myItems = generateItems();
        myMonster = generateMonster();
        myHasPit = myRandom.nextDouble() < PIT_PROBABILITY;
        myIsExit = false;
    }

    Room(final boolean theIsEntrance) {
        myRandom = new Random();
        myDoors = new HashMap<>();
        myItems = new ArrayList<>();
        myMonster = null;
        myHasPit = false;
        myIsEntrance = theIsEntrance;
        myIsExit = false;
    }

    private ArrayList<Item> generateItems() {
        final ArrayList<Item> items = new ArrayList<>();
        if (myRandom.nextDouble() < HEALTH_POTION_PROBABILITY) {
            items.add(new HealingPotion());
        }
        if (myRandom.nextDouble() < VISION_POTION_PROBABILITY) {
            items.add(new VisionPotion());
        }
        return items;
    }

    private Monster generateMonster() {
        if (myRandom.nextDouble() < MONSTER_PROBABILITY) {
            //TODO: make monster factory a singleton and add a method to randomly generate a monster.
            //return new MonsterFactory().createMonster("Ogre"); This is causing exceptions.
        }
        return null;
    }

    public Monster getMyMonster() {
        return myMonster;
    }
    public ArrayList<Item> getItems() {
        return myItems;
    }
    public boolean isEntrance() {
        return myIsEntrance;
    }
    public boolean isExit() {
        return myIsExit;
    }
    public boolean hasPit() {
        return myHasPit;
    }
    public Room getDoor(final Direction theDirection) {
        return myDoors.get(theDirection);
    }
    public Map<Direction, Room> getDoors() {
        return myDoors;
    }
    void addDoor(final Direction theDirection, final Room theRoom) {
        myDoors.put(theDirection, theRoom);
    }
    void setExit() {
        myItems.clear();
        myMonster = null;
        myHasPit = false;
        myIsExit = true;
    }
    public String getDirections() {
        return getDirections(null);
    }

    private String getDirections(final Direction[] theDirections) {
        final StringBuilder sb = new StringBuilder();
        if (theDirections != null) {
            for (final Direction direction : theDirections) {
                sb.append(direction.getDirChar());
            }
        }

        sb.append('\n');
        myDoors.forEach((final Direction direction, final Room room) -> {
            if (room != null && (theDirections == null || direction != Direction.opposite(theDirections[theDirections.length-1]))) {
                final Direction[] newDirections;
                if (theDirections != null) {
                    newDirections = new Direction[theDirections.length + 1];
                    System.arraycopy(theDirections, 0, newDirections, 0, theDirections.length);
                } else {
                    newDirections = new Direction[1];
                }

                newDirections[newDirections.length - 1] = direction;

                sb.append(room.getDirections(newDirections));
            }
        });

        return sb.toString();
    }

}
