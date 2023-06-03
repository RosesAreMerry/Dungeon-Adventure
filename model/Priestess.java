package model;

import java.util.Random;

/**
 * Represents a Priestess in the game.
 *
 * @author Maliha Hossain
 * @author Chelsea Dacones
 */
public class Priestess extends Hero implements Healable {
    boolean success;
    int myHitPoints;
    private Random myRandom;
    private static final double USE_SPECIALCASE_PROBABILITY = 0.5;
    /**
     * initializes the states
     *
     * @param theName
     */
    public Priestess(final String theName) {
        super(theName, 75, .7, 25, 45, 5, .3);
        myHitPoints = this.getHitPoints();
        myRandom= new Random();
    }

    @Override
    public  void heal() {
        if (!isFainted() && CanUseSpecialSkill()) {
            final int minBound = 1; // Minimum bound for the healAmount
            final int maxBound = Math.max(1, myHitPoints - this.getHitPoints()); // Positive bound
            final int healAmount = myRandom.nextInt(maxBound) + minBound;
            final int healedHitPoints = this.getHitPoints() + healAmount;
            this.setHitPoints(healedHitPoints);
        }else{
            return;
        }
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
     * @param theRandom the Random object to set.
     */
    public void setMyRandom(final Random theRandom) {
        this.myRandom = theRandom;
    }
}






