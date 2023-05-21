package model;

import java.util.Random;

public class Thief extends Hero {
    private boolean mySurpriseAttack;

    private boolean myCaught;

    private Random myRandom;

    public Thief(final String theName) {
        super(theName, 75, 0.8, 20, 40, 6, 0.4);
        mySurpriseAttack = false;
        myCaught = false;
        myRandom = new Random();
    }

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
     * @param theOpponent
     */
    @Override
    public void attack(final DungeonCharacter theOpponent) {
        final int numOfAttack = Math.max(1, this.getAttackSpeed() / theOpponent.getAttackSpeed());
        if (caught()) { // don't perform attack if caught
            System.out.println(getName() + " was caught");
            return;
        }
        if (useSpecialSkill()) {
            for (int i = 0; i < numOfAttack + 1; i++) {
                final int damage = myRandom.nextInt(getDamageMax() - getDamageMin() + 1)
                        + getDamageMin();
                theOpponent.setHitPoints(theOpponent.getHitPoints() - damage);
            }
        } else { // normal attack
            for (int i = 0; i < numOfAttack; i++) {
                final int damage = myRandom.nextInt(getDamageMax() - getDamageMin() + 1)
                        + getDamageMin();
                theOpponent.setHitPoints(theOpponent.getHitPoints() - damage);
            }
        }
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

