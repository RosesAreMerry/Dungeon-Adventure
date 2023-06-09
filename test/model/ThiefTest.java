package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Test class for {@link model.Thief}
 */
class ThiefTest {
    MonsterFactory myMonsterFactory;
    Monster myOpponent;
    Thief myThief;
    RandomMock myRandom;

    @BeforeEach
    void setUp() {
        myMonsterFactory = new MonsterFactory();
        myOpponent = myMonsterFactory.createMonsterByName("Ogre");
        myThief = new Thief("Test Thief") {
            @Override
            protected boolean canAttack() {
                return true;
            }
        };
        myRandom = new RandomMock();
        myRandom.setMockIntValue(30); // damage = 30 + 20 = 50
        myThief.setMyRandom(myRandom);
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
        myThief = new Thief("Special") {
            @Override
            boolean wasCaught() {
                return false;
            }

            @Override
            boolean useSpecialSkill() {
                return true;
            }
        };
        myRandom.setMockDoubleValue(0.0);
        myThief.setRandom(myRandom);
        myThief.setMyRandom(myRandom);
        myThief.attack(myOpponent);
        assertEquals(0, myOpponent.getHitPoints());
    }

    /**
     * Test method for {@link model.Thief#attack(DungeonCharacter)}
     * Test scenario: If Thief is neither caught nor able use their special skill (perform a normal attack)
     */
    @Test
    void testNormalAttack() {
        myThief.setRandom(myRandom);
        myRandom.setMockDoubleValue(0.8);
        myThief.attack(myOpponent);
        assertEquals(50, myOpponent.getHitPoints());
    }
}