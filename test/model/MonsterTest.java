package model;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MonsterTest {

    /**
     * Test method for {@link Monster#heal()}
     */
    @Test
    void testMonsterHeal() {
        MonsterFactory mf = new MonsterFactory();
        Monster gremlin = mf.createMonster("Gremlin");
        gremlin.myCurrentHitPoints = 10;
        MockRandom mr = new MockRandom();
        mr.setMockDouble(0.2);
        mr.setMockInt(10);
        gremlin.heal();
        assertTrue(gremlin.myCurrentHitPoints >= 30 && gremlin.myCurrentHitPoints <= 40);
    }

    @Test
    void attack() {
    }

    /**
     * Class to mock behavior of Random
     */
    static class MockRandom extends Random {
        private double myMockDouble;
        private int myMockInt;
        public void setMockDouble(double myDouble) {
            this.myMockDouble = myDouble;
        }
        public void setMockInt(int myInt) {
            this.myMockInt = myInt;
        }
        @Override
        public double nextDouble() {
            return myMockDouble;
        }
        @Override
        public int nextInt() {
            return myMockInt;
        }
    }
}
