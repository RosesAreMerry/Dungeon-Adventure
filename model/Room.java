package model;

import java.util.*;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static model.Direction.*;

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

    /**
     * Makes a string representation of the room.
     * Example of a room containing the pillar of Polymorphism and doors on all sides:<br>
     * *-*<br>
     * |P|<br>
     * *-*<br>
     *
     * @return a string representation of the room
     * */
    @Override
    public String toString() {
        final Map<Direction, Character> doors = Stream.of(NORTH, SOUTH, EAST, WEST).filter(myDoors::containsKey)
                .collect(Collectors.toMap(dir -> dir, dir -> dir == NORTH || dir == SOUTH ? '-' : '|'));
        final StringBuilder sb = new StringBuilder();

        sb.append('*').append(doors.getOrDefault(NORTH, '*')).append('*').append('\n');

        sb.append(doors.getOrDefault(WEST, '*')).append(getRoomChar()).append(doors.getOrDefault(EAST, '*')).append('\n');

        sb.append('*').append(doors.getOrDefault(SOUTH, '*')).append('*').append('\n');

        return sb.toString();
    }

    /**
     * Returns the character to represent the room.
     * M - Multiple Items
     * X - Pit
     * i - Entrance (In)
     * O - Exit (Out)
     * V - Vision Potion
     * H - Healing Potion
     * <space> - Empty Room
     * A, E, I, P - Pillars
     *
     * @return the character to represent the room
     * */
    private char getRoomChar() {
        if (myItems.size() > 1) {
            return 'M';
        } else if (myHasPit) {
            return 'X';
        } else if (myIsEntrance) {
            return 'i';
        } else if (myIsExit) {
            return 'O';
        } else if (myItems.size() == 1) {
            final Item item = myItems.get(0);
            if (item instanceof VisionPotion) {
                return 'V';
            } else if (item instanceof HealingPotion) {
                return 'H';
            } else if (item instanceof final PillarOfOO thePillar) {
                return thePillar.getName().charAt(0);
            }
        }
        return ' ';
    }

}
