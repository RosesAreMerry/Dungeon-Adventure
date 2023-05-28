package model;

import java.util.Random;

/**
 * The parent class for the character hierarchy.
 *
 * @author Maliha Hossain
 * @author Chelsea Dacones
 * @version May 14th 2023
 */
public abstract class DungeonCharacter {
    private final int myDamageMin;
    private final int myDamageMax;
    private final int myAttackSpeed;
    private final double myHitChance;
    private final int myMaxHitPoints;
    private final String myName;
    private int myHitPoints;
    protected Random myRandom;
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
            // report attack failure
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
        myTotalDamage = 0;
        for (int i = 0; i < numOfAttacks; i++) {
            final int damage = myRandom.nextInt(getDamageMax() - getDamageMin() + 1)
                    + getDamageMin();
            myTotalDamage += damage;
            theOpponent.setHitPoints(Math.max(0, (theOpponent.getHitPoints() - damage)));
            setTotalDamage(myTotalDamage);
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

    protected boolean canBlockAttack() {
        return false;
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
     * Sets the Random object for testing purposes.
     *
     * @param theRandom the Random object to set.
     */
    public void setRandom(final Random theRandom) {
        myRandom = theRandom;
    }

}







