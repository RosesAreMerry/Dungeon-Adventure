package model;

import java.util.Random;

/**
 * Theif Class represents a character in the game
 */
public class Thief extends Hero{
    private boolean mySurpriseAttack;
    
    private boolean myCaught;
    
    private Random myRandom;

    private int myTotalDamage;

    public Thief(final String theName) {
        super(theName, 75, 0.8, 20, 40, 6, 0.4);
        mySurpriseAttack = false;
        myCaught = false;
        myRandom = new Random();
        myTotalDamage = 0;
    }
    /**
     * determines if the specialskill was used or not
     */
    private boolean useSpecialSkill() {
        if (myRandom.nextDouble() <= .4) {
            mySurpriseAttack = true;
        }
        return mySurpriseAttack;
    }

    private boolean caught() {
        if (myRandom.nextDouble() <= .2) {
            myCaught = true;
        }
        return myCaught;
    }

    /**
     * attack method
     * if surpriseAttack is used then the character gets one extra turn
     * @param theOpponent
     */
    @Override
    public void attack(final DungeonCharacter theOpponent) {
        final int numOfAttack = Math.max(1, this.getAttackSpeed() / theOpponent.getAttackSpeed());
        if (caught() || !canAttack()) { // don't perform attack if caught, or cannot attack based on chance to hit
            theOpponent.setAttacked(false);
            return;
        }
        if (useSpecialSkill()) {
            for (int i = 0; i < numOfAttack + 1; i++) {
                final int damage = myRandom.nextInt(getDamageMax() - getDamageMin() + 1)
                        + getDamageMin();
                myTotalDamage += damage;
                setTotalDamage(myTotalDamage);
                theOpponent.setHitPoints(Math.max(theOpponent.getHitPoints() - damage, 0));
                theOpponent.setAttacked(true);
            }
        } else { // normal attack
            calculateDamage(theOpponent);
            theOpponent.setAttacked(true);
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
}

