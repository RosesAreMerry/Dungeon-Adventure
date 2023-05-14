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