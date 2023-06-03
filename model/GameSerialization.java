package model;

import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class GameSerialization {

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

    public static GameData loadGame(final String theFileName) {
        try (final ObjectInputStream inputStream = new ObjectInputStream(
                new FileInputStream("savedGames/" + theFileName + ".ser"))) {
            return (GameData) inputStream.readObject();
        } catch (final IOException | ClassNotFoundException theException) {
            System.err.println("Error loading the game: " + theException.getMessage());
        }
        return null;
    }

    public static List<String> getSavedGames() {
        return Arrays.stream(Objects.requireNonNull(new File("savedGames/").listFiles()))
                .map(File::getName).map(name -> name.substring(0, name.lastIndexOf('.'))).collect(Collectors.toList());
    }
}
