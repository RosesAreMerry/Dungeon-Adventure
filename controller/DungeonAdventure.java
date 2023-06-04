package controller;

import model.*;
import view.AdventureView;
import view.RoomData;

import java.util.*;

/**
 * Serves as the main entry point for the game and orchestrates the actions of the player, monsters,
 * and other entities within the game. Overall, it manages the game flow by handling user input, updating the game state,
 * and displays relevant information to users.
 *
 * @author Chelsea Dacones
 * @author Rosemary Roach
 */
public class DungeonAdventure {
    private static final int MAX_PIT_DAMAGE = 10;
    private static final String YOU_WIN_ASCII = """
            ██╗   ██╗ ██████╗ ██╗   ██╗    ██╗    ██╗██╗███╗   ██╗██╗
            ╚██╗ ██╔╝██╔═══██╗██║   ██║    ██║    ██║██║████╗  ██║██║
             ╚████╔╝ ██║   ██║██║   ██║    ██║ █╗ ██║██║██╔██╗ ██║██║
              ╚██╔╝  ██║   ██║██║   ██║    ██║███╗██║██║██║╚██╗██║╚═╝
               ██║   ╚██████╔╝╚██████╔╝    ╚███╔███╔╝██║██║ ╚████║██╗
               ╚═╝    ╚═════╝  ╚═════╝      ╚══╝╚══╝ ╚═╝╚═╝  ╚═══╝╚═╝""";
    private static final String GAME_OVER_ASCII = """
             ██████╗  █████╗ ███╗   ███╗███████╗     ██████╗ ██╗   ██╗███████╗██████╗
            ██╔════╝ ██╔══██╗████╗ ████║██╔════╝    ██╔═══██╗██║   ██║██╔════╝██╔══██╗
            ██║  ███╗███████║██╔████╔██║█████╗      ██║   ██║██║   ██║█████╗  ██████╔╝
            ██║   ██║██╔══██║██║╚██╔╝██║██╔══╝      ██║   ██║╚██╗ ██╔╝██╔══╝  ██╔══██╗
            ╚██████╔╝██║  ██║██║ ╚═╝ ██║███████╗    ╚██████╔╝ ╚████╔╝ ███████╗██║  ██║
             ╚═════╝ ╚═╝  ╚═╝╚═╝     ╚═╝╚══════╝     ╚═════╝   ╚═══╝  ╚══════╝╚═╝  ╚═╝""";
    private static final String[] DIFFICULTY_DESCRIPTIONS = new String[]{"Easy - Stronger starting player, weaker monsters, more items, small dungeon",
            "Medium - Default values, medium-sized dungeon",
            "Hard - Weaker starting player, stronger monsters, less items, large dungeon"};
    private final AdventureView myAdventureView;
    private Dungeon myDungeon;
    private Hero myHero;
    private ActionHandler myActionHandler;
    private boolean myIsPlaying;
    private boolean myWonGame;
    private DifficultyLevel myDifficultyLevel;

    public DungeonAdventure() {
        myAdventureView = new AdventureView();
        myActionHandler = new ActionHandler(myHero, this);
        boolean playAgain = true;
        while (playAgain) {
            myWonGame = false;
            myIsPlaying = false;
            displayIntroduction();
            displayMenu();
            playAgain = myAdventureView.promptUserChoice(new String[]{"Play Again", "Exit"},
                    false).equals("Play Again");
        }
    }

    /**
     * The main entry point of the game.
     *
     * @param theArgs command line arguments (not used)
     */
    public static void main(final String[] theArgs) {
        new DungeonAdventure();
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
            myDifficultyLevel = selectDifficultyLevel();
            myHero = createAdventurer();
            myDungeon = myDifficultyLevel.createDungeon();
            myDifficultyLevel.adjustGameLevel(myDungeon, myHero);
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
        if (myHero.isFainted()) {
            myAdventureView.sendMessage(GAME_OVER_ASCII);
        }
        displayDungeon();
    }

    /**
     * Displays the introduction of the game to the user.
     */
    private void displayIntroduction() {
        myAdventureView.sendMessage("[Introduction placeholder]");
    }

    void displayMenu() {
        myAdventureView.sendMessage("----------- MENU -----------");
        final String choice;
        final String[] options = myIsPlaying ? new String[]{"Help", "Save Game", "New Game", "Exit Game", "Close Menu"}
                : new String[]{"Play", "Instructions", "Exit Game"};
        choice = myAdventureView.promptUserChoice(options, true);
        myActionHandler.handleMenuAction(choice, this, myIsPlaying ? myDungeon : null, myIsPlaying ? myHero : null, myIsPlaying);
    }

