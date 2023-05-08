package model;

import java.util.ArrayList;
import java.util.Random;

public class Hero extends DungeonCharacter{
    private final int myMaxHealth;
    private double myBlockChance;
    private final ArrayList<Item> myInventory;
    private int myHealth;
    public Hero(String theName, int theHitPoints, double theHitChance, int theDamageMin,
                int theDamageMax, int theAttackSpeed, double theBlockChance) {
        super(theName, theHitPoints, theHitChance, theDamageMin, theDamageMax, theAttackSpeed);
        this.myBlockChance = theBlockChance;
        int minHealth = 75;
        int maxHealth = 100;
        myHealth = getHitPoints();
        myMaxHealth = new Random().nextInt(maxHealth + minHealth + 1) + minHealth;
        myInventory = new ArrayList<>();
    }

    public double getMyBlockChance(){
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

    /**
     * Add item to inventory.
     *
     * @param theItem the item to add to the inventory
     */
    public void addToInventory(Item theItem) {
        myInventory.add(theItem);
    }

    public void useHealingPotion(int theHealthRestore) {
        myHealth += theHealthRestore;
        if (myHealth > myMaxHealth) {
            myHealth = myMaxHealth;
        }
    }
    @Override
    public void attack(DungeonCharacter theOpponent) {

    }
}