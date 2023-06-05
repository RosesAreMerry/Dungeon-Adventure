package model;

import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Provides methods for saving and loading game data using serialization,
 * as well as retrieving a list of saved game names.
 *
 * @author Chelsea Dacones
 */
public class GameSerialization {

    /**
     * Saves the game data to a file.
     *
     * @param theFileName the name of the file to save the game data to
     * @param theGameData the game data to be saved
     */
    public static void saveGame(final String theFileName, final GameData theGameData) {
        try {
            final File saveDir = new File("savedGames/");
            saveDir.mkdirs();
            final File saveFile = new File(saveDir, theFileName + ".ser");
            try (final ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(saveFile))) {
                outputStream.writeObject(theGameData);
            }
        } catch (final IOException theException) {
            System.err.println("Error saving the game: " + theException.getMessage());
        }
    }

    /**
     * Loads the game data from a file.
     *
     * @param theFileName the name of the file to load the game data from
     * @return the loaded GameData object, or null if an error occurred during loading
     */
    public static GameData loadGame(final String theFileName) {
        try (final ObjectInputStream inputStream = new ObjectInputStream(
                new FileInputStream("savedGames/" + theFileName + ".ser"))) {

            final GameData gameData = (GameData) inputStream.readObject();
            // Coordinates are a singleton (kind of), so we need to load them with the rooms so that printDungeon() works.
            Coordinate.loadWith(gameData.getDungeon().getAllRooms().keySet());
            return gameData;
        } catch (final IOException | ClassNotFoundException theException) {
            System.err.println("Error loading the game: " + theException.getMessage());
        }
        return null;
    }

    /**
     * Retrieves a list of saved game names.
     *
     * @return the list of saved game names.
     */
    public static List<String> getSavedGames() {
        return Arrays.stream(Objects.requireNonNull(new File("savedGames/").listFiles()))
                .map(File::getName).map(name -> name.substring(0, name.lastIndexOf('.'))).collect(Collectors.toList());
    }
}
