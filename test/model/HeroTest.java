package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for {@link model.Hero}
 */
class HeroTest {
    private Warrior myWarrior;

    @BeforeEach
    void setUp() {
        myWarrior = new Warrior("Test Hero");
        myWarrior.setHitPoints(100);
    }

    /**
     * Test method for {@link model.Hero#useHealingPotion(int)}
     */
    @Test
    void testUseHealingPotion() {
        myWarrior.setHitPoints(100);
        myWarrior.useHealingPotion(20);
        assertEquals(120, myWarrior.getHitPoints());
    }

    /**
     * Test method for {@link model.Hero#useHealingPotion(int)}
     * Test scenario: Restore a number of hit points that will exceed the max number of hit points.
     */
    @Test
    void testUseHealingPotionExceedHitPoints() {
        myWarrior.useHealingPotion(50);
        assertEquals(125, myWarrior.getHitPoints());
    }

    /**
     * Test method for {@link Hero#toString}
     */
    @Test
    void testToString() {
        final Hero hero = new Hero("Test", 100, 0.8, 35, 60, 4, 0.2) {
        };
        final ArrayList<Item> items = new ArrayList<>();
        items.add(new VisionPotion());
        items.add(new HealingPotion());
        items.add(new HealingPotion());
        items.add(new PillarOfOO("Abstraction"));
        items.add(new PillarOfOO("Encapsulation"));
        hero.addToInventory(items);
        assertEquals("""
                        Test
                        Hit Points: 100
                        Total Healing Potions: 2
                        Total Vision Potions: 1
                        Pillar Piece(s) Found: Pillar of Abstraction, Pillar of Encapsulation""",
                hero.toString());
    }
}