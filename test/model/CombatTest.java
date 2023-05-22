package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CombatTest extends RandomMock {
    DungeonCharacter myPlayer;
    DungeonCharacter myOpponent;
    RandomMock myRandom;
    Combat myCombat;
    @BeforeEach
    void setUp() {
        myRandom = new RandomMock();
        myCombat = new Combat();
        myPlayer = new DungeonCharacter("Adventurer", 125, 0.8,
                30, 60, 4) {
            @Override
            protected boolean canAttack() {
                return true;
            }
        };
        myOpponent = new DungeonCharacter("Monster", 70, 0.8,
                15, 30, 2) {
            @Override
            protected boolean canAttack() {
                return true;
            }
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
}