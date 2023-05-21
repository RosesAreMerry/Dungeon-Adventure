package model;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MonsterTest extends RandomMock{

    private final Monster myGremlin = new Monster("Gremlin", 70, 0.8, 15,
            30, 5, 0.4, 20, 40) {
        @Override
        public boolean isAttacked() {
            return true;
        }

        @Override
        public boolean isFainted() {
            return super.isFainted();
        }
    };

    /**
     * Test method for {@link model.Monster#heal()}
     * Test situation: A successful heal (monster can heal based on chance to heal,
     * and if they've been attacked and have not fainted)
     */
    @Test
    void testSuccessfulHeal() {
        final RandomMock rm = new RandomMock();
        rm.setMockDoubleValue(0.2); // 0.2 < 0.4
        rm.setMockIntValue(10); // healAmount = 10 + 20 = 30
        myGremlin.setMyRandom(rm);
        myGremlin.setHitPoints(20); // myCurrentHitPoints = 20
        myGremlin.heal(); // myCurrentHitPoints = 20 + 30 = 50; min of 50 and 70
        assertEquals(50, myGremlin.getHitPoints());
    }

    /**
     * Test method for {@link model.Monster#heal()}
     * Test situation: Healing multiple times.
     */
    @Test
    void testMultipleHeals() {
        final RandomMock rm = new RandomMock();
        rm.setMockDoubleValue(0.2); // 0.2 < 0.4
        rm.setMockIntValue(10); // healAmount = 10 + 20 = 30
        myGremlin.setMyRandom(rm);
        myGremlin.setHitPoints(20); // myCurrentHitPoints = 20
        myGremlin.heal(); // myCurrentHitPoints = 20 + 30 = 50; min of 50 and 70
        myGremlin.heal(); // myCurrentHitPoints = 50 + 30 = 80; min of 80 and 70
        assertEquals(70, myGremlin.getHitPoints());
    }

    /**
     * Test method for {@link model.Monster#heal()}
     * Test situation: When adding heal amount to current hit points exceeds
     * the max hit points of the Monster
     */
    @Test
    void testExceedMaxHitPointsHeal() {
        final RandomMock rm = new RandomMock();
        rm.setMockDoubleValue(0.3); // 0.3 < 0.4
        rm.setMockIntValue(5); // healAmount = 5 + 20 = 25
        myGremlin.setMyRandom(rm);
        myGremlin.setHitPoints(50); // myCurrentHitPoints = 50
        myGremlin.heal(); // myCurrentHitPoints = min of 75 and 70
        assertEquals(70, myGremlin.getHitPoints());
    }

    /**
     * Test method for {@link model.Monster#heal()}
     * Test situation: An unsuccessful heal (monster cannot heal based on chance to heal)
     */
    @Test
    void testUnsuccessfulHeal() {
        final RandomMock rm = new RandomMock();
        final MonsterFactory mf = new MonsterFactory();
        final Monster monster = mf.createMonster("Gremlin");
        rm.setMockDoubleValue(0.5); // 0.5 ≮ 0.4
        rm.setMockIntValue(10);
        monster.setMyRandom(rm);
        monster.setHitPoints(50);
        monster.heal();
        assertEquals(50, monster.getHitPoints());
    }

    /**
     * Test method for {@link model.Monster#attack(DungeonCharacter)}
     * Test scenario: A successful attack (monster can attack based on chance to hit)
     */
    @Test
    void testSuccessfulAttack() {
        final RandomMock rm = new RandomMock();
        final MonsterFactory mf = new MonsterFactory();
        final Monster ogre = mf.createMonster("Ogre");
        final Monster gremlin = new Monster("Gremlin", 70, 0.8, 15,
                30, 5, 0.4, 20, 40) {
            @Override
            public boolean canAttack() {
                return true;
            }
        };
        rm.setMockIntValue(5); // damage = 5 + 15 = 20
        gremlin.setMyRandom(rm);
        gremlin.attack(ogre); // gremlin gets two attacks; ogre hit points = 200 - 20 - 20 = 160
        assertEquals(160, ogre.getHitPoints());
    }

    /**
     * Test method for {@link model.Monster#attack(DungeonCharacter)}
     * Test scenario: An unsuccessful attack (monster cannot attack based on chance to hit)
     */
    @Test
    void testUnsuccessfulAttack() {
        final RandomMock rm = new RandomMock();
        final MonsterFactory mf = new MonsterFactory();
        final Monster ogre = mf.createMonster("Ogre");
        final Monster gremlin = new Monster("Gremlin", 70, 0.8, 15,
                30, 5, 0.4, 20, 40) {
            @Override
            public boolean canAttack() {
                return false;
            }
        };
        rm.setMockIntValue(5);
        gremlin.setMyRandom(rm);
        gremlin.attack(ogre);
        assertEquals(200, ogre.getHitPoints());
    }

}
