package controller;

import model.*;
import view.AdventureView;
import view.CombatView;
import view.InventoryView;
import view.RoomData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

/**
 * Serves as the main entry point for the game and orchestrates the actions of the player, monsters,
 * and other entities within the game. Overall, it manages the game flow by handling user input, updating the game state,
 * and displays relevant information to users.
 */
public class DungeonAdventure {
    private Dungeon myDungeon;
    private Hero myHero;
    private final AdventureView myAdventureView;
    private final InventoryView myInventoryView;
    private final CombatView myCombatView;
    private final RoomData myRoomData;

    public DungeonAdventure() {
        myAdventureView = new AdventureView();
        final Consumer<Item> myItemHandler = item -> {
            if (item instanceof final Potion thePotion) {
                thePotion.use(myHero);
            }
        };
        myInventoryView = new InventoryView(myItemHandler);
        myCombatView = new CombatView();
        myRoomData = new RoomData("You walk into a room",
                new String[]{"North", "South"},
                new String[]{"Health Potion"},
                new String[]{"Ogre", "Gremlin"},
                false, false);
        boolean playAgain = true;
        while (playAgain) {
            playGame();
            playAgain = promptPlayAgain();
        }
    }

    /**
     * Handles the overall game play.
     */
    private void playGame() {
        // Introduction and game setup
        displayIntroduction();
        myHero = createAdventurer();
        createDungeon();
        myHero.addToInventory(new HealingPotion());
        myAdventureView.sendMessage("\nYou walk into a dungeon.");

        // Main game loop
        do {
            displayCurrentRoom();
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
        switch (character) {
            case "Thief" -> {
                return new Thief(name);
            }
            case "Warrior" -> {
                return new Warrior(name);
            }
            case "Priestess" -> {
                return new Priestess(name);
            }
        }
        return null;
    }

    /**
     * Creates the dungeon for the game.
     */
    private void createDungeon() {
    }

    /**
     * Displays the current room to the user.
     * This method shows the description of the current room, along with any available items in the room.
     */
    private void displayCurrentRoom() {
        myAdventureView.printRoom(myRoomData, null);
    }

    /**
     * Displays the available options to the user.
     * Presents a list of available choices to the user and handles the action associated with the choice.
     */
    private void displayOptions() {
        myAdventureView.sendMessage("What do you want to do?");
        final List<String> options = new ArrayList<>();
        if (myRoomData.getMonsters().length == 0) {
            for (final String door : myRoomData.getDoors()) {
                options.add("Go " + door);
            }
            options.add("Look Around");
        }
        options.add("See Inventory");
        for (final String monster : myRoomData.getMonsters()) {
            options.add("Battle " + monster);
        }
        final String choice = myAdventureView.promptUserChoice(options.toArray(new String[0]));
        myAdventureView.sendMessage("You chose: " + choice);
        handleAction(choice);
    }

    /**
     * Handles the user's chosen action.
     * Takes the user's selection and executes the corresponding logic in the game.
     *
     * @param theChoice the user's chosen action.
     */
    private void handleAction(final String theChoice) {
        final Map<String, Runnable> actions = new HashMap<>();
        final String direction = theChoice.substring(3);
        final String monster = theChoice.substring(7);
        actions.put("Go " + direction, () -> { /* handle moving to another room action */ });
        actions.put("Battle " + monster, () -> handleCombat(monster));
        actions.put("See Inventory", () -> myInventoryView.showInventory(myHero.getMyInventory()));
        actions.put("Look Around", () -> { /* handle Look Around action */ });
        final Runnable action = actions.get(theChoice);
        action.run();
    }

    private void handleCombat(final String theOpponent) {
        if (myRoomData.getMonsters() != null && myRoomData.getMonsters().length > 0) {
            final Combat combat = new Combat();
            final MonsterFactory monsterFactory = new MonsterFactory();
            final Monster opponent = monsterFactory.createMonsterByName(theOpponent);
            combat.initiateCombat(myHero, opponent);
            myRoomData.removeMonsterFromRoom(theOpponent);
            if (opponent.isFainted()) {
                myAdventureView.sendMessage(theOpponent + " was defeated!");
            }
        }
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

    /**
     * The main entry point of the game.
     *
     * @param theArgs command line arguments (not used)
     */
    public static void main(final String[] theArgs) {
        new DungeonAdventure();
    }
}
