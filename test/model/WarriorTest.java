package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Test class for {@link model.Warrior}
 */
public class WarriorTest extends RandomMock {
    private Warrior myWarrior;
    private DungeonCharacter myOpponent;
    private RandomMock myRandomMock;

    @BeforeEach
    public void setUp() {
        myRandomMock = new RandomMock();
        myRandomMock.setMockIntValue(30);
        myWarrior = new Warrior("Test Warrior") {
            @Override
            protected boolean canAttack() {
                return true;
            }
        };
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
        myWarrior.setRandom(myRandomMock);
        myWarrior.attack(myOpponent);
        assertTrue(myOpponent.getHitPoints() < 200);
    }

    /**
     * Test method for {@link model.Warrior#attack(DungeonCharacter)}
     * Test scenario: Perform attack with special skill ("Crushing Blow" (75 to 175 points of damage))
     */
    @Test
    public void testAttackSpecialSkillTrue() {
        final Warrior warrior = new Warrior("Test Warrior") {
            @Override
            protected boolean useSpecialSkill() {
                return true;
            }
        };
        warrior.setMyRandom(myRandomMock);
        warrior.attack(myOpponent);
        assertEquals(95, myOpponent.getHitPoints());
    }
}

