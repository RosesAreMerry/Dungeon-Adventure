package model;

import java.util.Random;

/**
 * Represents a Warrior in the game.
 * @author Maliha Hossain
 * @author Chelsea Dacones
 */
public class Warrior extends Hero {
    /**
     * constructor to initialize the states
     *
     * @param theName
     */
    private boolean myUsedSpecialCase;
    private static final double USE_SPECIALCASE_PROBABILITY = 0.4;


    public Warrior(final String theName) {
        super(theName, 125, 0.8, 35, 60, 4, 0.2);
        myUsedSpecialCase = useSpecialSkill();

    }

    public void attack(final DungeonCharacter theOpponent) {
        if (canAttack()) {
            if (myUsedSpecialCase) {
               this.setTotalDamage(0);
                final int damage = myRandom.nextInt(101) + 75; // 75 to 175 points of damage
                this.setTotalDamage(getTotalDamage()+damage);
                //setTotalDamage(damage);
                theOpponent.setHitPoints(theOpponent.getHitPoints() - damage);
                theOpponent.setAttacked(true);
            }else if(!myUsedSpecialCase) {
                calculateDamage(theOpponent); // if special skill is unsuccessful, perform normal attack
                theOpponent.setAttacked(true);
            }
        } else {
            theOpponent.setAttacked(false);
            setTotalDamage(0);
            // report attack failure
        }
    }

    /**
     * Checks whether Warrior can use Crushing Blow skill.
     * Has 40% chance of succeeding.
     *
     * @return true if Warrior can use special skill; otherwise false
     */
    private boolean useSpecialSkill() {
        final double randomValue = Math.random();
        return randomValue <= USE_SPECIALCASE_PROBABILITY;
    }
    /**
     * this is for testing
     *
     * @param theValue
     */
    public void setSpecialCase(final boolean theValue) {
        myUsedSpecialCase = theValue;
    }


}