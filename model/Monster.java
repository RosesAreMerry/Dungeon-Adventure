package model;

public abstract class Monster extends DungeonCharacter{
    private Double myHealChances;
    private int myMinHeal;
    private int myMaxHeal;

    /**
     * initialize the fields
     *
     * @param theName
     * @param theHitPoints
     * @param theHitChance
     * @param theDamageMin
     * @param theDamageMax
     * @param theAttackSpeed
     */
    protected Monster(String theName, int theHitPoints, Double theHitChance, int theDamageMin, int theDamageMax, int theAttackSpeed) {
        super(theName, theHitPoints, theHitChance, theDamageMin, theDamageMax, theAttackSpeed);
    }

    //    protected Monster() {
//
//
//    }
    public boolean heal(){ //boolean or int ??
        throw new UnsupportedOperationException("Method not yet implemented");
    }


    @Override
    public int attack(DungeonCharacter opponent) {
        return 0;
    }
}
