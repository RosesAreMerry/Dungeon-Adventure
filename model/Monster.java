package model;

import java.util.Random;

/**
 * Represents a Monster in the game.
 *
 * @author Chelsea Dacones
 * @version May 14th 2023
 */
public class Monster extends DungeonCharacter implements Healable {
    private final double myChanceToHeal;
    private final int myMinHeal;
    private final int myMaxHeal;
    private Random myRandom;

    Monster(final String theName, final int theHitPoints, final double theHitChance, final int theDamageMin,
                      final int theDamageMax, final int theAttackSpeed, final double theHealChance, final int theMinHeal, final int theMaxHeal) {
        super(theName, theHitPoints, theHitChance, theDamageMin, theDamageMax, theAttackSpeed);
        this.myChanceToHeal = theHealChance;
        this.myMinHeal = theMinHeal;
        this.myMaxHeal = theMaxHeal;
        this.myRandom = new Random();
    }

    /**
     * Heals the Monster based on chance to heal and range of heal points.
     * Monster's can heal themselves if they've been attacked and have not fainted.
     */
    @Override
    public void heal() {
        if (isAttacked() && !isFainted()) {
            final double randomValue = myRandom.nextDouble();
            int myCurrentHitPoints = getHitPoints();
            if (randomValue < myChanceToHeal) { //
                final int healAmount = myRandom.nextInt(myMaxHeal - myMinHeal + 1) + myMinHeal;
                myCurrentHitPoints += healAmount;
                // Ensure the monster's hit points do not exceed maximum hit points
                myCurrentHitPoints = Math.min(myCurrentHitPoints, getMaxHitPoints());
                setHitPoints(myCurrentHitPoints);
            }
        }
    }

    /**
     * Attacks opponent based on chance to hit.
     * If the opponent has the ability to block the attack, the attack may be blocked.
     * Otherwise, the damage is inflicted on the opponent.
     *
     * @param theOpponent the character to attack.
     */
    @Override
    public void attack(final DungeonCharacter theOpponent) {
        if (theOpponent.canBlockAttack()) {
            theOpponent.setAttacked(false);
            return;
        }
        if (canAttack()) {
            calculateDamage(theOpponent);
            theOpponent.setAttacked(true);
            if (theOpponent instanceof Priestess) {
                ((Priestess) theOpponent).heal();
            }
        }
    }

    public double getMyChanceToHeal() {
        return myChanceToHeal;
    }
    public int getMyMinHeal() {
        return myMinHeal;
    }

    public int getMyMaxHeal() {
        return myMaxHeal;
    }

    /**
     * Sets the Random object for testing purposes.
     *
     * @param theRandom the Random object to set.
     */
    public void setMyRandom(final Random theRandom) {
        this.myRandom = theRandom;
    }

    /**
     * THe Monster's statistics.
     * Made for testing purposes.
     * @return the Monster's statistics.
     */
    @Override
    public String toString() {
        return "Monster: " + getName() +
                "\nHit Points: " + getHitPoints() +
                "\nChance to Hit: " + getHitChance() +
                "\nMinimum Damage: " + getDamageMin() +
                "\nMaximum Damage: " + getDamageMax() +
                "\nAttack Speed: " + getAttackSpeed() +
                "\nChance to Heal: " + getMyChanceToHeal() +
                "\nMinimum Heal Points: " + getMyMinHeal() +
                "\nMaximum Heal Points: " + getMyMaxHeal();

    }
}
