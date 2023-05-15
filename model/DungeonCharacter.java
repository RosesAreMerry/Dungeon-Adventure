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
    private String myName;
    private int myHitPoints;
    private final int myDamageMin;
    private final int myDamageMax;
    private final int myAttackSpeed;
    private final double myHitChance;
    private Random myRandom;
    private boolean myIsAttacked;

    /**
     * Initialize the instance fields.
     */
    public DungeonCharacter(final String theName, final int theHitPoints, final double theHitChance,
                               final int theDamageMin, final int theDamageMax, final int theAttackSpeed) {
        this.myName = theName;
        this.myHitPoints = theHitPoints;
        this.myHitChance = theHitChance;
        this.myDamageMin = theDamageMin;
        this.myDamageMax = theDamageMax;
        this.myAttackSpeed = theAttackSpeed;
        myRandom = new Random();
        myIsAttacked = false;
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
    public void setName(final String theName) {
        this.myName = theName;
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

    public Double getMyHitChance(){
        return myHitChance;
    }
    public int getDamageMin() {
        return myDamageMin;
    }

    /**
     * Decides if character can attack based on chance to hit.
     *
     * @return true if character can attack opponent; otherwise false
     */
    protected boolean canAttack() {
        final double randomValue = new Random().nextDouble();
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
    protected void calculateDamage(final DungeonCharacter theOpponent) {
        final int numOfAttacks = Math.max(1, this.getAttackSpeed() / theOpponent.getAttackSpeed());
        for (int i = 0; i < numOfAttacks; i++) {
            final int damage = myRandom.nextInt(getDamageMax() - getDamageMin() + 1)
                    + getDamageMin();
            theOpponent.setHitPoints(theOpponent.getHitPoints() - damage);
        }
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
     * Sets the Random object for testing purposes.
     *
     * @param theRandom the Random object to set.
     */
    public void setRandom(final Random theRandom) {
        this.myRandom = theRandom;
    }

}







