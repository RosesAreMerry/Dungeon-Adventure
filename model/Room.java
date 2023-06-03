package model;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static model.Direction.*;
import static model.Direction.SOUTH;

public class Room implements Serializable {
    private static final double PIT_PROBABILITY = 0.1;
    private static final double MONSTER_PROBABILITY = 0.1;
    private static final double HEALTH_POTION_PROBABILITY = 0.1;
    private static final double VISION_POTION_PROBABILITY = 0.1;
    private static final String[] FLAVOR_TEXTS_EMPTY = new String[] {
            "This room is dark and damp.",
            "As you open the door, a musty scent hits your nose.",
            "You hear a faint dripping sound.",
            "A burst of humid air hits you as you open the door.",
            "You hear a faint scurrying sound.",
            "You hear a faint hissing sound.",
            "You hear a faint scratching sound.",
            "A creaking sound comes from the ceiling.",
            "You hear a distant scream.",
            "Water is dripping from the ceiling.",
            "Dust covers every surface of this abandoned room.",
            "A faint breeze blows through the room.",
    };

    private static final String[] FLAVOR_TEXTS_MONSTER = new String[] {
            "A monster is in the room!",
            "As soon as you enter this room, you realize that you are not alone.",
            "A monster is lurking in the shadows.",
            "As you search the dark corners of this room, glowing eyes look back at you.",
            "You feel a presence in the room.",
            "You hear a faint growling sound.",
            "As you enter, a chill runs down your spine."
    };

    private static final String[] FLAVOR_TEXTS_PIT = new String[] {
            "As you enter, you hear a slight click.",
            "You hear a faint rumbling sound.",
            "As you enter, you trip over a loose stone.",
            "As you enter, you trip over a nearly invisible wire.",
            "You hear a faint creaking sound.",
            "Slightly too late, you realize that the floor is not solid.",
            "There is something strange about the floor here.",
    };

    private static final String[] FLAVOR_TEXTS_ITEMS = new String[]{
            "This room is piled high with random nick nacks.",
            "Dust covers almost every surface of this abandoned room.",
            "Piles of bones litter the floor.",
            "The contents of this room are scattered across the floor.",
            "This room seems unaffected by the ravages of time, you must be the first to enter in a long time.",
            "This room is filled with old furniture.",
            "This room is filled with old paintings.",
            "This room is filled with old books.",
    };
    @Serial
    private static final long serialVersionUID = -8969980202667648723L;

    private String myFlavorText;
    private final ArrayList<Item> myItems;
    private Monster myMonster;
    private boolean myIsExit;
    private boolean myIsEntrance;
    private boolean myHasPit;
    private final Map<Direction, Room> myDoors;
    private final Random myRandom;

    Room() {
        this(new Random());
    }
    Room(final Random theRandom) {
        myRandom = theRandom;
        myDoors = new HashMap<>();
        myItems = generateItems();
        myMonster = generateMonster();
        myHasPit = myRandom.nextDouble() < PIT_PROBABILITY;
        myIsExit = false;
        myFlavorText = generateFlavorText();
    }

    Room(final boolean theIsEntrance) {
        myRandom = new Random();
        myDoors = new HashMap<>();
        myItems = new ArrayList<>();
        myMonster = null;
        myHasPit = false;
        myIsEntrance = theIsEntrance;
        myFlavorText = generateFlavorText();
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
            return new MonsterFactory().createMonsterRandom();
        }
        return null;
    }

    private String generateFlavorText() {
        if (myIsEntrance) {
            return "The door to the dungeon is closed. You are trapped in the dark.";
        }
        if (myIsExit) {
            return "You see something strange and welcome to your dark adjusted eyes, natural light! You have found the exit!";
        }
        if (myHasPit) {
            return FLAVOR_TEXTS_PIT[myRandom.nextInt(FLAVOR_TEXTS_PIT.length)];
        }
        if (myMonster != null) {
            return FLAVOR_TEXTS_MONSTER[myRandom.nextInt(FLAVOR_TEXTS_MONSTER.length)];
        }
        if (myItems.size() > 0) {
            return FLAVOR_TEXTS_ITEMS[myRandom.nextInt(FLAVOR_TEXTS_ITEMS.length)];
        }
        return FLAVOR_TEXTS_EMPTY[myRandom.nextInt(FLAVOR_TEXTS_EMPTY.length)];
    }

    public Monster getMonster() {
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
    public String getFlavorText() {
        return myFlavorText;
    }
    public void killMonster() {
        myMonster = null;
        myFlavorText = generateFlavorText();
    }
    public void removePit() {
        myHasPit = false;
    }
    void addDoor(final Direction theDirection, final Room theRoom) {
        myDoors.put(theDirection, theRoom);
    }
    public String getMyFlavorText() {
        return myFlavorText;
    }

    void setExit() {
        myItems.clear();
        myMonster = null;
        myHasPit = false;
        myIsExit = true;
        myFlavorText = generateFlavorText();
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
