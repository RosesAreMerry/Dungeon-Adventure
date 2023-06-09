package model;

import java.io.Serial;
import java.io.Serializable;
import java.util.Random;

/**
 * Thief represents a player in the game.
 *
 * @author Maliha Hossain
 */
public class Thief extends Hero implements Serializable {
    private static final double USE_SPECIAL_CASE_PROBABILITY = 0.4;
    private static final double CAUGHT_PROBABILITY = 0.5;
    @Serial
    private static final long serialVersionUID = 2136676198219810338L;
    private Random myRandom;

    public Thief(final String theName) {
        super(theName, 75, 0.8, 20, 40, 6, 0.4);
        myRandom = new Random();
        super.setTotalDamage(0);

    }
    /**
     * Determines if the special skill was used or not
     */
    boolean useSpecialSkill() {
       return myRandom.nextDouble() <= USE_SPECIAL_CASE_PROBABILITY;
    }
    /**
     * Determines if the special skill was used or not
     */
    boolean wasCaught() {
        return myRandom.nextDouble() <= CAUGHT_PROBABILITY;
    }

    /**
     * Performs an attack on the monster.
     * If the special skill is used then the character gets one extra turn.
     *
     * @param theOpponent the character to attack
     */
    @Override
    public void attack(final DungeonCharacter theOpponent) {
        final int numOfAttack = Math.max(1, this.getAttackSpeed() / theOpponent.getAttackSpeed());
        if (wasCaught() || !canAttack()) { // don't perform attack if caught, or cannot attack based on chance to hit
            theOpponent.setAttacked(false);
            setTotalDamage(0);
            return;
        }
        if (canAttack()) {
            if (useSpecialSkill()) {
                this.setTotalDamage(0);
                for (int i = 0; i < numOfAttack + 1; i++) {
                    final int damage = myRandom.nextInt(getDamageMax() - getDamageMin() + 1)
                            + getDamageMin();
                    setTotalDamage(getTotalDamage() + damage);
                    theOpponent.setHitPoints(Math.max(theOpponent.getHitPoints() - damage, 0));
                    theOpponent.setAttacked(true);
                }
            } else { // normal attack
                calculateDamage(theOpponent);
                theOpponent.setAttacked(true);
            }
        }
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
        return this.getName() + " the Thief" +
                "\nHit Points: " + getHitPoints() +
                "\nChance to Hit: " + getHitChance() +
                "\nMinimum Damage: " + getDamageMin() +
                "\nMaximum Damage: " + getDamageMax() +
                "\nAttack Speed: " + getAttackSpeed() +
                "\nBlock Chance: " + getBlockChance() +
                "\nSpecial Skill: " + "Surprise Attack - 40 percent chance it is successful";
    }
}

