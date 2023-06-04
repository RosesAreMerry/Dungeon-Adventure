package controller;

import model.*;
import view.AdventureView;
import view.CombatView;
import view.InventoryView;
import view.RoomData;

import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Serves as the main entry point for the game and orchestrates the actions of the player, monsters,
 * and other entities within the game. Overall, it manages the game flow by handling user input, updating the game state,
 * and displays relevant information to users.
 * @author
 */
public class DungeonAdventure {
    public static final int MAX_PIT_DAMAGE = 10;
    private final AdventureView myAdventureView;
    private Dungeon myDungeon;
    private Hero myHero;
    private ActionHandler myActionHandler;
    private boolean myIsPlaying;
    private boolean myWonGame;

    public DungeonAdventure() throws InterruptedException {
        myAdventureView = new AdventureView();
        myActionHandler = new ActionHandler(myHero, this);
        boolean playAgain = true;
        while (playAgain) {
            myWonGame = false;
            myIsPlaying = false;
            displayIntroduction();
            displayMenu();
            playAgain = promptPlayAgain();
        }
    }

    /**
     * Handles the overall game play.
     */
    void playGame() throws InterruptedException {
        final String choice = myAdventureView.promptUserChoice(new String[]{"Load Game", "New Game"}, true);
        if (choice.equals("Load Game")) {
            final GameData gameData = myActionHandler.loadSavedGame();
            myHero = gameData.getHero();
            myDungeon = gameData.getDungeon();
        } else {
            myHero = createAdventurer();
            myDungeon = createDungeon();
            myAdventureView.sendMessage("\nYou walk into a dungeon.");
        }
        myIsPlaying = true;
        myActionHandler = new ActionHandler(myHero, this);
        // Main game loop
        while (!myHero.isFainted()) {
            displayCurrentRoom();
            if (myWonGame) {
                break;
            }
            displayOptions();
        }

        // Game conclusion
//        displayGameResult();
        displayDungeon();
    }

    /**
     * Displays the introduction of the game to the user.
     */
    private void displayIntroduction() {
        myAdventureView.sendMessage("[Introduction placeholder]");
    }

    void displayMenu() {
        myAdventureView.sendMessage("---------- MENU ----------");
        final String choice;
        if (myIsPlaying) {
            choice = myAdventureView.promptUserChoice(new String[]{"Instructions", "Save Game", "New Game", "Exit Game", "Close Menu"}, true);
            myActionHandler.handleMenuAction(choice, this, myDungeon, myHero, true);
        } else {
            choice = myAdventureView.promptUserChoice(new String[]{"Play", "Instructions", "Exit Game"}, true);
            myActionHandler.handleMenuAction(choice, this, null, null, false);
        }
    }

    /**
     * Creates an adventurer character based on user's input.
     * Prompts the user for their name and character selection and returns the corresponding Hero object.
     *
     * @return the Hero character selected by the user.
     */
    private Hero createAdventurer() {
        final String name = myAdventureView.promptUserInput("\nWhat is your name? ", "Please enter a name: ", (String s) -> s != null && s.length() > 0);
        myAdventureView.sendMessage("\nPick your character: ");
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
        final String[] options = {"Small (10 rooms)", "Medium (25 rooms)", "Large (50 rooms)"};
        myAdventureView.sendMessage("\nChoose your dungeon: ");
        final String choice = myAdventureView.promptUserChoice(options, true, "What size dungeon do you want to explore? ");

        return switch (choice) {
            case "Medium (25 rooms)" -> DungeonBuilder.INSTANCE.buildDungeon(25);
            case "Large (50 rooms)" -> DungeonBuilder.INSTANCE.buildDungeon(50);
            default -> DungeonBuilder.INSTANCE.buildDungeon(10);
        };
    }

