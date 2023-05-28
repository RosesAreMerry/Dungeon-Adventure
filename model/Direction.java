package model;

public enum Direction {
    NORTH,
    EAST,
    SOUTH,
    WEST;

    public static Direction opposite(final Direction theDirection) {
        return switch (theDirection) {
            case NORTH -> SOUTH;
            case SOUTH -> NORTH;
            case WEST -> EAST;
            case EAST -> WEST;
        };
    }

    public Direction opposite() {
        return opposite(this);
    }

    public Coordinate applyToCoordinate(final Coordinate theCoordinate) {
        return switch (this) {
            case NORTH -> new Coordinate(theCoordinate.getX() + 1, theCoordinate.getY());
            case SOUTH -> new Coordinate(theCoordinate.getX() - 1, theCoordinate.getY());
            case EAST -> new Coordinate(theCoordinate.getX(), theCoordinate.getY() + 1);
            case WEST -> new Coordinate(theCoordinate.getX(), theCoordinate.getY() - 1);
        };
    }

    public char getDirChar() {
        return switch (this) {
            case NORTH -> 'N';
            case SOUTH -> 'S';
            case EAST -> 'E';
            case WEST -> 'W';
        };
    }
}
