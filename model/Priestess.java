package model;

import java.io.Serial;
import java.io.Serializable;
import java.util.Random;

/**
 * Represents a Priestess in the game.
 *
 * @author Maliha Hossain
 * @author Chelsea Dacones
 */
public class Priestess extends Hero implements Healable, Serializable {
    private static final double USE_SPECIALCASE_PROBABILITY = 0.5;
    @Serial
    private static final long serialVersionUID = -6055191108103186823L;
    private final int myHitPoints;
    private Random myRandom;
    private int myHealAmount;

    /**
     * initializes the states
     *
     * @param theName the name of the player
     */
    public Priestess(final String theName) {
        super(theName, 75, .7, 25, 45, 5, .3);
        myRandom = new Random();
        myHealAmount = 0;
        myHitPoints = this.getHitPoints();
    }

    @Override
    public void heal() {
        if (!isFainted() && canUseSpecialSkill()) {
            final int minBound = 1; // Minimum bound for the healAmount
            final int maxBound = Math.max(1, myHitPoints - this.getHitPoints()); // Positive bound
            myHealAmount = myRandom.nextInt(maxBound) + minBound;
            setHealAmount(myHealAmount);
            final int healedHitPoints = this.getHitPoints() + myHealAmount;
            this.setHitPoints(healedHitPoints);
        }
    }
    private void setHealAmount(final int theHealAmount) {
        myHealAmount = theHealAmount;
    }
    @Override
    public int healAmount() {
        return myHealAmount;
    }

    private boolean canUseSpecialSkill() {
        return myRandom.nextDouble() <= USE_SPECIALCASE_PROBABILITY ;
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






