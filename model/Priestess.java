package model;

import java.util.Random;

/**
 * Represents a Priestess in the game.
 *
 * @author Maliha Hossain
 * @author Chelsea Dacones
 */
public class Priestess extends Hero implements Healable {

    private Random myRandom;

    private int myHealAmount;

    /**
     * initializes the states
     *
     * @param theName
     */
    public Priestess(final String theName) {
        super(theName, 75, .7, 25, 45, 5, .3);
        myRandom = new Random();
        myHealAmount = 0;
    }

    @Override
    public void heal() {
        int myCurrentHitPoints = getHitPoints();
        myHealAmount = myRandom.nextInt(20 - 10 + 1) + 10;
        myCurrentHitPoints += myHealAmount;
        setHealAmount(myHealAmount);
        // Ensure the hit points do not exceed maximum hit points
        myCurrentHitPoints = Math.min(myCurrentHitPoints, getMaxHitPoints());
        setHitPoints(myCurrentHitPoints);

//        final int minBound = 1; // Minimum bound for the healAmount
//        final int maxBound = Math.max(1, myCurrentHitPoints - this.getHitPoints()); // Positive bound
//        final int healAmount = myRandom.nextInt(maxBound) + minBound;
//        myHealedHitPoints = this.getHitPoints() + healAmount;
//        this.setHitPoints(myHealedHitPoints);
    }

    private void setHealAmount(int theHealAmount) {
        myHealAmount = theHealAmount;
    }
    @Override
    public int healAmount() {
        return myHealAmount;
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