    private DifficultyLevel selectDifficultyLevel() {
        myAdventureView.sendMessage("Select Game Difficulty:\033[0m\n" + String.join("\n", DIFFICULTY_DESCRIPTIONS));
        final String choice = myAdventureView.promptUserInput("What difficulty level do you want to play? ",
                "Please enter the name of the level: ", (String s) -> s.equalsIgnoreCase("easy")
        || s.equalsIgnoreCase("medium") || s.equalsIgnoreCase("hard"));
        return switch (choice.toUpperCase()) {
            case "EASY" -> DifficultyLevel.EASY;
            case "MEDIUM" -> DifficultyLevel.MEDIUM;
            case "HARD" -> DifficultyLevel.HARD;
            default -> throw new IllegalStateException("Unexpected value: " + choice);
        };
    }
    /**
     * Creates an adventurer character based on user's input.
     * Prompts the user for their name and character selection and returns the corresponding Hero object.
     *
     * @return the Hero character selected by the user.
     */
    private Hero createAdventurer() {
        final String name = myAdventureView.promptUserInput("\nWhat is your name? ", "Please enter a name: ", (String s) -> s != null && s.length() > 0);
        final Map<String, Hero> playerCreators = Map.of(
                "Thief", new Thief(name),
                "Warrior", new Warrior(name),
                "Priestess", new Priestess(name)
        );
        String character;
        do {
            myAdventureView.sendMessage("Pick your character: ");
            character = myAdventureView.promptUserChoice(new String[]{"Thief", "Warrior", "Priestess"}, false);
            myDifficultyLevel.adjustHeroStatistics(playerCreators.get(character));
            myAdventureView.sendMessage("\033[0m\n" + playerCreators.get(character));
        } while (myAdventureView.promptUserInput("\nWould you like to reselect your character? ", "Please enter 'Yes' or 'No': ",
                (String s) -> s != null && (s.equalsIgnoreCase("Yes") || s.equalsIgnoreCase("No"))).equalsIgnoreCase("Yes"));
        return switch (character) {
            case "Thief" -> new Thief(name);
            case "Warrior" -> new Warrior(name);
            case "Priestess" -> new Priestess(name);
            default -> null;
        };
    }

    /**
     * Displays the current room to the user.
     * This method shows the description of the current room, along with any available items in the room.
     */
    private void displayCurrentRoom() {
        // print adjacent rooms if vision potion is active; otherwise only display current room
        myAdventureView.printRoom(new RoomData(myDungeon.getCurrentRoom()), myHero.isVisionPotionActive() ? myDungeon.getNeighbors() : null);
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
            if (roomData.getMonsters().length != 0) {
                options.add("Battle " + myDungeon.getCurrentRoom().getMonster().getName());
                options.add("View Enemy Stats");
            } else {
                Arrays.stream(roomData.getDoors()).map(door -> "Go " + door).forEach(options::add);
                if (!myDungeon.getCurrentRoom().getItems().isEmpty()) {
                    options.add("Pick Up Items");
                }
            }
            if (!myHero.getMyInventory().isEmpty()) {
                options.add("See Inventory");
            }
            options.addAll(Arrays.asList("View Player Stats", "Open Menu"));
            final String choice = myAdventureView.promptUserChoice(options.toArray(new String[0]));
            myAdventureView.sendMessage("You chose: " + choice + "\n");
            myActionHandler.handleGameAction(choice, myDungeon, myHero);
        }
    }

    public void handlePit() {
        myAdventureView.sendMessage("You fell into a pit!");
        final int damage = new Random().nextInt(MAX_PIT_DAMAGE) + 1;
        myHero.setHitPoints(Math.max(myHero.getHitPoints() - damage, 0));
        myAdventureView.sendMessage("You took " + damage + " damage! " + myHero.getHitPoints() + " hit points remaining.");
        myDungeon.getCurrentRoom().removePit();
    }

    /**
     * Displays the entire dungeon layout to the user.
     */
    private void displayDungeon() {
        myAdventureView.sendMessage("[Dungeon placeholder]");
    }

    private boolean wonGame() {
        final List<String> allPillars = new ArrayList<>(Arrays.asList("Pillar of Abstraction", "Pillar of Encapsulation", "Pillar of Inheritance", "Pillar of Polymorphism"));
        final List<String> collectedPillars = myHero.getMyInventory().stream().filter(item -> item instanceof PillarOfOO).map(Item::getName).toList();
        final List<String> remainingPillars = new ArrayList<>(allPillars);
        remainingPillars.removeAll(collectedPillars);
        final boolean hasAllPillars = collectedPillars.size() == allPillars.size();
        myAdventureView.sendMessage(hasAllPillars
                ? "Congratulations adventurer! You've collected all four Pillars of OO and have won the game!" + "\n" + YOU_WIN_ASCII + "\n"
                : "You're missing " + (allPillars.size() - collectedPillars.size()) + " Pillars of OO: " + String.join(", ", remainingPillars) + "\n");
        return hasAllPillars;
    }
}
