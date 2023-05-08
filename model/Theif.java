package model;

public class Theif extends Hero{
    protected Theif(String theName, int theHitPoints, Double theHitChance, int theDamageMin, int theDamageMax, int theAttackSpeed, Double theBlockChance) {
        super(theName, theHitPoints, theHitChance, theDamageMin, theDamageMax, theAttackSpeed, theBlockChance);
    }

    @Override
    public int attack(DungeonCharacter opponent) {
        return 0;
    }
}
