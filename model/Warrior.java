package model;

import java.util.Random;

public class Warrior extends Hero{
    boolean usedSpecialSkill=false;
    public Warrior(String theName) {
        super(theName, 125, 0.8, 35, 60, 4, 0.2);
    }



    @Override
    public int attack(DungeonCharacter opponent) {
        int attaccknumber = getNumAttacks(opponent);
        if (canAttack()) {
            for (int i = 0; i < attaccknumber; i++) {
                int damage = new Random().nextInt(getMyDamageMax() - getMyDamageMin() + 1)
                        + getMyDamageMin();
                setHitPoints(getHitPoints() - damage);
            }
        }
        return 0;
    }
    public int SpecialSkill(DungeonCharacter opponent) {
        //int extradamage;
        Random rand = new Random();
        int extradamage=0;
        if(rand.nextDouble()<.4){
            extradamage= rand.nextInt(101)+75;
        }
        usedSpecialSkill=true;
        return extradamage;
    }
}