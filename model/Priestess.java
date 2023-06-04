package model;

import java.util.Random;

/**
 * Represents a Priestess in the game.
 *
 * @author Maliha Hossain
 * @author Chelsea Dacones
 */
public class Priestess extends Hero implements Healable {
    int myHitPoints;
    private Random myRandom;
    private static final double USE_SPECIALCASE_PROBABILITY = 0.5;
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
        myHitPoints = this.getHitPoints();
    }

    @Override
    public void heal() {
        if (!isFainted() && CanUseSpecialSkill()) {
            final int minBound = 1; // Minimum bound for the healAmount
            final int maxBound = Math.max(1, myHitPoints - this.getHitPoints()); // Positive bound
            myHealAmount = myRandom.nextInt(maxBound) + minBound;
            setHealAmount(myHealAmount);
            final int healedHitPoints = this.getHitPoints() + myHealAmount;
            this.setHitPoints(healedHitPoints);
        } else{
            return;
        }
    }
    private void setHealAmount(int theHealAmount) {
        myHealAmount = theHealAmount;
    }
    @Override
    public int healAmount() {
        return myHealAmount;
    }

    /**
     * @param theOpponent the character to attack.
     */
    public void attack(final DungeonCharacter theOpponent) {
        // check if character can attack based on chance to hit
        if (canAttack()) {
            calculateDamage(theOpponent);
            theOpponent.setAttacked(true);
        } else {
            // report attack failure
            theOpponent.setAttacked(false);
            setTotalDamage(0);
        }
    }
    private boolean CanUseSpecialSkill() {
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






