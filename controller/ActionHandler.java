package controller;

import model.*;
import view.AdventureView;
import view.CombatView;
import view.InventoryView;
import view.RoomData;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.function.Consumer;

import static model.GameSerialization.getSavedGames;
import static model.GameSerialization.saveGame;

public class ActionHandler {
    private final DungeonAdventure myDungeonAdventure;
    private final AdventureView myAdventureView;
    private final InventoryView myInventoryView;
    private final CombatView myCombatView;

    /**
     * Constructs an ActionHandler object.
     *
     * @param theHero             the player's character
     * @param theDungeonAdventure represents the game's adventure
     */
     ActionHandler(final Hero theHero, final DungeonAdventure theDungeonAdventure) {
        myDungeonAdventure = theDungeonAdventure;
        myAdventureView = new AdventureView();
        myCombatView = new CombatView();
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
     * @param theChoice  the choice made by the player
     * @param theDungeon the game's dungeon
     * @param theHero    the player's character
     */
    public void handleGameAction(final String theChoice, final Dungeon theDungeon, final Hero theHero) {
        switch (theChoice) {
            case "Pick Up Items" -> handlePickUp(theDungeon, theHero);
            case "See Inventory" -> {
                myAdventureView.sendMessage("Your inventory:");
                myInventoryView.showInventory(theHero.getMyInventory());
            }
            case "Open Menu" -> myDungeonAdventure.displayMenu();
            default -> {
                if (theChoice.startsWith("Go")) {
                    final String direction = theChoice.substring(3);
                    handleMove(direction, theDungeon, theHero);
                } else if (theChoice.startsWith("Battle")) {
                    final String monster = theChoice.substring(7);
                    handleCombat(monster, theDungeon, theHero);
                } else if (theChoice.startsWith("View")) { // handle viewing character's stats
                    final String character = theChoice.substring(5);
                    handleViewStats(character, theDungeon, theHero);
                }
            }
        }
    }

    /**
     * Handles the action for "View Player Stats" and "View Enemy Stats."
     *
     * @param theCharacter the character to view the stats of
     * @param theDungeon   the game's dungeon
     * @param theHero      the player's character
     */
    private void handleViewStats(final String theCharacter, final Dungeon theDungeon, final Hero theHero) {
        final String message = theCharacter.equals("Player Stats") ? theHero.toString()
                : theDungeon.getCurrentRoom().getMonster().toString();
        myAdventureView.sendMessage("\033[0m" + message);
    }

    /**
     * Handles the combat between the player and monster.
     *
     * @param theOpponent the player's opponent
     * @param theDungeon  the game's dungeon
     * @param theHero     the player's character
     */
    private void handleCombat(final String theOpponent, final Dungeon theDungeon, final Hero theHero) {
        final RoomData roomData = new RoomData(theDungeon.getCurrentRoom());
        if (roomData.getMonsters() != null && roomData.getMonsters().length > 0) {
            final Combat combat = new Combat();
            final MonsterFactory monsterFactory = new MonsterFactory();
            final Monster opponent = monsterFactory.createMonsterByName(theOpponent);
            myCombatView.showCombat(theHero.getName(), theOpponent, opponent.getHitPoints(),
                    theHero.getHitPoints(), null);
            combat.initiateCombat(theHero, opponent);
            myCombatView.showCombat(theHero.getName(), theOpponent, opponent.getHitPoints(),
                    theHero.getHitPoints(), combat.getActionLog().toArray(new String[0]));
            roomData.removeMonsterFromRoom(theOpponent);
            final String message = opponent.isFainted() ? theOpponent + " was defeated!"
                    : "You were defeated by the " + theOpponent + "!\n";
            if (opponent.isFainted()) {
                theDungeon.getCurrentRoom().killMonster();
            }
            myAdventureView.sendMessage(message);
        }
    }

    /**
     * Handles moving to other rooms in the dungeon.
     *
     * @param theDirection the direction to move to
     * @param theDungeon   the game's dungeon
     * @param theHero      the player's character
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
     * @param theHero    the player's character
     */
    private void handlePickUp(final Dungeon theDungeon, final Hero theHero) {
        final RoomData roomData = new RoomData(theDungeon.getCurrentRoom());
        if (roomData.getItems().length > 0) {
            final StringBuilder sb = new StringBuilder();
            myAdventureView.buildList(sb, roomData.getItems(), "You acquired ", "You acquired ",
                    "!", true);
            myAdventureView.sendMessage(sb.toString());
            theHero.addToInventory(theDungeon.getCurrentRoom().getItems());
            final boolean foundPillar = Arrays.stream(roomData.getItems()).anyMatch(item -> item.startsWith("Pillar"));
            if (foundPillar) {
                myAdventureView.sendMessage("You've collected " + theHero.numOfPillarsCollected()
                        + " out of 4 Pillars of OO.");
            }
            theDungeon.getCurrentRoom().getItems().clear();
        }
    }

    private void displayInstructions(final boolean theGameInProgress) {
        final String fileName = theGameInProgress ? "help_manual.txt" : "instructions.txt";
        try (final Scanner scanner = new Scanner(new File(fileName))) {
            while (scanner.hasNextLine()) {
                System.out.println(scanner.nextLine());
            }
        } catch (final FileNotFoundException theException) {
            theException.printStackTrace();
        }
    }

    /**
     * Handles saving the game.
     *
     * @param theDungeon the game's dungeon
     * @param theHero    the player's character
     */
    private void handleSaveGame(final Dungeon theDungeon, final Hero theHero) {
        final GameData gameData = new GameData(theDungeon, theHero);
        String fileName;
        File saveFile;
        do {
            fileName = myAdventureView.promptUserInput("Enter a name for your game entry: ",
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
        final String fileName = myAdventureView.promptUserChoice(getSavedGames().toArray(new String[0]), true);
        final GameData myGameData = GameSerialization.loadGame(fileName);



        if (myGameData != null) {
            final Hero hero = myGameData.getHero();
            final int numOfPillars = hero.numOfPillarsCollected();
            final List<String> pillarsCollected = hero.getMyInventory().stream().filter(PillarOfOO.class::isInstance).map(Item::getName).toList();
            myAdventureView.sendMessage(fileName + " loaded successfully!\n\nCurrent hit points: "
                    + hero.getHitPoints() + "\nPillars collected (" + numOfPillars + "/4): "
                    + String.join(", ", pillarsCollected));
        } else {
            myAdventureView.sendMessage("Failed to load " + fileName);
        }
        return myGameData;
    }

    /**
     * Handles a menu action based on the user's choice.
     *
     * @param theChoice           the choice made by the player in the menu
     * @param theDungeonAdventure represents the game's adventure
     * @param theDungeon          the game's dungeon
     * @param theHero             the player's character
     * @param theIsPlaying        indicates whether a game is currently in progress
     */
    public void handleMenuAction(final String theChoice, final DungeonAdventure theDungeonAdventure,
                                 final Dungeon theDungeon, final Hero theHero, final boolean theIsPlaying) {
        switch (theChoice) {
            case "Play", "New Game" -> {
                try {
                    theDungeonAdventure.playGame();
                } catch (final InterruptedException theException) {
                    throw new RuntimeException(theException);
                }
            }
            case "Help", "Instructions" -> {
                final boolean isHelp = theChoice.equals("Help");
                displayInstructions(isHelp);
                theDungeonAdventure.displayMenu();
            }
            case "Exit Game" -> {
                myAdventureView.sendMessage("Exiting the game. Thanks for playing!");
                System.exit(0);
            }
            case "Save Game" -> {
                if (theIsPlaying) {
                    handleSaveGame(theDungeon, theHero);
                }
            }
            case "Close Menu" -> {
            }
        }
    }
}
