package model;

public class Monster extends DungeonCharacter{
    private Double myHealChances;
    private int myMinHeal;
    private int myMaxHeal;
    protected Monster() {
        super();
    }
    public boolean heal(){ //boolean or int ??
        throw new UnsupportedOperationException("Method not yet implemented");
    }


}
