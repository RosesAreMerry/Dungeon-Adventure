package model;

import org.junit.jupiter.api.Test;

public class DungeonBuilderTest {

    @Test
    public void testDungeonBuilder() {
        DungeonBuilder dungeonBuilder = DungeonBuilder.INSTANCE;
        Dungeon dungeon = dungeonBuilder.buildDungeon(2000);
        System.out.println(dungeon.getCurrentRoom());
    }

}
