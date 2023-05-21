package model;

import org.junit.jupiter.api.Test;

import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public class DungeonBuilderTest {

    @Test
    public void testDungeonBuilder() {
        DungeonBuilder dungeonBuilder = DungeonBuilder.INSTANCE;
        try {
            dungeonBuilder.buildDungeon(100);
        } catch (final Exception e) {
            e.printStackTrace();
            fail();
        }
    }

}
