package model;

import java.io.Serial;
import java.util.Arrays;
import java.util.List;
import java.io.Serializable;
import java.util.Map;
import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Dungeon implements Serializable {

    private final Map<Coordinate, Room> myRooms;
    private Room myHeroLocation;
    @Serial
    private static final long serialVersionUID = -263907444889960995L;

    Dungeon(final Room theEntrance, final Map<Coordinate, Room> theRooms) {
        myHeroLocation = theEntrance;
        myRooms = theRooms;
    }
    public Room getCurrentRoom() {
        return myHeroLocation;
    }

    public void move(final Direction theDirection) {
        if (myHeroLocation.getDoor(theDirection) != null) {
            myHeroLocation = myHeroLocation.getDoor(theDirection);
        } else {
            throw new IllegalArgumentException("There is no door in that direction");
        }
    }

    public Map<String, Room> getNeighbors() {
        final Coordinate current = myRooms.entrySet().stream()
                .filter(entry -> entry.getValue().equals(myHeroLocation)).findFirst().get().getKey();
        return myRooms.keySet().stream().filter(key -> key.isNeighbor(current)).collect(Collectors.toMap(current::getDirection, myRooms::get));
    }

    /**
     * This method returns a collection of all the rooms in the dungeon.
     * It is used for testing purposes.
     *
     * @return a collection of all the rooms in the dungeon.
     * */
    Map<Coordinate, Room> getAllRooms() {
        return myRooms;
    }

    public void printDungeon() {
        final int minX = myRooms.keySet().stream().mapToInt(Coordinate::getX).min().getAsInt();
        final int maxX = myRooms.keySet().stream().mapToInt(Coordinate::getX).max().getAsInt();
        final int minY = myRooms.keySet().stream().mapToInt(Coordinate::getY).min().getAsInt();
        final int maxY = myRooms.keySet().stream().mapToInt(Coordinate::getY).max().getAsInt();

        final char[][] dungeon = new char[(maxY - minY + 1) * 2 + 2][(maxX - minX + 1) * 2 + 2];

        for (final char[] array : dungeon) {
            Arrays.fill(array, ' ');
        }

        for (int y = minY * 2; y <= maxY * 2; y += 2) {
            for (int x = minX * 2; x <= maxX * 2; x += 2) {
                final int roomX = x / 2;
                final int roomY = y / 2;
                final Coordinate current = Coordinate.of(roomX, roomY);
                if (myRooms.containsKey(current)) {
                    // blends the room string into the dungeon array
                    // room string is 3x3
                    final String roomString = myRooms.get(current).toString();
                    final String[] roomStringArray = roomString.split("\n");
                    // Invert the y axis, so that the dungeon is printed correctly
                    final int arrayY = dungeon.length - (y - minY * 2) - 3;
                    final int arrayX = x - minX * 2;
                    for (int i = 0; i < 3; i++) {
                        dungeon[arrayY + i][arrayX] = roomStringArray[i].charAt(0);
                        dungeon[arrayY + i][arrayX + 1] = roomStringArray[i].charAt(1);
                        dungeon[arrayY + i][arrayX + 2] = roomStringArray[i].charAt(2);
                    }
                }
            }
        }

        // converts the dungeon array into a string
        final StringBuilder sb = new StringBuilder();
        for (final char[] array : dungeon) {
            sb.append(array);
            sb.append("\n");
        }
        System.out.println(sb);
    }

}
