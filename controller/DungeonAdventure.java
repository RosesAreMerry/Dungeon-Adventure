package controller;

import model.*;
import view.AdventureView;
import view.CombatView;
import view.InventoryView;
import view.RoomData;

import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Stream;

/**
 * Serves as the main entry point for the game and orchestrates the actions of the player, monsters,
 * and other entities within the game. Overall, it manages the game flow by handling user input, updating the game state,
 * and displays relevant information to users.
 */
public class DungeonAdventure {
    public static final int MAX_PIT_DAMAGE = 10;
    private Dungeon myDungeon;
    private Hero myHero;
    private RoomData myCurrentRoomData;
    private final AdventureView myAdventureView;
    private final InventoryView myInventoryView;

    public DungeonAdventure() throws InterruptedException {
        myAdventureView = new AdventureView();
        final Consumer<Item> myItemHandler = item -> {
            if (item instanceof final Potion thePotion) {
                thePotion.use(myHero);
            }
            if (item instanceof PillarOfOO) {
                myAdventureView.sendMessage("Pillars cannot be used");
            }
        };
        myInventoryView = new InventoryView(myItemHandler);
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
        myHero = createAdventurer();
        myDungeon = createDungeon();
        myCurrentRoomData = getCurrentRoomData();
        myAdventureView.sendMessage("\nYou walk into a dungeon.");

        // Main game loop
        do {
            Thread.sleep(1000);
            displayCurrentRoom();
            Thread.sleep(2000);
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

    private void setCurrentRoomData(final RoomData theRoomData) {
        myCurrentRoomData = theRoomData;
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
        handleItems();
    }

    /**
     * Displays the available options to the user.
     * Presents a list of available choices to the user and handles the action associated with the choice.
     */
    private void displayOptions() {
        if (myCurrentRoomData.getMonsters() != null) {
            for (final String monster : myCurrentRoomData.getMonsters()) {
                handleCombat(monster);
            }
        } if (!myHero.isFainted()) {
            myAdventureView.sendMessage("What do you want to do?");
            final List<String> options = new ArrayList<>();
            if (myCurrentRoomData.getMonsters().length == 0) {
                for (final String door : myCurrentRoomData.getDoors()) {
                    final String doorTitleCase = door.substring(0, 1).toUpperCase() + door.substring(1).toLowerCase();
                    options.add("Go " + doorTitleCase);
                }
                options.add("Look Around");
            }
            if (myHero.getMyInventory().size() > 0) {
                options.add("See Inventory");
            }
//        for (final String monster : myCurrentRoomData.getMonsters()) {
//            options.add("Battle " + monster);
//        }
            final String choice = myAdventureView.promptUserChoice(options.toArray(new String[0]));
            myAdventureView.sendMessage("You chose: " + choice);
            handleAction(choice);
        }


        myAdventureView.sendMessage("What do you want to do?");
        final List<String> options = new ArrayList<>();
        if (myCurrentRoomData.getMonsters().length == 0) {
            for (final String door : myCurrentRoomData.getDoors()) {
                options.add("Go " + door);
            }
            options.add("Look Around");
        }
        options.add("See Inventory");
        for (final String monster : myCurrentRoomData.getMonsters()) {
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
        if (theChoice.startsWith("Go")) { // handle moving to other rooms
            final String direction = theChoice.substring(3);
            actions.put("Go " + direction, () -> handleMove(direction));
        }
        final String monster = theChoice.substring(7);
        actions.put("Battle " + monster, () -> handleCombat(monster));
//        actions.put("Battle " + monster, () -> handleCombat(monster));
        actions.put("Look Around", this::lookAroundAction);
        actions.put("See Inventory", () -> myInventoryView.showInventory(myHero.getMyInventory()));
        final Runnable action = actions.get(theChoice);
        action.run();
    }

    private void handleCombat(final String theOpponent) {
        myCurrentRoomData = getCurrentRoomData();
        if (myCurrentRoomData.getMonsters() != null && myCurrentRoomData.getMonsters().length > 0) {
            final Combat combat = new Combat();
            final MonsterFactory monsterFactory = new MonsterFactory();
            final Monster opponent = monsterFactory.createMonsterByName(theOpponent);
            combat.initiateCombat(myHero, opponent);
            myCurrentRoomData.removeMonsterFromRoom(theOpponent);
            if (opponent.isFainted()) {
                myAdventureView.sendMessage(theOpponent + " was defeated!");
                myDungeon.getCurrentRoom().killMonster();
            }
        }
    }

    private void handleMove(final String theDir) {
        final Direction direction = Direction.valueOf(theDir.toUpperCase());
        myDungeon.move(direction);
    }

    private void handlePit() {
        myAdventureView.sendMessage("You fell into a pit!");
        final int damage = new Random().nextInt(MAX_PIT_DAMAGE) + 1;
        myAdventureView.sendMessage("You took " + damage + " damage! " + myHero.getHitPoints() + " hit points remaining.");
        myHero.setHitPoints(myHero.getHitPoints() - damage);
        myDungeon.getCurrentRoom().removePit();
    }

    private void handleItems() {
        final List<Item> items = myDungeon.getCurrentRoom().getItems();
        for (final Item item : items) {
            myHero.getMyInventory().add(item);
            myAdventureView.sendMessage("You picked up " + item.getName());
        }
        myDungeon.getCurrentRoom().getItems().removeIf(item -> true);
    }

    private void lookAroundAction() {
        myCurrentRoomData = getCurrentRoomData();
        if (myCurrentRoomData.getItems().length > 0) {
            final StringBuilder sb = new StringBuilder();
            myAdventureView.buildList(sb,
                    myCurrentRoomData.getItems(),
                    "You acquired ",
                    "You acquired ",
                    "!",
                    true);
            myAdventureView.sendMessage(String.valueOf(sb));
            myHero.addToInventory(myDungeon.getCurrentRoom().getItems());
            myDungeon.getCurrentRoom().getItems().clear();
        }
        myCurrentRoomData = new RoomData(myCurrentRoomData.getFlavor(), myCurrentRoomData.getDoors(),
                new String[]{}, new String[]{}, myCurrentRoomData.isPit(), myCurrentRoomData.isExit());
        setCurrentRoomData(myCurrentRoomData);
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
    public static void main(final String[] theArgs) throws InterruptedException {
        new DungeonAdventure();
    }
}
