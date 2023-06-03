package controller;

import model.*;
import view.AdventureView;
import view.InventoryView;
import view.RoomData;

import java.io.File;
import java.util.*;
import java.util.function.Consumer;

import static model.GameSerialization.getSavedGames;
import static model.GameSerialization.saveGame;

/**
 * Serves as the main entry point for the game and orchestrates the actions of the player, monsters,
 * and other entities within the game. Overall, it manages the game flow by handling user input, updating the game state,
 * and displays relevant information to users.
 */
public class DungeonAdventure2 {
    public static final int MAX_PIT_DAMAGE = 10;
    private final AdventureView myAdventureView;
    private Dungeon myDungeon;
    private Hero myHero;
    private GameData myGameData;
    private ActionHandler myActionHandler;

    public DungeonAdventure2() throws InterruptedException {
        myAdventureView = new AdventureView();
        myActionHandler = new ActionHandler(myHero);
        boolean playAgain = true;
        while (playAgain) {
            playGame();
            playAgain = promptPlayAgain();
        }
    }

    /**
     * Handles the overall game play.
     */
    private void playGame() throws InterruptedException {
        // Introduction and game setup
        displayIntroduction();
        final String choice = myAdventureView.promptUserChoice(new String[]{"Load Game", "New Game"}, true);
        if (choice.equals("Load Game")) {
            loadSavedGame();
        } else {
            myHero = createAdventurer();
            myDungeon = createDungeon();
            myAdventureView.sendMessage("\nYou walk into a dungeon.");
        }
        myGameData = new GameData(myDungeon, myHero);

        // Main game loop
        do {
//            Thread.sleep(1000);
            displayCurrentRoom();
//            Thread.sleep(2000);
            displayOptions();
        } while (!myHero.isFainted());

        // Game conclusion
        displayGameResult();
        displayDungeon();
    }

    /**
     * Displays the introduction of the game to the user.
     */
    private void displayIntroduction() {
        myAdventureView.sendMessage("[Introduction placeholder]");
    }

    /**
     * Creates an adventurer character based on user's input.
     * Prompts the user for their name and character selection and returns the corresponding Hero object.
     *
     * @return the Hero character selected by the user.
     */
    private Hero createAdventurer() {
        final String name = myAdventureView.promptUserInput("\nWhat is your name? ", "Please enter a name: ", (String s) -> s != null && s.length() > 0);
        myAdventureView.sendMessage("Pick your character: ");
        final String character = myAdventureView.promptUserChoice(new String[]{"Thief", "Warrior", "Priestess"}, false);
        return switch (character) {
            case "Thief" -> new Thief(name);
            case "Warrior" -> new Warrior(name);
            case "Priestess" -> new Priestess(name);
            default -> null;
        };
    }

    /**
     * Creates the dungeon for the game.
     */
    private Dungeon createDungeon() {
        final String[] options = {"Small", "Medium", "Large"};
        final String choice = myAdventureView.promptUserChoice(options, false, "What size dungeon do you want to explore? ");

        return switch (choice) {
            case "Medium" -> DungeonBuilder.INSTANCE.buildDungeon(25);
            case "Large" -> DungeonBuilder.INSTANCE.buildDungeon(50);
            default -> DungeonBuilder.INSTANCE.buildDungeon(10);
        };
    }


    private RoomData getCurrentRoomData() {
        return new RoomData(myDungeon.getCurrentRoom());
    }

    /**
     * Displays the current room to the user.
     * This method shows the description of the current room, along with any available items in the room.
     */
    private void displayCurrentRoom() {
        myAdventureView.printRoom(getCurrentRoomData(), null);
        if (myDungeon.getCurrentRoom().hasPit()) {
            handlePit();
        }
        myAdventureView.sendMessage(myDungeon.getCurrentRoom().toString());
    }

