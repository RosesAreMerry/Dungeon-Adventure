package model;

import java.io.Serial;
import java.io.Serializable;
import java.util.Random;

/**
 * The parent class for the character hierarchy.
 *
 * @author Maliha Hossain
 * @author Chelsea Dacones
 */
public abstract class DungeonCharacter implements Serializable {
    @Serial
    private static final long serialVersionUID = 4347694900186580770L;
    private int myDamageMin;
    private int myDamageMax;
    private int myAttackSpeed;
    private double myHitChance;
    private int myMaxHitPoints;
    private final String myName;
    private Random myRandom;
    private int myHitPoints;
    private boolean myIsAttacked;
    private int myTotalDamage;
    /**
     * Constructs a new DungeonCharacter and initializes instance fields.
     *
     * @param theName        the character's name
     * @param theHitPoints   the maximum number of hit points of the character
     * @param theHitChance   the character's probability to attack
     * @param theDamageMin   the minimum damage the character can inflict
     * @param theDamageMax   the maximum damage the character can inflict
     * @param theAttackSpeed the attack speed of the character
     */
    protected DungeonCharacter(final String theName, final int theHitPoints, final double theHitChance,
                               final int theDamageMin, final int theDamageMax, final int theAttackSpeed) {
        myName = theName;
        myHitPoints = theHitPoints;
        myHitChance = theHitChance;
        myDamageMin = theDamageMin;
        myDamageMax = theDamageMax;
        myAttackSpeed = theAttackSpeed;
        myRandom = new Random();
        myIsAttacked = false;
        myMaxHitPoints = theHitPoints;
        myTotalDamage = 0;
    }

    /**
     * Checks if character is fainted.
     *
     * @return true if hit points is less than or equal to 0; otherwise false.
     */
    public boolean isFainted() {
        return myHitPoints <= 0;
    }

    public String getName() {
        return myName;
    }

    public int getHitPoints() {
        return myHitPoints;
    }

    public void setHitPoints(final int theHitPoints) {
        this.myHitPoints = theHitPoints;
    }

    public int getAttackSpeed() {
        return myAttackSpeed;
    }

    public int getDamageMax() {
        return myDamageMax;
    }

    public Double getHitChance() {
        return myHitChance;
    }

    public void setHitChance(final Double theHitChance) {
        myHitChance = theHitChance;
    }

    public int getDamageMin() {
        return myDamageMin;
    }

    public int getMaxHitPoints() {
        return myMaxHitPoints;
    }

    public int getTotalDamage() {
        return myTotalDamage;
    }

    public void setTotalDamage(final int theTotalDamage) {
        myTotalDamage = theTotalDamage;
    }

    public void setDamageMin(final int theDamageMin) {
        this.myDamageMin = theDamageMin;
    }

    public void setDamageMax(final int theDamageMax) {
        this.myDamageMax = theDamageMax;
    }

    public void setAttackSpeed(final int theAttackSpeed) {
        this.myAttackSpeed = theAttackSpeed;
    }
    public void setMaxHitPoints(final int theMaxHitPoints) {
        this.myMaxHitPoints = theMaxHitPoints;
    }

    /**
     * Decides if character can attack based on chance to hit.
     *
     * @return true if character can attack opponent; otherwise false
     */
    protected boolean canAttack() {
        final double randomValue = myRandom.nextDouble();
        return randomValue < myHitChance;
    }

    /**
     * Performs an attack on the opponent of the character.
     *
     * @param theOpponent the character to attack.
     */
    public void attack(final DungeonCharacter theOpponent) {
        // check if character can attack based on chance to hit
        if (canAttack()) {
            calculateDamage(theOpponent);
            theOpponent.setAttacked(true);
        } else {
            theOpponent.setAttacked(false);
            setTotalDamage(0);
        }
    }

    /**
     * Calculates and applies the damage inflicted by the character onto the opponent.
     * Number of attacks is determined by the attack speeds of the two characters in battle.
     * Damage is randomly generated based on the damage range of the character and subtracted from the opponent.
     *
     * @param theOpponent the character to inflict damage on.
     */
    protected int calculateDamage(final DungeonCharacter theOpponent) {
        final int numOfAttacks = Math.max(1, this.getAttackSpeed() / theOpponent.getAttackSpeed());
        setTotalDamage(0);
        for (int i = 0; i < numOfAttacks; i++) {
            final int damage = myRandom.nextInt(getDamageMax() - getDamageMin() + 1)
                    + getDamageMin();
            //used the setter
            setTotalDamage(getTotalDamage() + damage);
            theOpponent.setHitPoints(Math.max(theOpponent.getHitPoints() - damage, 0));
        }
        return myTotalDamage;
    }

    /**
     * Checks if character has been attacked.
     * Used to track whether the character has been attacked for healing purposes.
     *
     * @return true if character has been attacked; otherwise false
     */
    protected boolean isAttacked() {
        return myIsAttacked;
    }

    /**
     * Sets the flag indicating whether the character has been attacked or not.
     *
     * @param theAttacked true if the character has been attacked; false otherwise
     */
    protected void setAttacked(final boolean theAttacked) {
        myIsAttacked = theAttacked;
    }

    /**
     * Decides if character can block attack.
     *
     * @return false, only Hero's have the ability to block an attack.
     */
    protected boolean canBlockAttack() {
        return false;
    }

    /**
     * Sets the Random object for testing purposes.
     *
     * @param theRandom the Random object to set.
     */
    public void setRandom(final Random theRandom) {
        myRandom = theRandom;
    }

    public String toString() {
        return "Monster: " + getName() +
                "\nHit Points: " + getHitPoints() +
                "\nChance to Hit: " + getHitChance() +
                "\nMinimum Damage: " + getDamageMin() +
                "\nMaximum Damage: " + getDamageMax() +
                "\nAttack Speed: " + getAttackSpeed();
    }

}







