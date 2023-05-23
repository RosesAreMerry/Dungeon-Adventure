package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DungeonCharacterTest extends RandomMock {

    private DungeonCharacter myPlayer;

    private DungeonCharacter myOpponent;

    private RandomMock myRandomMock;

    @BeforeEach
    void setUp() {
        myRandomMock = new RandomMock();
        myOpponent = new DungeonCharacter("Test Opponent", 75, 0.6,
                10, 15, 2) {
        };
        myRandomMock.setMockIntValue(15); // damage = 15 + 10 = 25
    }

    @Test
    void testSuccessfulAttack() {
        myPlayer = new DungeonCharacter("Test Player", 100, 0.5,
                10, 20,  4) {
            @Override
            protected boolean canAttack() {
                return true;
            }
        };
        myPlayer.setRandom(myRandomMock);
        myPlayer.attack(myOpponent);
        assertEquals(25 ,myOpponent.getHitPoints());
    }

    @Test
    void testUnsuccessfulAttack() {
        myPlayer = new DungeonCharacter("Test Player", 100, 0.5,
                10, 20,  4) {
            @Override
            protected boolean canAttack() {
                return false;
            }
        };
        assertEquals(75, myOpponent.getHitPoints());
    }

    @Test
    void testCalculateDamage() {
        myPlayer = new DungeonCharacter("Test Player", 100, 0.5,
                10, 20,  4) {
        };
        myPlayer.setRandom(myRandomMock);
        final int totalDamage = myPlayer.calculateDamage(myOpponent);
        assertEquals(50, totalDamage);
    }
}