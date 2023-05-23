package model;
/**
 * test class for warrior
 */

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class WarriorTest extends RandomMock{
    private Warrior mywarrior;
    private DungeonCharacter opponent;
    private RandomMock rm;//mock object

    @BeforeEach
    public void setUp() {
        rm = new RandomMock();
        rm.setMockIntValue(105);//
        mywarrior = new Warrior("TestWarrior");
        mywarrior.setMyRandom(rm);
        opponent=new Monster("gremlin",200,.6,
                30,60,2,.1,30,60);
        }

    @Test
    public void testAttackSpecialSkillFalse() {
        mywarrior.attack(opponent);
        Assertions.assertTrue(opponent.getHitPoints() < 200);
    }
    @Test
    public void testAttackSpecialSkilltrue() {
        rm.setMockIntValue(60);
//        mywarrior.setSpecialCase(true);
        mywarrior.attack(opponent);
        int expectedHitPoints =65; //200-135=95
        int actualHitPoints = opponent.getHitPoints();
        Assertions.assertEquals(expectedHitPoints, actualHitPoints);
    }
}

