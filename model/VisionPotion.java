package model;

/**
 * Represents a vision potion item that can be used by the player.
 *
 * @author Chelsea Dacones
 * @author Rosemary Roach
 */
public class VisionPotion extends Potion {

    /**
     * Constructs a VisionPotion object.
     * Sets the name of the potion as "Vision Potion"
     */
    public VisionPotion() {
        super("Vision Potion");
    }

    /**
     * Uses the vision potion to give the hero the ability to see adjacent rooms in the dungeon.
     *
     * @param theHero the hero using the vision potion
     */
    @Override
    public void use(final Hero theHero) {
        theHero.startVisionPotion();
        theHero.removeFromInventory(this);
    }
}
