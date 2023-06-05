package model;

import java.io.Serial;
import java.io.Serializable;
import java.util.Random;

/**
 * Represents a Monster in the game.
 *
 * @author Chelsea Dacones
 * @version May 14th 2023
 */
public class Monster extends DungeonCharacter implements Healable, Serializable {
    @Serial
    private static final long serialVersionUID = 6426453188776325404L;
    private final double myChanceToHeal;
    private final int myMinHeal;
    private final int myMaxHeal;
    private Random myRandom;
    private int myHealAmount;

    /**
     * Constructs a new Monster
     *
     * @param theName        the monster's name
     * @param theHitPoints   the hit points (health) of the monster
     * @param theHitChance   the monster's chance to attack the player
     * @param theDamageMin   the minimum damage the monster can inflict on the player
     * @param theDamageMax   the maximum damage the monster can inflict on the player
     * @param theAttackSpeed the attack speed of the monster (determines number of attacks)
     * @param theHealChance  the monster's chance of healing after being attacked
     * @param theMinHeal     the monster's minimum amount of hit points to restore
     * @param theMaxHeal     the monster's maximum amount of hit points to restore
     */
    Monster(final String theName, final int theHitPoints, final double theHitChance, final int theDamageMin,
            final int theDamageMax, final int theAttackSpeed, final double theHealChance, final int theMinHeal, final int theMaxHeal) {
        super(theName, theHitPoints, theHitChance, theDamageMin, theDamageMax, theAttackSpeed);
        this.myChanceToHeal = theHealChance;
        this.myMinHeal = theMinHeal;
        this.myMaxHeal = theMaxHeal;
        this.myRandom = new Random();
        this.myHealAmount = 0;
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
                myHealAmount = myRandom.nextInt(myMaxHeal - myMinHeal + 1) + myMinHeal;
                myCurrentHitPoints += myHealAmount;
                setHealAmount(myHealAmount);
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
        if (theOpponent.canBlockAttack() || !canAttack()) {
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

    public double getChanceToHeal() {
        return myChanceToHeal;
    }

    public int getMinHeal() {
        return myMinHeal;
    }

    public int getMaxHeal() {
        return myMaxHeal;
    }


    private void setHealAmount(final int theHealAmount) {
        myHealAmount = theHealAmount;
    }

    /**
     * Retrieves the amount of hit points that were restored during healing.
     *
     * @return the number of hit points restored
     */
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

    /**
     * THe Monster's statistics.
     * Made for testing purposes.
     *
     * @return the Monster's statistics.
     */
    @Override
    public String toString() {
        return getName() +
                "\nHit Points: " + getHitPoints() +
                "\nChance to Hit: " + getHitChance() +
                "\nMinimum Damage: " + getDamageMin() +
                "\nMaximum Damage: " + getDamageMax() +
                "\nAttack Speed: " + getAttackSpeed() +
                "\nChance to Heal: " + getChanceToHeal() +
                "\nMinimum Heal Points: " + getMinHeal() +
                "\nMaximum Heal Points: " + getMaxHeal();
    }
}
