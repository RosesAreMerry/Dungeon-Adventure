package model;

import java.io.*;

public class GameSerialization {

    public static void saveGame(final String theFileName, final GameData theGameData) {
        try {
            final String saveDirectory = "saves/";
            final File saveDir = new File(saveDirectory);
            final saveDir.mkdirs();
            final File saveFile = new File(saveDirectory + theFileName);
            ObjectOutputStream outputStream =  new ObjectOutputStream(new FileOutputStream(saveFile));
            outputStream.writeObject(theGameData);
            outputStream.close();
        } catch (IOException e) {
            System.err.println("Error saving the game: " + e.getMessage());
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
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error loading the game: " + e.getMessage());
        }
        return null;
    }
}
