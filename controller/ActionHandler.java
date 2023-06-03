package controller;

import model.*;
import view.AdventureView;
import view.InventoryView;
import view.RoomData;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import static model.GameSerialization.getSavedGames;
import static model.GameSerialization.saveGame;

public class ActionHandler {
    private final DungeonAdventure myDungeonAdventure;
    private final AdventureView myAdventureView;
    private final InventoryView myInventoryView;

    /**
     * Constructs an ActionHandler object.
     *
     * @param theHero the player's character
     * @param theDungeonAdventure represents the game's adventure
     */
    public ActionHandler(final Hero theHero, final DungeonAdventure theDungeonAdventure) {
        myDungeonAdventure = theDungeonAdventure;
        myAdventureView = new AdventureView();
        // handle usage of items by the player
        final Consumer<Item> myItemHandler = item -> {
            if (item instanceof final Potion thePotion) {
                thePotion.use(theHero);
                if (item.getName().equals("Healing Potion")) {
                    myAdventureView.sendMessage("You've used a Healing Potion. Current hit points: " +
                            theHero.getHitPoints());
                }
            }
            if (item instanceof PillarOfOO) {
                myAdventureView.sendMessage("This is a " + item.getName() + ". Pillars cannot be used.");
            }
        };
        myInventoryView = new InventoryView(myItemHandler);
    }

    /**
     * Handles a game action based on the player's choice.
     *
     * @param theChoice the choice made by the player
     * @param theDungeon the game's dungeon
     * @param theHero the player's character
     */
    public void handleGameAction(final String theChoice, final Dungeon theDungeon, final Hero theHero) {
        final Map<String, Runnable> actions = new HashMap<>();
        if (theChoice.startsWith("Go")) { // handle moving to other rooms
            final String direction = theChoice.substring(3);
            actions.put("Go " + direction, () -> handleMove(direction, theDungeon, theHero));
        } else if (theDungeon.getCurrentRoom().getMonster() != null && !theChoice.startsWith("View")) { // handle combat
            final String monster = theChoice.substring(7);
            actions.put("Battle " + monster, () -> handleCombat(monster, theDungeon, theHero));
        } else if (theChoice.startsWith("View")) { // handle viewing character's stats
            final String character = theChoice.substring(5);
            actions.put("View Player Stats", () -> handleViewStats(character, theDungeon, theHero));
            actions.put("View Enemy Stats", () -> handleViewStats(character, theDungeon, theHero));
        }
        actions.put("Pick Up Items", () -> handlePickUp(theDungeon, theHero));
        actions.put("See Inventory", () -> {myAdventureView.sendMessage("Your Inventory: ");
        myInventoryView.showInventory(theHero.getMyInventory());});
        actions.put("Open Menu", myDungeonAdventure::displayMenu);
        final Runnable action = actions.get(theChoice);
        action.run();
    }

    /**
     * Handles the action for "View Player Stats" and "View Enemy Stats."
     *
     * @param theCharacter the character to view the stats of
     * @param theDungeon the game's dungeon
     * @param theHero the player's character
     */
    private void handleViewStats(final String theCharacter, final Dungeon theDungeon, final Hero theHero) {
        if (theCharacter.equals("Player Stats")) {
            myAdventureView.sendMessage(theHero.toString());
        } else {
            myAdventureView.sendMessage(theDungeon.getCurrentRoom().getMonster().toString());
        }
    }

    /**
     * Handles the combat between the player and monster.
     *
     * @param theOpponent the player's opponent
     * @param theDungeon the game's dungeon
     * @param theHero the player's character
     */
    private void handleCombat(final String theOpponent, final Dungeon theDungeon, final Hero theHero) {
        final RoomData roomData = new RoomData(theDungeon.getCurrentRoom());
        if (roomData.getMonsters() != null && roomData.getMonsters().length > 0) {
            final Combat combat = new Combat();
            final MonsterFactory monsterFactory = new MonsterFactory();
            final Monster opponent = monsterFactory.createMonsterByName(theOpponent);
            combat.initiateCombat(theHero, opponent);
            roomData.removeMonsterFromRoom(theOpponent);
            if (opponent.isFainted()) {
                myAdventureView.sendMessage(theOpponent + " was defeated!");
                theDungeon.getCurrentRoom().killMonster();
            } else {
                myAdventureView.sendMessage("You were defeated by the " + theOpponent + "!");
            }
        }
    }

