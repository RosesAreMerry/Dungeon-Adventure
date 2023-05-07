package model;

import java.util.Random;

public abstract class Monster extends DungeonCharacter {
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
    }
    public void heal() {
        double random = Math.random();
        if (random < myChanceToHeal) {
            int healAmount = new Random().nextInt(myMaxHeal - myMinHeal + 1) + myMinHeal;
            myCurrentHitPoints += healAmount;
            myCurrentHitPoints = Math.min(myCurrentHitPoints, myMaxHitPoints);
        }
    }
    @Override
    public void attack(DungeonCharacter theOpponent) {
        int previousHitPoints = theOpponent.getHitPoints();
        int numOfAttacks = getAttackSpeed() / theOpponent.getAttackSpeed();
        if (canAttack()) {
            for (int i = 0; i < numOfAttacks; i++) {
                int damage = new Random().nextInt(getDamageMax() - getDamageMin() + 1)
                        + getDamageMin();
                setHitPoints(getHitPoints() - damage);
                myAttacked = previousHitPoints > getHitPoints();
                if (myAttacked && !isFainted()) {
                    heal();
                }
            }
        }
    }
}
