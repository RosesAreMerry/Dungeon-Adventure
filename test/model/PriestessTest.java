package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Test class for {@link model.Priestess}
 */
public class PriestessTest extends RandomMock {
    private Priestess myPriestess;
    private DungeonCharacter myOpponent;

    @BeforeEach
    public void setUp() {
        final RandomMock randomMock = new RandomMock();
        randomMock.setMockIntValue(20);//
        myPriestess = new Priestess("Hero") {
            @Override
            protected boolean canAttack() {
                return true;
            }

            @Override
            boolean canUseSpecialSkill() {
                return true;
            }
        };
        myPriestess.setMyRandom(randomMock);
        myPriestess.setRandom(randomMock);
        randomMock.setMockIntValue(10);
        myOpponent = new Monster("Gremlin",200,0.6,
                30,60,2,0.1,30,60);

    }

    /**
     * Test method for {@link model.Priestess#attack(DungeonCharacter)}
     */
    @Test
    public void testAttack() {
        myPriestess.attack(myOpponent);
        assertEquals(130, myOpponent.getHitPoints());
    }

    /**
     * Test method for {@link model.Priestess#heal()}
     */
    @Test
    public void testHeal() {
        myPriestess.setHitPoints(30);
        myPriestess.heal(); // heels by 11 hit points
        assertEquals(41, myPriestess.getHitPoints());
    }

    /**
     * Test method for {@link model.Priestess#toString()}
     */
    @Test
    void testToString() {
        assertEquals("""
                Hero the Priestess
                Hit Points: 75
                Chance to Hit: 0.7
                Minimum Damage: 25
                Maximum Damage: 45
                Attack Speed: 5
                Block Chance: 0.3
                Special Skill: Healing""",
                myPriestess.toString());
    }
}
