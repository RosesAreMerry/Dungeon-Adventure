package model;

import java.io.*;

public class GameSerializationUtil {

    public static void saveGame(String fileName, GameData gameData) {
        try (FileOutputStream fileOut = new FileOutputStream(fileName);
             ObjectOutputStream objectOut = new ObjectOutputStream(fileOut)) {
            objectOut.writeObject(gameData);
            System.out.println("Game saved successfully.");
        } catch (IOException e) {
            System.err.println("Error saving the game: " + e.getMessage());
        }
    }

    public static GameData loadGame(String fileName) {
        GameData gameData = null;
        try (FileInputStream fileIn = new FileInputStream(fileName);
        ObjectInputStream objectIn = new ObjectInputStream(fileIn)) {
            gameData = (GameData) objectIn.readObject();
        } catch (IOException | ClassNotFoundException e) {
        }
        return gameData;
    }
}
