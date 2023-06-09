package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for {@link model.GameSerialization}
 */
class GameSerializationTest {

    String myFileName;
    GameData myGameData;

    @BeforeEach
    void setUp() {
        myFileName = "testFile";
        myGameData = new GameData(DifficultyLevel.EASY.createDungeon(),
                new Thief("Test Thief"));
    }

    /**
     * Test method for {@link model.GameSerialization#saveGame(String, GameData)}
     */
    @Test
    void saveGame() {
        GameSerialization.saveGame(myFileName, myGameData);
        final File saveFile = new File("savedGames/" + myFileName + ".ser");
        assertTrue(saveFile.exists(), "Save file exists");
    }

    /**
     * Test method for {@link model.GameSerialization#loadGame(String)}
     */
    @Test
    void loadGame() {
        assertNotNull(GameSerialization.loadGame(myFileName));
    }

    /**
     * Test method for {@link GameSerialization#getSavedGames()}
     */
    @Test
    void getSavedGames() {
        final String[] expectedSaveFiles = getFilesInDirectory();
        final List<String> savedGames = GameSerialization.getSavedGames();
        for (final String fileName : expectedSaveFiles) {
            assertTrue(savedGames.contains(fileName));
        }
    }

    /**
     * Helper method for retrieving the files in the savedGamed directory.
     *
     * @return the files in savedGames
     */
    private String[] getFilesInDirectory() {
        final File directory = new File("savedGames");
        final File[] files = directory.listFiles();
        String[] fileNames = new String[0];
        if (files != null) {
            fileNames = Arrays.stream(files).map(File::getName)
                    .map(name -> name.substring(0, name.lastIndexOf('.'))).toArray(String[]::new);
        }
        return fileNames;
    }
}