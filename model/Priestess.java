package model;

import java.util.Random;
/**
 * Represents a Priestess in the game.
 * @author Maliha Hossain
 * @author Chelsea Dacones
 */
public class Priestess extends Hero implements Healable {
    boolean success;
    int myCurrentHitPoints;
    private Random myRandom;
    /**
     * initializes the states
     *
     * @param theName
     */
    public Priestess(final String theName) {
        super(theName, 75, .7, 25, 45, 5, .3);
        success = false;
        myCurrentHitPoints = this.getHitPoints();
        myRandom= new Random();
    }
    @Override
    public void heal() {
        final int minBound = 1; // Minimum bound for the healAmount
        final int maxBound = Math.max(1, myCurrentHitPoints - this.getHitPoints()); // Positive bound
        final int healAmount = myRandom.nextInt(maxBound) + minBound;
        final int healedHitPoints = this.getHitPoints() + healAmount;
        this.setHitPoints(healedHitPoints);
        }
    /**
     * Sets the Random object for testing purposes.
     *
     * @param theRandom the Random object to set.
     */
    public void setMyRandom(final Random theRandom) {
        this.myRandom = theRandom;
    }
}






