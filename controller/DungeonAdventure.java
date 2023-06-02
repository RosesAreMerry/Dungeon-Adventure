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
    private Dungeon myDungeon;
    private Hero myHero;
    private final AdventureView myAdventureView;
    private final InventoryView myInventoryView;
    private final CombatView myCombatView;

    public DungeonAdventure() {
        myAdventureView = new AdventureView();
        final Consumer<Item> myItemHandler = item -> {
            if (item instanceof final Potion thePotion) {
                thePotion.use(myHero);
            }
            if (item instanceof final PillarOfOO thePillar) {
                myAdventureView.sendMessage("This is the pillar of " + thePillar.getName() + ".");
            }
        };
        myInventoryView = new InventoryView(myItemHandler);
        myCombatView = new CombatView();
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
        myDungeon = createDungeon();
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
    private Dungeon createDungeon() {
        final String[] options = {"Small", "Medium", "Large"};
        final String choice = myAdventureView.promptUserChoice(options, false, "What size dungeon do you want to explore? ");

        switch (choice) {
            case "Medium" -> {
                return DungeonBuilder.INSTANCE.buildDungeon(25);
            }
            case "Large" -> {
                return DungeonBuilder.INSTANCE.buildDungeon(50);
            }
            default -> {
                return DungeonBuilder.INSTANCE.buildDungeon(10);
            }
        }
    }

    private RoomData getCurrentRoomData() {
        return new RoomData(myDungeon.getCurrentRoom());
    }

    /**
     * Displays the current room to the user.
     * This method shows the description of the current room, along with any available items in the room.
     */
    private void displayCurrentRoom() {
        Map<String, RoomData> adjacentRooms = null;
        if (myHero.isVisionPotionActive()) {
            adjacentRooms = myDungeon.getNeighbors().entrySet().stream()
                    .map(e -> new AbstractMap.SimpleEntry<>(e.getKey(), new RoomData(e.getValue())))
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        }
        myAdventureView.printRoom(getCurrentRoomData(), adjacentRooms);
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
        myAdventureView.sendMessage("What do you want to do?");
        final List<String> options = new ArrayList<>();
        RoomData rd = getCurrentRoomData();
        if (rd.getMonsters().length == 0) {
            for (final String door : rd.getDoors()) {
                options.add("Go " + door);
            }
            options.add("Look Around");
        }
        options.add("See Inventory");
        for (final String monster : rd.getMonsters()) {
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
        actions.put("Go " + direction, () -> { handleMove(direction); });
        actions.put("Battle " + monster, () -> handleCombat(monster));
        actions.put("See Inventory", () -> myInventoryView.showInventory(myHero.getMyInventory()));
        actions.put("Look Around", () -> { /* handle Look Around action */ });
        final Runnable action = actions.get(theChoice);
        action.run();
    }

    private void handleCombat(final String theOpponent) {
        final RoomData roomData = getCurrentRoomData();
        if (roomData.getMonsters() != null && roomData.getMonsters().length > 0) {
            final Combat combat = new Combat();
            final MonsterFactory monsterFactory = new MonsterFactory();
            final Monster opponent = monsterFactory.createMonsterByName(theOpponent);
            combat.initiateCombat(myHero, opponent);
            roomData.removeMonsterFromRoom(theOpponent);
            if (opponent.isFainted()) {
                myAdventureView.sendMessage(theOpponent + " was defeated!");
                myDungeon.getCurrentRoom().killMonster();
            }
        }
    }

    private void handleMove(final String theDir) {
        final Direction direction = Direction.valueOf(theDir.toUpperCase());
        myDungeon.move(direction);
        myHero.reduceVisionPotionTurns();
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
