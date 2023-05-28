package model;
/**
 * test class for warrior
 */

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class WarriorTest extends RandomMock{
    private Warrior myWarrior;
    private DungeonCharacter opponent;
    private RandomMock rm;//mock object

    @BeforeEach
    public void setUp() {
        rm = new RandomMock();
        rm.setMockIntValue(105);//
        myWarrior = new Warrior("TestWarrior");
        myWarrior.setRandom(rm);
        opponent=new Monster("Gremlin",200,.6,
                30,60,2,.1,30,60);
        }

    @Test
    public void testAttackSpecialSkillFalse() {
        System.out.println(opponent.getHitPoints());
        rm.setMockDoubleValue(0.0); // ensure that attack hits
        myWarrior.setSpecialCase(false);
        myWarrior.attack(opponent);
        System.out.println(opponent.getHitPoints());
        Assertions.assertTrue(opponent.getHitPoints() < 200);
    }
    @Test
    public void testAttackSpecialSkillTrue() {
        rm.setMockIntValue(60);
        myWarrior.setSpecialCase(true);
        myWarrior.attack(opponent);
        int expectedHitPoints =65; //200-135=95
        int actualHitPoints = opponent.getHitPoints();
        Assertions.assertEquals(expectedHitPoints, actualHitPoints);
    }
}

