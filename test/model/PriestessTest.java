package model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PriestessTest extends RandomMock {
    private Priestess myPriestess;
    private DungeonCharacter myOpponent;

    @BeforeEach
    public void setUp() {
        final RandomMock randomMock = new RandomMock();
        randomMock.setMockIntValue(20);//
        myPriestess = new Priestess("Priestess") {
            @Override
            protected boolean canAttack() {
                return true;
            }
        };
        myPriestess.setMyRandom(randomMock);
        myPriestess.setRandom(randomMock);
        randomMock.setMockIntValue(10);
        myOpponent = new Monster("Gremlin",200,0.6,
                30,60,2,0.1,30,60);

    }

    @Test
    public void testAttack() {
        myPriestess.attack(myOpponent);
        assertEquals(130, myOpponent.getHitPoints());
    }

    @Test
    public void testHeal() {
        myPriestess.setHitPoints(30);
        myPriestess.heal();
        assertEquals(50, myPriestess.getHitPoints());
    }
}
