package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HeroTest {
    private Warrior myWarrior;
    @BeforeEach
    void setUp() {
        myWarrior = new Warrior("Test Hero");
        myWarrior.setHitPoints(100);
    }

    @Test
    void testUseHealingPotion() {
        myWarrior.setHitPoints(100);
        myWarrior.useHealingPotion(20);
        assertEquals(120, myWarrior.getHitPoints());
    }

    @Test
    void testUseHealingPotionExceedHitPoints() {
        myWarrior.useHealingPotion(50);
        assertEquals(125, myWarrior.getHitPoints());
    }
}