    /**
     * Handles moving to other rooms in the dungeon.
     *
     * @param theDirection the direction to move to
     * @param theDungeon the game's dungeon
     * @param theHero the player's character
     */
    private void handleMove(final String theDirection, final Dungeon theDungeon, final Hero theHero) {
        final Direction direction = Direction.valueOf(theDirection.toUpperCase());
        theDungeon.move(direction);
        theHero.reduceVisionPotionTurns();
        myAdventureView.sendMessage("You moved to the " + theDirection);
    }

    /**
     * Handles the action to pick up items in a room.
     *
     * @param theDungeon the game's dungeon
     * @param theHero the player's character
     */
    private void handlePickUp(final Dungeon theDungeon, final Hero theHero) {
        final RoomData roomData = new RoomData(theDungeon.getCurrentRoom());
        if (roomData.getItems().length > 0) {
            final StringBuilder sb = new StringBuilder();
            myAdventureView.buildList(sb,
                    roomData.getItems(),
                    "You acquired ",
                    "You acquired ",
                    "!",
                    true);
            myAdventureView.sendMessage(String.valueOf(sb));
            theHero.addToInventory(theDungeon.getCurrentRoom().getItems());
            theDungeon.getCurrentRoom().getItems().clear();
        }
    }

    /**
     * Handles saving the game.
     *
     * @param theDungeon the game's dungeon
     * @param theHero the player's character
     */
    private void handleSaveGame(final Dungeon theDungeon, final Hero theHero) {
        final GameData gameData = new GameData(theDungeon, theHero);
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

    /**
     * Loads a saved game.
     *
     * @return myGameData the GameData of the loaded game
     */
    GameData loadSavedGame() {
        final String fileName = myAdventureView.promptUserChoice(getSavedGames()
                .toArray(new String[0]), true);
        final GameData myGameData = GameSerialization.loadGame(fileName);

        if (myGameData != null) {
            final ArrayList<Item> inventory = myGameData.getHero().getMyInventory();
            final int numOfPillars = (int) inventory.stream().filter(item -> item instanceof PillarOfOO).count();
            final List<String> pillarsCollected = inventory.stream().filter(item -> item instanceof PillarOfOO).map(Item::getName).toList();
            myAdventureView.sendMessage(fileName + " loaded successfully!");
            myAdventureView.sendMessage("\nCurrent hit points: " + myGameData.getHero().getHitPoints());
            myAdventureView.sendMessage("Pillars collected (" + numOfPillars + "/4): "
                    + String.join(", ", pillarsCollected));
        } else {
            myAdventureView.sendMessage("Failed to load " + fileName);
        }
        return myGameData;
    }

    /**
     * Handles a menu action based on the user's choice.
     *
     * @param theChoice the choice made by the player in the menu
     * @param theDungeonAdventure represents the game's adventure
     * @param theDungeon the game's dungeon
     * @param theHero the player's character
     * @param theIsPlaying indicates whether a game is currently in progress
     */
    public void handleMenuAction(final String theChoice, final DungeonAdventure theDungeonAdventure,
                                 final Dungeon theDungeon, final Hero theHero, final boolean theIsPlaying) {
        final Map<String, Runnable> actions = new HashMap<>();
        final Runnable startGame = () -> {
            try {
                theDungeonAdventure.playGame();
            } catch (final InterruptedException theException) {
                throw new RuntimeException(theException);
            }
        };
        if (theIsPlaying) { // if game is already in progress
            actions.put("Save Game", () -> handleSaveGame(theDungeon, theHero));
        } else {
            actions.put("Play", startGame);
        }
        actions.put("Instructions", () -> {/* Implement instructions logic */});
        actions.put("Exit Game", () -> {myAdventureView.sendMessage("Exiting the game. Thanks for playing!");
            System.exit(0);});
        actions.put("New Game", startGame);
        actions.put("Close Menu", () -> {});

        final Runnable action = actions.get(theChoice);
        action.run();
    }
}
