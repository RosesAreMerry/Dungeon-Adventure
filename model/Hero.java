package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Hero extends DungeonCharacter{
    private final int myMaxHealth;
    private double myBlockChance;
    private ArrayList<Item> myInventory;
    private int myHealth;

    public Hero(final String theName, final int theHitPoints, final double theHitChance, final int theDamageMin,
                final int theDamageMax, final int theAttackSpeed, final double theBlockChance) {
        super(theName, theHitPoints, theHitChance, theDamageMin, theDamageMax, theAttackSpeed);
        this.myBlockChance = theBlockChance;
        final int minHealth = 75;
        final int maxHealth = 100;
        myHealth = getHitPoints();
        myMaxHealth = getHitPoints();
        myInventory = new ArrayList<>();
    }

    public double getMyBlockChance() {
        return myBlockChance;
    }

    private int Attack(final DungeonCharacter theCharacter) {
        return 0;
    }

    private int specialSkill(final DungeonCharacter theCharacter){
        throw new UnsupportedOperationException("Method not yet implemented");
    }

    /**
     * Add item to inventory.
     *
     * @param theItem the item to add to the inventory
     */
    public void addToInventory(final Item theItem) {
        myInventory.add(theItem);
    }
    public void removeFromInventory(final Item theItem) {
        myInventory.remove(theItem);
    }
    public ArrayList<Item> getMyInventory() {
        return myInventory;
    }
    public void useHealingPotion(final int theHealthRestore) {
        // ensure hit points do not exceed maximum hit points
        myHealth = Math.min(myHealth + theHealthRestore, myMaxHealth);
        setHitPoints(myHealth);
    }
    @Override
    public void attack(final DungeonCharacter theOpponent) {

    }
}