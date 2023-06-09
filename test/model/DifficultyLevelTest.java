package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for {@link model.DifficultyLevel}
 */
class DifficultyLevelTest {
    private static final double DELTA = 0.0001;
    private Monster myMonster;

    @BeforeEach
    void setUp() {
        myMonster = new MonsterFactory().createMonsterByName("Ogre");
    }

    /**
     * Test method for {@link model.DifficultyLevel#adjustMonsterStatistics(Monster)}
     * Tests with Easy difficulty.
     */
    @Test
    void testAdjustMonsterStatistics() {
        DifficultyLevel.EASY.adjustMonsterStatistics(myMonster);
        assertEquals("""
                Ogre
                Hit Points: 160
                Chance to Hit: 0.48
                Minimum Damage: 24
                Maximum Damage: 48
                Attack Speed: 1
                Chance to Heal: 0.1
                Minimum Heal Points: 24
                Maximum Heal Points: 48""", myMonster.toString());
    }

    /**
     * Test method for {@link model.DifficultyLevel#adjustHeroStatistics(Hero)}
     * Test with Hard difficulty.
     */
    @Test
    void testAdjustHeroStatistics() {
        final Thief thief = new Thief("Test");
        DifficultyLevel.HARD.adjustHeroStatistics(thief);
        assertEquals("""
                Test the Thief
                Hit Points: 60
                Chance to Hit: 0.64
                Minimum Damage: 16
                Maximum Damage: 32
                Attack Speed: 4
                Block Chance: 0.32
                Special Skill: Surprise Attack - 40 percent chance it is successful""", thief.toString());
    }

    /**
     * Test method for {@link DifficultyLevel#adjustHindrances()}
     */
    @Test
    void testAdjustHindrancesTest() {
        assertEquals(0.1, DifficultyLevel.EASY.adjustHindrances());
        assertEquals(0.2, DifficultyLevel.MEDIUM.adjustHindrances());
        assertEquals(0.4, DifficultyLevel.HARD.adjustHindrances());
    }

    /**
     * Test method for {@link DifficultyLevel#adjustPotions}
     */
    @Test
    void testAdjustPotions() {
        assertEquals(0.3, DifficultyLevel.EASY.adjustPotions(), DELTA);
        assertEquals(0.1, DifficultyLevel.MEDIUM.adjustPotions(), DELTA);
        assertEquals(0.08, DifficultyLevel.HARD.adjustPotions(), DELTA);
    }

    /**
     * Test method for {@link DifficultyLevel#createDungeon}
     */
    @Test
    void testCreateDungeon() {
        final Dungeon dungeon = DifficultyLevel.HARD.createDungeon();
        assertEquals(50, dungeon.getAllRooms().size());
    }

    /**
     * Test method for {@link DifficultyLevel#adjustGameLevel(Dungeon, Hero)}
     */
    @Test
    void testAdjustGameLevel() {
        final MonsterFactory mf = new MonsterFactory();
        final Warrior warrior = new Warrior("Test");
        final Dungeon dungeon = DifficultyLevel.MEDIUM.createDungeon();
        Room currentRoom = null;
        DifficultyLevel.HARD.adjustGameLevel(dungeon, warrior);
        for (final Room room : dungeon.getAllRooms().values()) {
            if (room.getMonster() != null) {
                currentRoom = room;
            }
        }
        assert currentRoom != null;
        assertNotEquals(mf.createMonsterByName("Ogre").toString(), currentRoom.getMonster().toString());
        assertNotEquals(mf.createMonsterByName("Skeleton").toString(), currentRoom.getMonster().toString());
        assertNotEquals(mf.createMonsterByName("Werewolf").toString(), currentRoom.getMonster().toString());
        assertNotEquals(mf.createMonsterByName("Gremlin").toString(), currentRoom.getMonster().toString());
        assertNotEquals(mf.createMonsterByName("Dragon").toString(), currentRoom.getMonster().toString());
        assertNotEquals(mf.createMonsterByName("Golem").toString(), currentRoom.getMonster().toString());
    }
}