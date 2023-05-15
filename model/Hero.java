package model;

import java.util.ArrayList;

/**
 * Represents a Hero/Adventurer in the game.
 *
 * @author Chelsea Dacones
 */
public abstract class Hero extends DungeonCharacter {
    private final int myMaxHealth;
    private final double myBlockChance;
    private final ArrayList<Item> myInventory;
    private int myHealth;
    private final int myMaxHitPoints;

    public Hero(final String theName, final int theHitPoints, final double theHitChance, final int theDamageMin,
                final int theDamageMax, final int theAttackSpeed, final double theBlockChance) {
        super(theName, theHitPoints, theHitChance, theDamageMin, theDamageMax, theAttackSpeed);
        this.myBlockChance = theBlockChance;
        this.myMaxHitPoints = theHitPoints;
        myHealth = getHitPoints();
        myMaxHealth = getHitPoints();
        myInventory = new ArrayList<>();
    }

    /**
     * Add item to inventory.
     *
     * @param theItem the item to add to the inventory
     */
    public void addToInventory(final Item theItem) {
        myInventory.add(theItem);
    }

    /**
     * Remove item from inventory.
     *
     * @param theItem the item to remove from the inventory
     */
    public void removeFromInventory(final Item theItem) {
        myInventory.remove(theItem);
    }

    /**
     * Retrieve the character's inventory.
     *
     * @return the inventory.
     */
    public ArrayList<Item> getMyInventory() {
        return myInventory;
    }

    protected void useHealingPotion(final int theHealthRestore) {
        // ensure hit points do not exceed maximum hit points
        myHealth = Math.min(myHealth + theHealthRestore, myMaxHealth);
        setHitPoints(myHealth);
    }

    /**
     * @return boolean value determining if it can block or not
     */
    protected boolean canBlock() {
        final double randomValue = Math.random();
        return randomValue < myBlockChance;
    }

    protected int getMaxHitPoints() {
        return myMaxHitPoints;
    }
}