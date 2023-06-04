package model;

import java.util.Random;

/**
 * Represents a healing potion item that can be used by the player.
 *
 * @author Chelsea Dacones
 */
public class HealingPotion extends Potion {
    private static final int MIN_HEALING = 5;
    private static final int MAX_HEALING = 15;

    /**
     * Constructs a HealingPotion object.
     * Sets the name of the potion as "Healing Potion".
     */
    public HealingPotion() {
        super("Healing Potion");
    }

    /**
     * Uses the healing potion to restore health to the player.
     *
     * @param theHero the hero using the healing potion
     */
    @Override
    public void use(final Hero theHero) {
        final int healthRestore = new Random().nextInt(MAX_HEALING - MIN_HEALING + 1) + MIN_HEALING;
        theHero.useHealingPotion(healthRestore);
        theHero.removeFromInventory(this);
    }
}
