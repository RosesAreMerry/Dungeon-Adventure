package model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Random;
public class PriestessTest extends RandomMock {
    private Priestess mypriestess;
    private DungeonCharacter opponent;
    RandomMock rm;


    @BeforeEach
    public void setUp() {
        rm = new RandomMock();
        rm.setMockIntValue(20);//
        mypriestess = new Priestess("Priestess");
        mypriestess.setMyRandom(rm);
        opponent=new Monster("gremlin",200,.6,
                30,60,2,.1,30,60);

    }
    /**
     * attack the opponent
     */
    @Test
    public void testAttack() {
        mypriestess.attack(opponent);
        Assertions.assertTrue(opponent.getHitPoints()<200);
    }
    @Test
    public void testHeal() {
        rm.setMockIntValue(20); // heal amount= 20+1=21
        int initialHitPoints = mypriestess.getHitPoints();
        mypriestess.setHitPoints(25);
        mypriestess.heal();
        int expectedHitPoints=46;
        int actualHitPoints = mypriestess.getHitPoints();
        Assertions.assertEquals(expectedHitPoints, actualHitPoints);
    }
}
