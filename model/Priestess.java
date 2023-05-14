package model;

import java.util.Random;

public class Priestess extends Hero implements Healable {
    boolean success;
    int originalHitpoints;

    protected Priestess(String theName, int theHitPoints, Double theHitChance,
                        int theDamageMin, int theDamageMax, int theAttackSpeed, Double theBlockChance) {
        super(theName, 75, .7, 25, 45, 5, .3);
         originalHitpoints = theHitPoints;
         success = false;
    }

    @Override
    public boolean wasAttacked() {

        if (this.getHitPoints() < originalHitpoints) {
            return true;
        } else {
            return false;
        }
    }

    private void heal() {
        Healable healable = (Healable) this;
        healable.heal(originalHitpoints, this.getHitPoints());
    }

    /**
     * attack method
     *
     * @param theOpponent
     */
    public void attack(DungeonCharacter theOpponent) {
        final int numofAttack = Math.max(1, this.getAttackSpeed() / theOpponent.getAttackSpeed());
        if (canAttack()) {
            for (int i = 0; i < getAttackSpeed(); i++) {
                int damage = new Random().nextInt(getDamageMax() - getDamageMin() + 1)
                        + getDamageMin();
                theOpponent.setHitPoints(theOpponent.getHitPoints() - damage);
                success = true;
            }
            if (wasAttacked() && !isFainted()) {
                heal();
            }
            if (success) {
                System.out.println("successful");
            }
        }

    }
}




