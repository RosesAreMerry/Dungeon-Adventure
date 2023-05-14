package model;

import java.util.Random;

public class Monster extends DungeonCharacter {
    protected int myMaxHitPoints;
    protected double myChanceToHeal;
    protected int myMinHeal;
    protected int myMaxHeal;
    protected int myCurrentHitPoints;
    boolean myAttacked;
    protected Monster(String theName, int theHitPoints, double theHitChance, int theMinDamage, int theMaxDamage,
                      int theAttackSpeed, double theHealChance, int theMinHeal, int theMaxHeal) {
        super(theName, theHitPoints, theHitChance, theMinDamage, theMaxDamage, theAttackSpeed);
        this.myChanceToHeal = theHealChance;
        this.myMinHeal = theMinHeal;
        this.myMaxHeal = theMaxHeal;
        this.myMaxHitPoints = theHitPoints;
        myCurrentHitPoints = getHitPoints();
    }

    /**
     * Heal a Monster based on chance to heal and healing range.
     */
    public void heal() {
        double random = new Random().nextDouble();
        if (random < myChanceToHeal) { //
            int healAmount = new Random().nextInt(myMaxHeal - myMinHeal + 1) + myMinHeal;
            myCurrentHitPoints += healAmount;
            // Ensure the monster's hit points do not exceed maximum hit points
            myCurrentHitPoints = Math.min(myCurrentHitPoints, myMaxHitPoints);
        }
    }

    /**
     * need to work on it
     * @return
     */
    @Override
    public boolean wasAttacked() {
        return false;
    }

    @Override
    public void attack(DungeonCharacter theOpponent) {
        int previousHitPoints = theOpponent.getHitPoints();
        int numOfAttacks = 5;//Rose: this is giving an error so I commented out for now. Don't commit code that doesn't run in the future :) getNumAttacks(theOpponent);
        // check if monster can attack based on chance to hit
        if (canAttack()) {
            for (int i = 0; i < numOfAttacks; i++) {
                int damage = new Random().nextInt(getDamageMax() - getDamageMin() + 1)
                        + getDamageMin();
                setHitPoints(getHitPoints() - damage);
                myAttacked = previousHitPoints > getHitPoints(); // check if monster has been attacked
                // give monster chance to heal if attacked and is not fainted
                if (myAttacked && !isFainted()) {
                    heal();
                }
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
    @Override
    public String toString() {
        return "Monster: " + getName() +
                "\nHit Points: " + getHitPoints() +
                "\nChance to Hit: " + getMyHitChance() +
                "\nMinimum Damage: " + getDamageMin() +
                "\nMaximum Damage: " + getDamageMax() +
                "\nAttack Speed: " + getAttackSpeed() +
                "\nChance to Heal: " + getMyChanceToHeal() +
                "\nMinimum Heal Points: " + getMyMinHeal() +
                "\nMaximum Heal Points: " + getMyMaxHeal();

    }
}
