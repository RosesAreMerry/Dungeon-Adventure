package model;

import java.io.*;

public class GameSerialization {

    public static void saveGame(final String theFileName, final GameData theGameData) {
        try {
            final String saveDirectory = "saves/";
            final File saveDir = new File(saveDirectory);
            saveDir.mkdirs();
            final File saveFile = new File(saveDirectory + theFileName);
            final ObjectOutputStream outputStream =  new ObjectOutputStream(new FileOutputStream(saveFile));
            outputStream.writeObject(theGameData);
            outputStream.close();
        } catch (final IOException theException) {
            System.err.println("Error saving the game: " + theException.getMessage());
        }
    }

    public static GameData loadGame(final String theFileName) {
        try{
            final String saveDirectory = "saves/";
            final File saveFile = new File(saveDirectory + theFileName);
            final ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(saveFile));
            final GameData gameData = (GameData) inputStream.readObject();
            inputStream.close();
            return gameData;
        } catch (final IOException | ClassNotFoundException theException) {
            System.err.println("Error loading the game: " + theException.getMessage());
        }
        return null;
    }
}
