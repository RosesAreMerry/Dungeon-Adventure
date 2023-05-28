package model;

import java.util.Queue;
import java.util.Random;

/**
 * Extends the {@link java.util.Random} class to provide a mock implementation for testing purposes.
 * Allows setting the mock values for generating random double and int values.
 */
class RandomMock extends Random {
    private double myMockDoubleValue;
    private Queue<Double> myMockDoubleValues;
    private int myMockIntValue;

    public void setMockDoubleValue(final double theDouble) {
        myMockDoubleValue = theDouble;
    }
    public void setMockDoubleValues(final Queue<Double> theDoubleValues) {
        myMockDoubleValues = theDoubleValues;
    }

    public void setMockIntValue(final int theInt) {
        myMockIntValue = theInt;
    }
    @Override
    public double nextDouble() {
        if (myMockDoubleValues != null && !myMockDoubleValues.isEmpty()) {
            return myMockDoubleValues.remove();
        }
        return myMockDoubleValue;
    }
    @Override
    public int nextInt(final int theBound) {
        return myMockIntValue;
    }
}