package model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DirectionTest {


    @Test
    public void testApplyToCoordinate() {
        final Coordinate coordinate = Coordinate.of(1, 2);
        final Coordinate coordinate2 = Direction.NORTH.applyToCoordinate(coordinate);
        assertEquals(1, coordinate2.getX());
        assertEquals(3, coordinate2.getY());
    }

    @Test
    public void testApplyToCoordinate2() {
        final Coordinate coordinate = Coordinate.of(1, 2);
        final Coordinate coordinate2 = Direction.SOUTH.applyToCoordinate(coordinate);
        assertEquals(1, coordinate2.getX());
        assertEquals(1, coordinate2.getY());
    }

    @Test
    public void testGetOpposite() {
        assertEquals(Direction.SOUTH, Direction.NORTH.opposite());
    }

    @Test
    public void testToString() {
        assertEquals("North", Direction.NORTH.toString());
        assertEquals("South", Direction.SOUTH.toString());
        assertEquals("East", Direction.EAST.toString());
        assertEquals("West", Direction.WEST.toString());
    }

}