    /**
     * Displays the available options to the user.
     * Presents a list of available choices to the user and handles the action associated with the choice.
     */
    private void displayOptions() {
        final List<String> options = new ArrayList<>();
        myAdventureView.sendMessage("What do you want to do?");
        if (!myHero.isFainted()) {
            if (getCurrentRoomData().getMonsters().length != 0) {
                options.add("Battle " + myDungeon.getCurrentRoom().getMonster().getName());
                options.add("View Enemy Stats");
//              handleCombat(myDungeon.getCurrentRoom().getMonster().getName());
            }
            else {
                for (final String door : getCurrentRoomData().getDoors()) {
                    options.add("Go " + door);
                }
                options.add("Look Around");
            }
            if (myHero.getMyInventory().size() > 0 && myHero.getMyInventory() != null) {
                options.add("See Inventory");
            }
            options.add("View Player Stats");
            options.add("Save Game");
            final String choice = myAdventureView.promptUserChoice(options.toArray(new String[0]));
            myAdventureView.sendMessage("You chose: " + choice);
            myActionHandler.handleAction(choice, myDungeon, myHero);
        }
    }

    public void handlePit() {
        myAdventureView.sendMessage("You fell into a pit!");
        final int damage = new Random().nextInt(MAX_PIT_DAMAGE) + 1;
        myAdventureView.sendMessage("You took " + damage + " damage! " + myHero.getHitPoints() + " hit points remaining.");
        myHero.setHitPoints(myHero.getHitPoints() - damage);
        myDungeon.getCurrentRoom().removePit();
    }

    /**
     * Displays the final game result to the user.
     * Based on whether the user's character has fainted or won the game.
     */
    private void displayGameResult() {
        if (myHero.isFainted()) {
            myAdventureView.sendMessage("Game over!");
        } else {
            myAdventureView.sendMessage("You Win!");
        }
    }

    /**
     * Displays the entire dungeon layout to the user.
     */
    private void displayDungeon() {
    }

    /**
     * Prompts the user to play the game again or exit.
     *
     * @return true if the user wants to play again; false otherwise.
     */
    private boolean promptPlayAgain() {
        final String choice = myAdventureView.promptUserChoice(new String[] {"Play Again", "Exit"}, false);
        return choice.equals("Play Again");
    }

    private void handleSaveGame() {
        final GameData gameData = new GameData(myDungeon, myHero);
        String fileName;
        File saveFile;
        do {
            fileName =  myAdventureView.promptUserInput("Enter a name for your game entry: ",
                "Please enter a name for your game entry: ", (String s) -> s != null && s.length() > 0);
            saveFile = new File("savedGames/", fileName + ".ser");
            if (saveFile.exists()) {
                myAdventureView.sendMessage("File with the same name already exists. Do you want to overwrite?");
                if (myAdventureView.promptUserChoice(new String[]{"Yes", "No"}).equals("Yes")) {
                    break;
                }
            }
        } while (saveFile.exists());
        saveGame(fileName, gameData);
        myAdventureView.sendMessage(fileName + " was successfully saved!");
    }

    private void loadSavedGame() {
        final String fileName = myAdventureView.promptUserChoice(getSavedGames()
                .toArray(new String[0]), true);
        myGameData = GameSerialization.loadGame(fileName);
        if (myGameData != null) {
            myHero = myGameData.getHero();
            myDungeon = myGameData.getDungeon();
            myAdventureView.sendMessage(fileName + " loaded successfully!");
            myAdventureView.sendMessage("\nCurrent hit points: " + myHero.getHitPoints());
            myAdventureView.sendMessage("Pillars collected (2/4): " + myHero.getMyInventory()); // TODO: Finish implementing this
        } else {
            myAdventureView.sendMessage("Failed to load " + fileName);
        }
    }

    /**
     * The main entry point of the game.
     *
     * @param theArgs command line arguments (not used)
     */
    public static void main(final String[] theArgs) throws InterruptedException {
        new DungeonAdventure2();
    }
}
