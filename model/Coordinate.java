package model;

import java.io.*;
import java.util.HashSet;
import java.util.Set;

/**
 * This class represents a coordinate on the map. It is used to keep track of
 * where rooms are in the dungeon. It is immutable, and each coordinate is
 * unique. This means that there cannot be duplicate coordinates in a map.
 *
 * @author Rosemary Roach
 * */
class Coordinate implements Serializable {
    private final int myX;
    private final int myY;
    private static final HashSet<Coordinate> COORDINATES = new HashSet<>();
    @Serial
    private static final long serialVersionUID = -5760971247759619498L;

    private Coordinate (final int theX, final int theY) {
        myX = theX;
        myY = theY;
    }

    /**
     * This is the way to get a Coordinate object. It will return an existing Coordinate
     * object if it already exists, otherwise it will create a new one. Because of this,
     * Coordinate objects are basically singletons for each unique coordinate. This is
     * useful because it means that there cannot be duplicate coordinates in a map.
     *
     * @param theX the x coordinate
     * @param theY the y coordinate
     *
     * @return a Coordinate object
     * */
    public static Coordinate of(final int theX, final int theY) {
        if (COORDINATES.stream().anyMatch(c -> c.getX() == theX && c.getY() == theY)) {
            return COORDINATES.stream().filter(c -> c.getX() == theX && c.getY() == theY).findFirst().get();
        }

        final Coordinate newCoordinate = new Coordinate(theX, theY);

        COORDINATES.add(newCoordinate);

        return newCoordinate;
    }

    public int getX() {
        return myX;
    }

    public int getY() {
        return myY;
    }

    /**
     * Returns whether the given coordinate is adjacent to this one, meaning that
     * it is one space away in any cardinal direction.
     *
     * @param theCoordinate the coordinate to check
     *
     * @return whether the given coordinate is adjacent to this one
     * */
    public boolean isAdjacent(final Coordinate theCoordinate) {
        return Math.abs(getX() - theCoordinate.getX()) + Math.abs(getY() - theCoordinate.getY()) == 1;
    }

    /**
     * Returns whether the given coordinate is a neighbor to this one, meaning that
     * it is one space away in any cardinal or diagonal direction.
     *
     * @param theCoordinate the coordinate to check
     *
     * @return whether the given coordinate is a neighbor to this one
     * */
    public boolean isNeighbor(final Coordinate theCoordinate) {
        final int xDiff = Math.abs(getX() - theCoordinate.getX());
        final int yDiff = Math.abs(getY() - theCoordinate.getY());
        return xDiff <= 1 && yDiff <= 1 && xDiff + yDiff != 0;
    }

    /**
     * Get the cardinal Direction from this coordinate to the given coordinate.
     * Only works if the given coordinate is adjacent to this one.
     *
     * @param theOther the coordinate to get the direction to
     *
     * @return the direction from this coordinate to the given coordinate
     *
     * @throws IllegalArgumentException if the given coordinate is not adjacent to this one
     * */
    public Direction getDirection(final Coordinate theOther) {
        if (isAdjacent(theOther)) {
            if (theOther.getY() > getY()) {
                return Direction.NORTH;
            } else if (theOther.getY() < getY()) {
                return Direction.SOUTH;
            } else if (theOther.getX() > getX()) {
                return Direction.EAST;
            } else {
                return Direction.WEST;
            }
        } else {
            throw new IllegalArgumentException("The coordinates are not adjacent");
        }
    }

    /**
     * Get a string representation of the direction from this coordinate to the given coordinate.
     *
     * @param theOther the coordinate to get the direction to
     *
     * @return a string representation of the direction from this coordinate to the given coordinate
     * */
    public String getDirectionString(final Coordinate theOther) {
        final StringBuilder sb = new StringBuilder();

        if (theOther.getY() > getY()) {
            sb.append("North");
        } else if (theOther.getY() < getY()) {
            sb.append("South");
        }

        if (theOther.getY() != getY() && theOther.getX() != getX()) {
            sb.append(" ");
        }

        if (theOther.getX() > getX()) {
            sb.append("East");
        } else if (theOther.getX() < getX()) {
            sb.append("West");
        }
        return sb.toString();
    }

    /**
     * Load the given set of coordinates into the Coordinate class. This is used
     * to reload the coordinates from a file during deserialization.
     *
     * @param theCoordinates the set of coordinates to load
     * */
    public static void loadWith(final Set<Coordinate> theCoordinates) {
    	COORDINATES.clear();
        COORDINATES.addAll(theCoordinates);
    }

}