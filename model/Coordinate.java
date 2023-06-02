package model;

class Coordinate {
    private final int myX;
    private final int myY;

    public Coordinate (int theX, int theY) {
        myX = theX;
        myY = theY;
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
            sb.append("North ");
        } else if (theOther.getY() < getY()) {
            sb.append("South ");
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