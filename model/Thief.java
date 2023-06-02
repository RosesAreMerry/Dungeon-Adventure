package model;

import java.util.Random;

/**
 * Theif Class represents a character in the game
 */
public class Thief extends Hero{
    private boolean mySurpriseAttack;
    
    private boolean myCaught;
    
    private Random myRandom;
    private static final double USE_SPECIALCASE_PROBABILITY = 0.4;
    private static final double CAUGHT_PROBABILITY= 0.5;



    public Thief(final String theName) {
        super(theName, 75, 0.8, 20, 40, 6, 0.4);
        mySurpriseAttack = false;
        myCaught = false;
        myRandom = new Random();
        super.setTotalDamage(0);

    }
    /**
     * determines if the specialskill was used or not
     */
    private boolean useSpecialSkill() {
        if (myRandom.nextDouble() <= USE_SPECIALCASE_PROBABILITY) {
            mySurpriseAttack = true;
        }else{
            mySurpriseAttack = false;
        }
       return mySurpriseAttack;
    }
    /**
     * determines if the specialskill was used or not
     */
    private boolean caught() {
        if (myRandom.nextDouble() <= CAUGHT_PROBABILITY) {
            myCaught = true;
        }else{
            myCaught= false;
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
        if (caught()) { // don't perform attack if caught
            System.out.println(getName() + " was caught");
           setTotalDamage(0);

            return;
        }
        if(canAttack()) {
            if (useSpecialSkill()) {
                this.setTotalDamage(0);
                for (int i = 0; i < numOfAttack + 1; i++) {
                    final int damage = myRandom.nextInt(getDamageMax() - getDamageMin() + 1)
                            + getDamageMin();
                    setTotalDamage(getTotalDamage() + damage);
                    theOpponent.setHitPoints(theOpponent.getHitPoints() - damage);
                    //System.out.println(theOpponent.getName() + "  current HitPoint is " + theOpponent.getHitPoints());
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
    public void setRandom(final Random theRandom) {
        this.myRandom = theRandom;
    }
}

