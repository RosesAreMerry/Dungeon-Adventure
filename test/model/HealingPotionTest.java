package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for {@link model.HealingPotion}
 */
class HealingPotionTest {

    Hero myHero;
    HealingPotion myHealingPotion;

    @BeforeEach
    void setUp() {
        myHealingPotion = new HealingPotion();
        myHero = new Warrior("Test Hero");
    }

    /**
     * Test method for {@link HealingPotion#use(Hero)}
     */
    @Test
    void testUse() {
        myHero.setHitPoints(90);
        myHealingPotion.use(myHero);
        assertTrue(myHero.getHitPoints() > 90);
    }

    /**
     * Test method for {@link HealingPotion#use(Hero)}
     * Test scenario: When health exceeds max hit points after performing heal
     */
    @Test
    void testUseExceedMaxHitPoints() {
        myHero.setHitPoints(125);
        myHealingPotion.use(myHero);
        assertEquals(125, myHero.getHitPoints());
    }
}