    /**
     * Displays the current room to the user.
     * This method shows the description of the current room, along with any available items in the room.
     */
    private void displayCurrentRoom() {
        Map<String, Room> adjacentRooms = null;
        if (myHero.isVisionPotionActive()) {
            adjacentRooms = myDungeon.getNeighbors();
        }
        myAdventureView.printRoom(new RoomData(myDungeon.getCurrentRoom()), adjacentRooms);
        if (myDungeon.getCurrentRoom().hasPit()) {
            handlePit();
        }
        myAdventureView.sendMessage(myDungeon.getCurrentRoom().toString());
        if (myDungeon.getCurrentRoom().isExit()) {
            myWonGame = wonGame();
        }
    }

    /**
     * Displays the available options to the user.
     * Presents a list of available choices to the user and handles the action associated with the choice.
     */
    private void displayOptions() {
        final List<String> options = new ArrayList<>();
        final RoomData roomData = new RoomData(myDungeon.getCurrentRoom());
        myAdventureView.sendMessage("What do you want to do?");
        if (!myHero.isFainted()) {
            if (new RoomData(myDungeon.getCurrentRoom()).getMonsters().length != 0) {
                options.add("Battle " + myDungeon.getCurrentRoom().getMonster().getName());
                options.add("View Enemy Stats");
            }
            else {
                for (final String door : roomData.getDoors()) {
                    options.add("Go " + door);
                }
                if (myDungeon.getCurrentRoom().getItems().size() > 0) {
                    options.add("Pick Up Items");
                }
            }
            if (myHero.getMyInventory().size() > 0 && myHero.getMyInventory() != null) {
                options.add("See Inventory");
            }
            options.add("View Player Stats");
            options.add("Open Menu");
            final String choice = myAdventureView.promptUserChoice(options.toArray(new String[0]));
            myAdventureView.sendMessage("You chose: " + choice + "\n");
            myActionHandler.handleGameAction(choice, myDungeon, myHero);
        }
    }

    public void handlePit() {
        myAdventureView.sendMessage("You fell into a pit!");
        final int damage = new Random().nextInt(MAX_PIT_DAMAGE) + 1;
        myHero.setHitPoints(myHero.getHitPoints() - damage);
        myAdventureView.sendMessage("You took " + damage + " damage! " + myHero.getHitPoints() + " hit points remaining.");
        myDungeon.getCurrentRoom().removePit();
    }

//    /**
//     * Displays the final game result to the user.
//     * Based on whether the user's character has fainted or won the game.
//     */
//    private void displayGameResult() {
//        if (myHero.isFainted()) {
//            myAdventureView.sendMessage("Game over!");
//        } else {
//            myAdventureView.sendMessage("You Win!");
//        }
//    }

    /**
     * Displays the entire dungeon layout to the user.
     */
    private void displayDungeon() {
        myAdventureView.sendMessage("[Dungeon placeholder]");
    }

    private boolean wonGame() {
        final int numOfPillars = (int) myHero.getMyInventory().stream().filter(item -> item instanceof PillarOfOO).count();
        final List<String> allPillars = new ArrayList<>(Arrays.asList("Pillar of Abstraction", "Pillar of Encapsulation", "Pillar of Inheritance", "Pillar of Polymorphism"));
        final List<String> collectedPillars = myHero.getMyInventory().stream().filter(item -> item instanceof PillarOfOO).map(Item::getName).toList();
        final List<String> remainingPillars = allPillars.stream().filter(pillarName -> !collectedPillars.contains(pillarName)).toList();
        if (numOfPillars == 4) {
            myAdventureView.sendMessage("Congratulations adventurer! You've collected all four Pillars of OO and have won the game!");
            return true;
        } else {
            myAdventureView.sendMessage("You're still missing " + (4 - numOfPillars) + " Pillars of OO. Explore the dungeon further to find "
                    + String.join(", ", remainingPillars) + "\n");
            return false;
        }
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
    public static void main(final String[] theArgs) throws InterruptedException {
        new DungeonAdventure();
    }
}
