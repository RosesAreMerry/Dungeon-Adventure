package model;

import java.util.Random;
public class Warrior extends Hero{
    boolean usedSpecialSkill;
    boolean success;
    public Warrior(String theName) {
        super(theName, 125, 0.8, 35, 60, 4, 0.2);
        usedSpecialSkill= false;
        success=false;
    }

    /**
     * attacks the opponent and reduces hitpoints
     * @param theOpponent
     */
    @Override
    public void attack(DungeonCharacter theOpponent) { // how to assign the attacknumber to the correct person
        final int numofAttack = Math.max(1, this.getAttackSpeed()/ theOpponent.getAttackSpeed());
        if (canAttack()) {
            for (int i = 0; i < numofAttack; i++) {
                int damage = new Random().nextInt(getDamageMax() - getDamageMin() + 1)
                        + getDamageMin();
                theOpponent.setHitPoints(theOpponent.getHitPoints() - damage);
            }
            if(usedSpecialSkill){
                int myExtradamage=SpecialSkill(theOpponent);
                theOpponent.setHitPoints(getHitPoints()-myExtradamage);
            }
     }
        success= true;
        if(success){
            System.out.println("the attack was successful ");
        }
        else{
            System.out.println("the attack was not successful ");
     }
}

/**
* this method reduces the opponents  if  the used sucessfully
* @param theOpponent
* @return the damage caused
*/
    public int SpecialSkill(DungeonCharacter theOpponent) {
        //int extradamage;
        Random rand = new Random();
        int damage=0;
        if(rand.nextDouble()<.4){
           damage= rand.nextInt(101)+75;
        }
        usedSpecialSkill=true;
        return damage;
    }
}