package model;

/**
 * Enum representing cardinal directions.
 * */
public enum Direction {
    NORTH,
    EAST,
    SOUTH,
    WEST;


    /**
     * Returns the direction opposite to the one provided.
     *
     * @param theDirection the direction to get the opposite of
     *
     * @return the opposite direction
     * */
    public static Direction opposite(final Direction theDirection) {
        return switch (theDirection) {
            case NORTH -> SOUTH;
            case SOUTH -> NORTH;
            case WEST -> EAST;
            case EAST -> WEST;
        };
    }

    /**
     * Returns the opposite direction to this one.
     *
     * @return the opposite direction
     * */
    public Direction opposite() {
        return opposite(this);
    }

    /**
     * Apply a movement to a coordinate in the direction of this direction.
     *
     * @param theCoordinate the coordinate to apply the movement to
     *
     * @return the coordinate after the movement
     * */
    public Coordinate applyToCoordinate(final Coordinate theCoordinate) {
        return switch (this) {
            case NORTH -> Coordinate.of(theCoordinate.getX(), theCoordinate.getY() + 1);
            case SOUTH -> Coordinate.of(theCoordinate.getX(), theCoordinate.getY() - 1);
            case EAST -> Coordinate.of(theCoordinate.getX() + 1, theCoordinate.getY());
            case WEST -> Coordinate.of(theCoordinate.getX() - 1, theCoordinate.getY());
        };
    }

    @Override
    public String toString() {
        return switch (this) {
            case NORTH -> "North";
            case SOUTH -> "South";
            case EAST -> "East";
            case WEST -> "West";
        };
    }
}
