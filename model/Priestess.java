package model;

public class Priestess extends Hero{

    protected Priestess(String theName, int theHitPoints, Double theHitChance, int theDamageMin, int theDamageMax, int theAttackSpeed, Double theBlockChance) {
        super(theName, theHitPoints, theHitChance, theDamageMin, theDamageMax, theAttackSpeed, theBlockChance);
    }

    @Override
    public void attack(DungeonCharacter opponent) {

    }
}
