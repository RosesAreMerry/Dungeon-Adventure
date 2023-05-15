package model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
public class WarriorTest {
    private Warrior warrior;
    private DungeonCharacter opponent;

    @BeforeEach
    public void setUp() {
        warrior = new Warrior("TestWarrior");
        opponent=new Monster("gremlin",200,.6,
                30,60,2,.1,30,60);
        }

    @Test
    public void testAttackSpecialSkillFalse() {
        warrior.attack(opponent);
        Assertions.assertTrue(opponent.getHitPoints() < 200);
    }
    @Test
    public void testAttackSpecialSkilltrue() {
        warrior.setspecialcase(true);
        warrior.attack(opponent);

        int expectedHitPoints =121;
        int actualHitPoints = opponent.getHitPoints();
        Assertions.assertEquals(expectedHitPoints, actualHitPoints);
    }
    }

