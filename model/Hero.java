package model;
public abstract class Hero extends DungeonCharacter{
    private Double myBlockChance;

    protected Item[] myInventory;
    protected Hero(String theName, int theHitPoints, Double theHitChance, int theDamageMin, int theDamageMax, int theAttackSpeed,Double theBlockChance) {
        super(theName,  theHitPoints,  theHitChance,  theDamageMin,  theDamageMax, theAttackSpeed);
        this.myBlockChance= theBlockChance;
    }

    public Double getMyBlockChance(){
        return myBlockChance;
    }

    public void setMyBlockChance(Double theBlockchance){
        myBlockChance=theBlockchance;

    }



    private int Attack(DungeonCharacter d) {
        return 0;
    }

    private int specialSkill(DungeonCharacter d){
        throw new UnsupportedOperationException("Method not yet implemented");
    }
}
