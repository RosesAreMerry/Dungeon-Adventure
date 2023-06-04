package model;

import java.util.Random;

/**
 * Represents a Warrior in the game.
 *
 * @author Maliha Hossain
 * @author Chelsea Dacones
 */
public class Warrior extends Hero {
    private boolean myUsedSpecialCase;
    private static final double USE_SPECIALCASE_PROBABILITY = 0.4;

    private Random myRandom;

    /**
     * constructor to initialize the states
     *
     * @param theName
     */
    public Warrior(final String theName) {
        super(theName, 125, 0.8, 35, 60, 4, 0.2);
        myRandom = new Random();
    }

    @Override
    public void attack(final DungeonCharacter theOpponent) {
        if (canAttack()) {
            if (useSpecialSkill()) {
                final int damage = myRandom.nextInt(101) + 75; // 75 to 175 points of damage
                theOpponent.setHitPoints(Math.max(theOpponent.getHitPoints() - damage, 0));
                this.setTotalDamage(getTotalDamage()+damage);
                theOpponent.setAttacked(true);
            } else if(!useSpecialSkill()) {
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
    protected boolean useSpecialSkill() {
        final double randomValue = Math.random();
        return randomValue <= USE_SPECIALCASE_PROBABILITY;
    }

    public void setMyRandom(final Random theRandom) {
        myRandom = theRandom;
    }

    @Override
    public String toString() {
        return this.getName() + " the " + this.getClass().getSimpleName() +
                "\nHit Points: " + getHitPoints() +
                "\nChance to Hit: " + getHitChance() +
                "\nMinimum Damage: " + getDamageMin() +
                "\nMaximum Damage: " + getDamageMax() +
                "\nAttack Speed: " + getAttackSpeed() +
                "\nBlock Chance: " + getBlockChance() +
                "\nSpecial Skill: " + "Crushing Blow that does 75 to 175 points of damage " +
                "but only has a 40% chance of succeeding";
    }

}