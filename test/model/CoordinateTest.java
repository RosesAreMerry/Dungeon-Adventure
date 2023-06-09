package model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CoordinateTest {

    @Test
    public void testCoordinate() {
        final Coordinate coordinate = Coordinate.of(1, 2);
        assertEquals(1, coordinate.getX());
        assertEquals(2, coordinate.getY());
    }

    @Test
    public void testSameCoordinate() {
        final Coordinate coordinate1 = Coordinate.of(1, 2);
        final Coordinate coordinate2 = Coordinate.of(1, 2);
        assertEquals(coordinate1, coordinate2);
    }

    @Test
    public void testAdjacentCoordinates() {
        final Coordinate coordinate1 = Coordinate.of(1, 2);
        final Coordinate coordinate2 = Coordinate.of(1, 3);
        assertTrue(coordinate1.isAdjacent(coordinate2));
    }

    @Test
    public void testNeighborCoordinates() {
        final Coordinate coordinate1 = Coordinate.of(1, 2);
        final Coordinate coordinate2 = Coordinate.of(2, 3);
        assertTrue(coordinate1.isNeighbor(coordinate2));
    }

    @Test
    public void testGetDirection() {
        final Coordinate coordinate1 = Coordinate.of(1, 2);
        final Coordinate coordinate2 = Coordinate.of(1, 3);
        assertEquals(Direction.NORTH, coordinate1.getDirection(coordinate2));
    }

    @Test
    public void testGetDirectionFailure() {
        final Coordinate coordinate1 = Coordinate.of(1, 2);
        final Coordinate coordinate2 = Coordinate.of(2, 3);
        assertThrows(IllegalArgumentException.class, () -> coordinate1.getDirection(coordinate2));
    }

}
