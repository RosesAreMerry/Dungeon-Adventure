package model;

public class Hero extends DungeonCharacter{
    private Double myBlockChance;
    //private Item[] myInventory;
    private int myHealth;

    protected Hero() {
        super();
    }
    private int regularAttack(DungeonCharacter d){
        throw new UnsupportedOperationException("Method not yet implemented");
    }
    private int specialSkill(DungeonCharacter d){
        throw new UnsupportedOperationException("Method not yet implemented");
    }

    public void heal(int theHealthRestore) {
        myHealth += theHealthRestore;
        int maxHealth = 100;
        if (myHealth > maxHealth) {
            myHealth = maxHealth;
        }
    }
}
