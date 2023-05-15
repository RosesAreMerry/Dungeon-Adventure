package model;

import java.util.Random;

/**
 * Represents a Priestess in the game.
 *
 * @author Maliha Hossain
 * @author Chelsea Dacones
 */
public class Priestess extends Hero implements Healable {
    boolean success;

    public Priestess(final String theName) {
        super(theName, 75, .7, 25, 45, 5, .3);
        success = false;
    }

    @Override
    public void heal() {
        int myCurrentHitPoints = getHitPoints();
        final int healAmount = new Random().nextInt(20 - 10 + 1) + 10;
        myCurrentHitPoints += healAmount;
        // Ensure the Priestess' hit points do not exceed maximum hit points
        myCurrentHitPoints = Math.min(myCurrentHitPoints, getMaxHitPoints());
        setHitPoints(myCurrentHitPoints);
        }
    }

//    /**
//     * attack method
//     *
//     * @param theOpponent
//     */
//    public void attack(DungeonCharacter theOpponent) {
//        final int numofAttack = Math.max(1, this.getAttackSpeed() / theOpponent.getAttackSpeed());
//        if (canAttack()) {
//            for (int i = 0; i < getAttackSpeed(); i++) {
//                int damage = new Random().nextInt(getDamageMax() - getDamageMin() + 1)
//                        + getDamageMin();
//                theOpponent.setHitPoints(theOpponent.getHitPoints() - damage);
//                success = true;
//            }
//            if (wasAttacked() && !isFainted()) {
//                heal();
//            }
//            if (success) {
//                System.out.println("successful");
//            }
//        }
//    }




