package model;

import java.util.Random;

/**
 * Extends the {@link java.util.Random} class to provide a mock implementation for testing purposes.
 * Allows setting the mock values for generating random double and int values.
 */
class RandomMock extends Random {
    private double myMockDoubleValue;
    private int myMockIntValue;

    public void setMockDoubleValue(final double theDouble) {
        this.myMockDoubleValue = theDouble;
    }

    public void setMockIntValue(final int theInt) {
        this.myMockIntValue = theInt;
    }
    @Override
    public double nextDouble() {
        return myMockDoubleValue;
    }
    @Override
    public int nextInt(final int theBound) {
        return myMockIntValue;
    }
}