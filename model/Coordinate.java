package model;

import java.io.Serial;
import java.io.Serializable;
import java.util.HashSet;

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

    public boolean isAdjacent(final Coordinate theCoordinate) {
        return Math.abs(getX() - theCoordinate.getX()) + Math.abs(getY() - theCoordinate.getY()) == 1;
    }

    public boolean isNeighbor(final Coordinate theCoordinate) {
        final int xDiff = Math.abs(getX() - theCoordinate.getX());
        final int yDiff = Math.abs(getY() - theCoordinate.getY());
        return xDiff <= 1 && yDiff <= 1 && xDiff + yDiff != 0;
    }

    public String getDirection(final Coordinate theOther) {
        final StringBuilder sb = new StringBuilder();

        if (theOther.getY() > getY()) {
            sb.append("North");
        } else if (theOther.getY() < getY()) {
            sb.append("South");
        } else if (theOther.getX() != getX()) {
            sb.append(" ");
        }

        if (theOther.getX() > getX()) {
            sb.append("East");
        } else if (theOther.getX() < getX()) {
            sb.append("West");
        }
        return sb.toString();
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj == this) {
            return true;
        }

        if (!(obj instanceof final Coordinate theCoordinate)) {
            return false;
        }
        return getX() == theCoordinate.getX() && getY() == theCoordinate.getY();
    }
}