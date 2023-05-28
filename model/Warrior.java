package model;

import java.util.Random;

/**
 * Represents a Warrior in the game.
 *
 * @author Maliha Hossain
 * @author Chelsea Dacones
 */
public class Warrior extends Hero {
    /**
     * constructor to initialize the states
     *
     * @param theName
     */
    private Random myRandom;

    public Warrior(final String theName) {
        super(theName, 125, 0.8, 35, 60, 4, 0.2);
        myRandom = new Random();
    }


    public void attack(final DungeonCharacter theOpponent) {
        if (canAttack()) {
            if (useSpecialSkill()) {
                final int damage = myRandom.nextInt(101) + 75; // 75 to 175 points of damage
                theOpponent.setHitPoints(Math.max(theOpponent.getHitPoints() - damage, 0));
                theOpponent.setAttacked(true);
                return; // exit method after performing special skill
            }
            calculateDamage(theOpponent); // if special skill is unsuccessful, perform normal attack
            theOpponent.setAttacked(true);
        } else {
            theOpponent.setAttacked(false);
            // report attack failure
        }
    }

    /**
     * Checks whether Warrior can use Crushing Blow skill.
     * Has 40% chance of succeeding.
     *
     * @return true if Warrior can use special skill; otherwise false
     */
    protected boolean useSpecialSkill() {
        final double randomValue = Math.random();
        return randomValue < 0.4;
    }

    public void setMyRandom(final Random theRandom) {
        myRandom = theRandom;
    }


}