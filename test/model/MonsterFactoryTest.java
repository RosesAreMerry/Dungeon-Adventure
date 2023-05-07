package model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MonsterFactoryTest {
    @Test
    void testCreateMonster() {
        MonsterFactory mf = new MonsterFactory();

        Monster ogre = mf.createMonster("Ogre");
        assertEquals("Monster: Ogre" +
                "\nHit Points: 200" +
                "\nChance to Hit: 0.6" +
                "\nMinimum Damage: 30" +
                "\nMaximum Damage: 60" +
                "\nAttack Speed: 2" +
                "\nChance to Heal: 0.1" +
                "\nMinimum Heal Points: 30" +
                "\nMaximum Heal Points: 60", ogre.toString());
    }
}