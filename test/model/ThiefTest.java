package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ThiefTest {
    MonsterFactory myMonsterFactory;
    Monster myOpponent;
    Thief myThief;
    RandomMock myRandom;
    @BeforeEach
    void setUp() {
        myMonsterFactory = new MonsterFactory();
        myOpponent = myMonsterFactory.createMonster("Ogre");
        myThief = new Thief("Test Thief");
        myRandom = new RandomMock();
        myRandom.setMockIntValue(30); // damage = 30 + 20 = 50
        myThief.setRandom(myRandom);
    }

    /**
     * Test method for {@link model.Thief#attack(DungeonCharacter)}
     * Test scenario: If Thief is caught (attack should not happen)
     */
    @Test
    void testAttackIfCaught() {
        myRandom.setMockDoubleValue(0.1);
        myThief.attack(myOpponent);
        assertEquals(200, myOpponent.getHitPoints());
    }

    /**
     * Test method for {@link model.Thief#attack(DungeonCharacter)}
     * Test scenario: If Thief can use special skill (get an extra attack)
     */
    @Test
    void testSpecialAttack() {
        myRandom.setMockDoubleValue(0.3);
        myThief.attack(myOpponent);
        assertEquals(0 ,myOpponent.getHitPoints());
    }

    /**
     * Test method for {@link model.Thief#attack(DungeonCharacter)}
     * Test scenario: If Thief is neither caught nor able use their special skill (perform a normal attack)
     */
    @Test
    void testNormalAttack() {
        myRandom.setMockDoubleValue(0.5);
        myThief.attack(myOpponent);
        assertEquals(50 ,myOpponent.getHitPoints());
    }
}