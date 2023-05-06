package model;

import java.util.ArrayList;
import java.util.Random;

public class Hero extends DungeonCharacter{
    private final int myMaxHealth;
    private Double myBlockChance;
    private final ArrayList<Item> myInventory;

    private int myHealth;
    protected Hero() {
        super();
        int minHealth = 75;
        int maxHealth = 100;
        myMaxHealth = new Random().nextInt(maxHealth + minHealth + 1) + minHealth;
        myInventory = new ArrayList<>();
    }
    private int regularAttack(DungeonCharacter d){
        throw new UnsupportedOperationException("Method not yet implemented");
    }
    private int specialSkill(DungeonCharacter d){
        throw new UnsupportedOperationException("Method not yet implemented");
    }
    public void addToInventory(Item theItem) {
        myInventory.add(theItem);
    }
    public void heal(int theHealthRestore) {
        myHealth += theHealthRestore;
        if (myHealth > myMaxHealth) {
            myHealth = myMaxHealth;
        }
    }
}
