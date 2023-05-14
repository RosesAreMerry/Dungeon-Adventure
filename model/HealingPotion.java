package model;

import java.util.Random;

public class HealingPotion extends Potion {
    private static final int MIN_HEALING = 5;
    private static final int MAX_HEALING = 15;
    public HealingPotion() {
        super("Healing Potion");
    }
    @Override
    public void use(final Hero theHero) {
        final int healthRestore = new Random().nextInt(MAX_HEALING - MIN_HEALING + 1) + MIN_HEALING;
        theHero.useHealingPotion(healthRestore);
        theHero.removeFromInventory(this);
    }
}
