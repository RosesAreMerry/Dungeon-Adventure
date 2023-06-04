package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import view.AdventureView;
import view.CombatView;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.function.Consumer;
import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.*;

class CombatTest extends RandomMock {
    private DungeonCharacter myPlayer;
    private DungeonCharacter myOpponent;
    private RandomMock myRandom;
    private Combat myCombat;

    @BeforeEach
    void setUp() {
        myRandom = new RandomMock();
        myCombat = new Combat();
        myPlayer = new DungeonCharacter("Adventurer", 125, 1.0,
                30, 60, 4) {
        };
        myOpponent = new DungeonCharacter("Monster", 70, 1.0,
                15, 30, 2) {
        };
        myPlayer.setRandom(myRandom);
        myOpponent.setRandom(myRandom);
    }

    /**
     * Test method for {@link model.Combat#initiateCombat(DungeonCharacter, DungeonCharacter)}
     * Test scenario: A short battle between two characters where only
     * one character is able to inflict damage on the other.
     */
    @Test
    void testInitiateCombatShortBattle() {
        myRandom.setMockIntValue(5);
        myCombat.initiateCombat(myPlayer, myOpponent);
        assertEquals(0, myOpponent.getHitPoints());
        assertEquals(125, myPlayer.getHitPoints());
    }

    /**
     * Test method for {@link model.Combat#initiateCombat(DungeonCharacter, DungeonCharacter)}
     * Test scenario: A battle where both characters inflict damage on one another.
     */
    @Test
    void testInitiateCombatLongerBattle() {
        myRandom.setMockIntValue(1);
        myCombat.initiateCombat(myPlayer, myOpponent);
        assertEquals(0, myOpponent.getHitPoints());
        assertEquals(109, myPlayer.getHitPoints());
    }

    @Test
    void testInitiateCombatWithHealing() {
        final MockDungeonCharacter adventurer = new MockDungeonCharacter("Test Hero", 150, 1.0,
                10, 20, 4) {};
        final MockDungeonCharacter monster = new MockDungeonCharacter("Test Monster", 100, 1.0,
                5, 15, 2) {};
        myRandom.setMockIntValue(10);
        myCombat.initiateCombat(adventurer, monster);
        assertEquals(0, monster.getHitPoints());
        assertEquals(150, adventurer.getHitPoints());
    }

    @Test
    void testInitiateCombatUnsuccessful() {
        final DungeonCharacter hero = new DungeonCharacter("Hero", 125, 0.0,
                30, 60, 4) {
        };
        myCombat.initiateCombat(hero, myOpponent);
        assertEquals(70, myOpponent.getHitPoints());
    }

    private static class MockDungeonCharacter extends DungeonCharacter implements Healable {
        private int myHealAmount;
        private int myTotalDamage;
        private Random myRandom;

        public MockDungeonCharacter(String theName, int theHitPoints, double theHitChance, int theDamageMin, int theDamageMax, int theAttackSpeed) {
            super(theName, theHitPoints, theHitChance, theDamageMin, theDamageMax, theAttackSpeed);
            myHealAmount = 0;
            myTotalDamage = 0;
            myRandom = new RandomMock();
        }

        @Override
        public void attack(final DungeonCharacter theOpponent) {
            // check if character can attack based on chance to hit
            if (canAttack()) {
                calculateDamage(theOpponent);
                theOpponent.setAttacked(true);
            } else {
                theOpponent.setAttacked(false);
                // report attack failure
            }
        }

        @Override
        protected int calculateDamage(final DungeonCharacter theOpponent) {
            final int numOfAttacks = Math.max(1, this.getAttackSpeed() / theOpponent.getAttackSpeed());
            myTotalDamage = 0;
            for (int i = 0; i < numOfAttacks; i++) {
                final int damage = myRandom.nextInt(getDamageMax() - getDamageMin() + 1)
                        + getDamageMin();
                myTotalDamage += damage;
                theOpponent.setHitPoints(Math.max((theOpponent.getHitPoints() - damage), 0));
                setTotalDamage(myTotalDamage);
            }
            return myTotalDamage;
        }

        @Override
        public void heal() {
            if (isAttacked() && !isFainted()) {
                int myCurrentHitPoints = getHitPoints();
                myHealAmount = myRandom.nextInt(10 - 5 + 1) + 5;
                myCurrentHitPoints += myHealAmount;
                setMyHealAmount(myHealAmount);
                // Ensure the monster's hit points do not exceed maximum hit points
                myCurrentHitPoints = Math.min(myCurrentHitPoints, getMaxHitPoints());
                setHitPoints(myCurrentHitPoints);
            }
        }
        private void setMyHealAmount(final int theHealAmount) {
            myHealAmount = theHealAmount;
        }
        @Override
        public int healAmount() {
            return myHealAmount;
        }
        public void setRandom(final Random theRandom) {
            this.myRandom = theRandom;
        }
    }
}