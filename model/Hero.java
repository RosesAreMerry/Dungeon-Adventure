package model;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Represents a Hero/Adventurer in the game.
 *
 * @author Chelsea Dacones
 */
public abstract class Hero extends DungeonCharacter {
    public static final int VISION_POTION_TURNS = 3;
    private final double myBlockChance;
    private final ArrayList<Item> myInventory;
    private int myHealth;
    private int myVisionPotionTurns;
    @Serial
    private static final long serialVersionUID = 4434118078796032667L;

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

    public Double getblockChance(){
        return myBlockChance;
    }


    /**
     * Starts the effect of the vision potion.
     * */
    void startVisionPotion() {
        myVisionPotionTurns = VISION_POTION_TURNS;
    }

    /**
     * Reduces the number of turns the vision potion is active.
     * Once the number of turns reaches 0, the potion is no longer active.
     * */
    public void reduceVisionPotionTurns() {
        myVisionPotionTurns--;
    }

    /**
     * Returns true if the vision potion is active.
     * */
    public boolean isVisionPotionActive() {
        return myVisionPotionTurns > 0;
    }

    @Override
    public String toString() {
        return  "Hero: " + this.getName() + " the "
                + this.getClass().getSimpleName() +
                "\nHit Points: " + getHitPoints() +
                "\nChance to Hit: " + getHitChance() +
                "\nMinimum Damage: " + getDamageMin() +
                "\nMaximum Damage: " + getDamageMax() +
                "\nAttack Speed: " + getAttackSpeed() +
                "\nmyBlockChance: "+ getblockChance();
    }
}