package model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MonsterFactoryTest {

    /**
     * Test method for {@link model.MonsterFactory#createMonsterByName(String)} (String)}
     */
    @Test
    void testCreateMonsterByName() {
        final MonsterFactory mf = new MonsterFactory();
        final Monster ogre = mf.createMonsterByName("Ogre");
        assertEquals("Ogre" +
                "\nHit Points: 200" +
                "\nChance to Hit: 0.6" +
                "\nMinimum Damage: 30" +
                "\nMaximum Damage: 60" +
                "\nAttack Speed: 2" +
                "\nChance to Heal: 0.1" +
                "\nMinimum Heal Points: 30" +
                "\nMaximum Heal Points: 60", ogre.toString());
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