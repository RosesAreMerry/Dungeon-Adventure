package model;

import java.util.Random;

public class HealthPotion extends Potion {
    private static final int MIN_HEALING = 5;
    private static final int MAX_HEALING = 15;
    public HealthPotion() {
        super("Health Potion");
    }
    @Override
    public void use(Hero theHero) {
        int healthRestore = new Random().nextInt(MAX_HEALING - MIN_HEALING + 1) + MIN_HEALING;
        theHero.useHealingPotion(healthRestore);
    }
}
