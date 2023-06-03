package controller;

import model.*;
import view.AdventureView;
import view.InventoryView;
import view.RoomData;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.function.Consumer;

import static model.GameSerialization.saveGame;

public class ActionHandler {

    private final AdventureView myAdventureView;

    private final InventoryView myInventoryView;

    public ActionHandler(Hero theHero) {
        myAdventureView = new AdventureView();
        final Consumer<Item> myItemHandler = item -> {
            if (item instanceof final Potion thePotion) {
                thePotion.use(theHero);
            }
            if (item instanceof PillarOfOO) {
                myAdventureView.sendMessage("Pillars cannot be used");
            }
        };
        myInventoryView = new InventoryView(myItemHandler);
    }

    public void handleAction(String theChoice, Dungeon theDungeon, Hero theHero) {
        final Map<String, Runnable> actions = new HashMap<>();
        if (theChoice.startsWith("Go")) { // handle moving to other rooms
            final String direction = theChoice.substring(3);
            actions.put("Go " + direction, () -> handleMove(direction, theDungeon));
        } else if (theDungeon.getCurrentRoom().getMonster() != null) {
            final String monster = theChoice.substring(7);
            actions.put("Battle " + monster, () -> handleCombat(monster, theDungeon, theHero));
        } else if (theChoice.startsWith("View")) {
            final String character = theChoice.substring(5);
            actions.put("View Player Stats", () -> handleViewStats(character, theDungeon, theHero));
            actions.put("View Enemy Stats", () -> handleViewStats(character, theDungeon, theHero));
        }
        actions.put("Look Around", () -> handleLookAround(theDungeon, theHero));
        actions.put("See Inventory", () -> myInventoryView.showInventory(theHero.getMyInventory()));
        actions.put("Save Game", () -> handleSaveGame(theDungeon, theHero));
        final Runnable action = actions.get(theChoice);
        action.run();
    }

    private void handleViewStats(final String theCharacter, Dungeon theDungeon, Hero theHero) {
        if (theCharacter.equals("Player Stats")) {
            myAdventureView.sendMessage(theHero.toString());
        } else {
            myAdventureView.sendMessage(theDungeon.getCurrentRoom().getMonster().toString());
        }
    }

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

    private void handleMove(final String theDirection, final Dungeon theDungeon) {
        final Direction direction = Direction.valueOf(theDirection.toUpperCase());
        theDungeon.move(direction);
    }

    private void handleLookAround(final Dungeon theDungeon, Hero theHero) {
        RoomData roomData = new RoomData(theDungeon.getCurrentRoom());
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

    private void handleSaveGame(Dungeon theDungeon, Hero theHero) {
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
}
