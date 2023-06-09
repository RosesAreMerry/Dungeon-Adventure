package model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for {@link model.MonsterFactory}
 */
class MonsterFactoryTest {

    /**
     * Test method for {@link model.MonsterFactory#createMonsterByName(String)} (String)}
     */
    @Test
    void testCreateMonsterByName() {
        final MonsterFactory mf = new MonsterFactory();
        final Monster ogre = mf.createMonsterByName("Ogre");
        assertEquals("""
                Ogre
                Hit Points: 200
                Chance to Hit: 0.6
                Minimum Damage: 30
                Maximum Damage: 60
                Attack Speed: 2
                Chance to Heal: 0.1
                Minimum Heal Points: 30
                Maximum Heal Points: 60""", ogre.toString());
    }

    /**
     * Test method for {@link model.MonsterFactory#createMonsterByName(String)} (String)}
     * Test scenario: Create Monster that does not exist
     */
    @Test
    void testCreateMonsterByNameUnknown() {
        final MonsterFactory mf = new MonsterFactory();
        final Exception exception = assertThrows(RuntimeException.class, () -> mf.createMonsterByName("Yeti"));
        assertEquals("No Monster with the provided name was found in the database.", exception.getMessage());
    }
}