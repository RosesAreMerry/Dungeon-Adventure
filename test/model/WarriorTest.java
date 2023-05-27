package model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test class for Warrior
 */
public class WarriorTest extends RandomMock {
    private Warrior myWarrior;
    private DungeonCharacter myOpponent;
    private RandomMock myRandomMock;

    @BeforeEach
    public void setUp() {
        myRandomMock = new RandomMock();
        myRandomMock.setMockIntValue(105);//
        myWarrior = new Warrior("Test Warrior");
        myWarrior.setMyRandom(myRandomMock);
        myOpponent = new Monster("Gremlin", 200, .6,
                30, 60, 2, .1, 30, 60);
    }

    /**
     * Test method for {@link model.Warrior#attack(DungeonCharacter)}
     * Test scenario: Perform a normal attack (no special skill (Crushing Blow))
     */
    @Test
    public void testAttackSpecialSkillFalse() {
        myWarrior.attack(myOpponent);
        Assertions.assertTrue(myOpponent.getHitPoints() < 200);
    }

    /**
     * Test method for {@link model.Warrior#attack(DungeonCharacter)}
     * Test scenario: Perform attack with special skill ("Crushing Blow" (75 to 175 points of damage))
     */
    @Test
    public void testAttackSpecialSkillTrue() {
        myRandomMock.setMockIntValue(60);
        myWarrior.attack(myOpponent);
        final int expectedHitPoints = 65; // 200 - 135 = 95
        final int actualHitPoints = myOpponent.getHitPoints();
        Assertions.assertEquals(expectedHitPoints, actualHitPoints);
    }
}

