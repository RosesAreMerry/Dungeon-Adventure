package model;

import java.util.ArrayList;

/**
 * Represents a Hero/Adventurer in the game.
 *
 * @author Chelsea Dacones
 */
public abstract class Hero extends DungeonCharacter {
    private final double myBlockChance;
    private final ArrayList<Item> myInventory;
    private int myHealth;

    protected Hero(final String theName, final int theHitPoints, final double theHitChance, final int theDamageMin,
                final int theDamageMax, final int theAttackSpeed, final double theBlockChance) {
        super(theName, theHitPoints, theHitChance, theDamageMin, theDamageMax, theAttackSpeed);
        myBlockChance = theBlockChance;
        myHealth = getHitPoints();
        myInventory = new ArrayList<>();
    }

    public void addToInventory(final ArrayList<Item> theItem) {
        myInventory.addAll(theItem);
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
        myHealth = Math.min(getHitPoints() + theHealthRestore, getMaxHitPoints());
        setHitPoints(myHealth);
    }

    @Override
    protected boolean canBlockAttack() {
        final double randomValue = Math.random();
        return randomValue <= myBlockChance;
    }
}