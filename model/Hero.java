package model;
public abstract class Hero extends DungeonCharacter{
    private Double myBlockChance;
    private Item[] myInventory;
    protected Hero(String theName, int theHitPoints, Double theHitChance, int theDamageMin, int theDamageMax, int theAttackSpeed,Double theBlockChance) {
        super(theName,  theHitPoints,  theHitChance,  theDamageMin,  theDamageMax, theAttackSpeed);
        this.myBlockChance= theBlockChance;
    }
    private int Attack(DungeonCharacter d){
        throw new UnsupportedOperationException("Method not yet implemented");
    }
    private int specialSkill(DungeonCharacter d){
        throw new UnsupportedOperationException("Method not yet implemented");
    }
}
