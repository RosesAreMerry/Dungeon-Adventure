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
        super(theName, 75, 0.7, 25, 45, 5, 0.3);
        myRandom = new Random();
        myHealAmount = 0;
        myHitPoints = this.getHitPoints();
    }

    /**
     * Performs Priestess' special skill: healing.
     */
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

    boolean canUseSpecialSkill() {
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

    @Override
    public String toString() {
        return getName() + " the Priestess" +
                "\nHit Points: " + getHitPoints() +
                "\nChance to Hit: " + getHitChance() +
                "\nMinimum Damage: " + getDamageMin() +
                "\nMaximum Damage: " + getDamageMax() +
                "\nAttack Speed: " + getAttackSpeed() +
                "\nBlock Chance: " + getBlockChance() +
                "\nSpecial Skill: " + "Healing";
    }
}






