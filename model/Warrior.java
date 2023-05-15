package model;

import java.util.Random;

/**
 * Represents a Warrior in the game.
 *
 * @author Maliha Hossain
 * @author Chelsea Dacones
 */
public class Warrior extends Hero {
    /**
     * constructor to initialize the states
     * @param theName
     */
    private boolean myUsedSpecialCase;
    public Warrior(final String theName) {
        super(theName, 125, 0.8, 35, 60, 4, 0.2);
        myUsedSpecialCase= useSpecialSkill();
    }

    /**
     * Attacks the opponent and reduces hit points.
     * @param theOpponent the opponent to attack.
     */
//    @Override
//    public void attack(final DungeonCharacter theOpponent) {
//        int points= theOpponent.getHitPoints();
//        if (canAttack()) {
//            if (myUsedSpecialCase) {
//                final int damage = 79; // 75 to 175 points of damage
//                theOpponent.setHitPoints(theOpponent.getHitPoints() - damage);
//                theOpponent.setAttacked(true);
//                return; // exit method after performing special skill
//            }
//            calculateDamage(theOpponent); // if special skill is unsuccessful, perform normal attack
//            theOpponent.setAttacked(true);
//        } else {
//            theOpponent.setAttacked(false);
//            // report attack failure
//        }
//
//    }
    public void attack(final DungeonCharacter theOpponent) {
        if (canAttack()) {
            if ( myUsedSpecialCase==true) {
                final int damage = new Random().nextInt(101) + 75; // 75 to 175 points of damage
                theOpponent.setHitPoints(theOpponent.getHitPoints() - damage);
                theOpponent.setAttacked(true);

                return; // exit method after performing special skill
            }
            calculateDamage(theOpponent); // if special skill is unsuccessful, perform normal attack
            theOpponent.setAttacked(true);
        } else {
            theOpponent.setAttacked(false);
            // report attack failure
        }

    }

    /**
     * Checks whether Warrior can use Crushing Blow skill.
     * Has 40% chance of succeeding.
     * @return true if Warrior can use special skill; otherwise false
     */
    private boolean useSpecialSkill() {
        final double randomValue = Math.random();
        return randomValue < 0.4;
    }

    /**
     * this is for testing
     * @param value
     */
    public void setspecialcase(boolean value){
        myUsedSpecialCase=value;
    }
}